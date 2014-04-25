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
package org.exoplatform.acceptance.service.credential;

import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.service.AbstractMongoCRUDService;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.storage.credential.CredentialMongoStorage;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Services to manage Credentials
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named
public class CredentialService extends AbstractMongoCRUDService<Credential> implements CRUDService<Credential> {

  @Inject
  private CredentialMongoStorage credentialMongoStorage;

  /**
   * {@inheritDoc}
   */
  @Override
  protected CredentialMongoStorage getMongoStorage() {
    return credentialMongoStorage;
  }

  /**
   * <p>findByName.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @return a {@link org.exoplatform.acceptance.model.credential.Credential} object.
   */
  public Credential findByName(String name) {
    return getMongoStorage().findByName(name);
  }

  /**
   * <p>findByType.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.credential.Credential.Type} object.
   * @return a {@link java.util.List} object.
   */
  public List<Credential> findByType(Credential.Type type) {
    return getMongoStorage().findByType(type);
  }
}
