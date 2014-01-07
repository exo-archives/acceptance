/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.acceptance.service;

import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.model.credential.KeyPairCredential;
import org.exoplatform.acceptance.model.credential.TokenCredential;
import org.exoplatform.acceptance.model.credential.UsernamePasswordCredential;
import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.credential.CredentialService;
import org.exoplatform.acceptance.service.vcs.VCSRepositoryService;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service is used to load some hardcoded data from the model to use them in development mode or in tests
 */
@Named
public class DevDataLoaderService {
  private static final Logger LOGGER = LoggerFactory.getLogger(DevDataLoaderService.class);
  @Inject
  private VCSRepositoryService VCSRepositoryService;
  @Inject
  private CredentialService credentialService;

  public void initializeData() {
    credentialService.updateOrCreate(new UsernamePasswordCredential("A username/password", "a_username", "a_password"));
    credentialService.updateOrCreate(new TokenCredential("A token", "a_token"));
    credentialService.updateOrCreate(new KeyPairCredential("A key pair", "a_private_key", "a_public_key"));
    createVCSRepository("Platform-UI");
    createVCSRepository("Commons");
    createVCSRepository("Content");
    createVCSRepository("Calendar");
    createVCSRepository("Social");
    createVCSRepository("Wiki");
    createVCSRepository("Forum");
    createVCSRepository("Integration");
    createVCSRepository("Platform");
  }

  private VCSRepository createVCSRepository(String name) {
    VCSRepository gitRepository = new VCSRepository(name.toLowerCase());
    gitRepository.addRemoteRepository("development", "https://github.com/exodev/" + name.toLowerCase() + ".git",
                                      Credential.NONE.getId());
    gitRepository.addRemoteRepository("blessed", "https://github.com/exoplatform/" + name.toLowerCase() + ".git",
                                      Credential.NONE.getId());
    return VCSRepositoryService.updateOrCreate(gitRepository);
  }
}
