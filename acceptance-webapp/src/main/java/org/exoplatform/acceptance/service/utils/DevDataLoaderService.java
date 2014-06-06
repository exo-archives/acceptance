/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.acceptance.service.utils;

import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.model.credential.KeyPairCredential;
import org.exoplatform.acceptance.model.credential.TokenCredential;
import org.exoplatform.acceptance.model.credential.UsernamePasswordCredential;
import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.credential.CredentialService;
import org.exoplatform.acceptance.service.vcs.VCSRepositoryService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * This service is used to load some hardcoded data from the model to use them in development mode or in tests
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named
public class DevDataLoaderService {
  @Inject
  private VCSRepositoryService vcsRepositoryService;
  @Inject
  private CredentialService credentialService;

  /**
   * <p>initializeData.</p>
   *
   * @since 2.0.0
   */
  public void initializeData() {
    credentialService.updateOrCreate(new UsernamePasswordCredential("A username/password", "a_username", "a_password"));
    credentialService.updateOrCreate(new TokenCredential("A token", "a_token"));
    credentialService.updateOrCreate(new KeyPairCredential("A key pair", "a_private_key", "a_public_key"));
    createVCSRepository("maven-depmgt-pom", false);
    createVCSRepository("platform-ui", true);
    createVCSRepository("commons", true);
    createVCSRepository("ecms", true);
    createVCSRepository("calendar", true);
    createVCSRepository("social", true);
    createVCSRepository("wiki", true);
    createVCSRepository("forum", true);
    createVCSRepository("integration", true);
    createVCSRepository("platform", true);
    createVCSRepository("platform-public-distributions", false);
    createVCSRepository("ide", true);
    createVCSRepository("platform-private-distributions", false);
  }

  /**
   * <p>createVCSRepository.</p>
   *
   * @param repoName   a {@link java.lang.String} object.
   * @param hasDevRepo a boolean.
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   * @since 2.0.0
   */
  private VCSRepository createVCSRepository(String repoName, boolean hasDevRepo) {
    VCSRepository gitRepository = new VCSRepository(repoName);
    if (hasDevRepo) {
      gitRepository.addRemoteRepository("development", "https://github.com/exodev/" + repoName + ".git",
                                        Credential.NONE.getId());
    }
    gitRepository.addRemoteRepository("blessed", "https://github.com/exoplatform/" + repoName + ".git",
                                      Credential.NONE.getId());
    return vcsRepositoryService.updateOrCreate(gitRepository);
  }
}
