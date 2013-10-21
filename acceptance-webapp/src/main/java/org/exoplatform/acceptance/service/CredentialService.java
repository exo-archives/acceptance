/*
 * Copyright (C) 2011-2013 eXo Platform SAS.
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
import org.exoplatform.acceptance.storage.CredentialMongoStorage;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Services to manage Credentials
 */
@Named
public class CredentialService extends AbstractMongoCRUDService<Credential> implements CRUDService<Credential> {

  @Inject
  CredentialMongoStorage credentialMongoStorage;

  @Override
  CredentialMongoStorage getMongoStorage() {
    return credentialMongoStorage;
  }

  public Credential findByName(String name) {
    return getMongoStorage().findByName(name);
  }

  public List<Credential> findByType(Credential.Type type) {
    return getMongoStorage().findByType(type);
  }
}
