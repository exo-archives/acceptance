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

import org.exoplatform.acceptance.model.Application;
import org.exoplatform.acceptance.model.Deployment;
import org.exoplatform.acceptance.storage.ApplicationMongoStorage;
import org.exoplatform.acceptance.storage.DeploymentMongoStorage;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Services to manage Deployments
 */
@Named
public class DeploymentService extends AbstractMongoCRUDService<Deployment> implements CRUDService<Deployment> {

  @Inject
  private DeploymentMongoStorage deploymentMongoStorage;

  @Inject
  private ApplicationMongoStorage applicationMongoStorage;

  @Override
  protected DeploymentMongoStorage getMongoStorage() {
    return deploymentMongoStorage;
  }

  public Iterable<Deployment> findByApplication(String applicationNam) {
    return getMongoStorage().findByApplication(applicationMongoStorage.findByName(applicationNam));
  }

  /**
   * Get a paginated list of deployment for a particular application name
   *
   * @param applicationName the application name
   * @param page            the page number to get (0 by default is the first page)
   * @param limit           the maximum number of entries in the page of results ( > 0 to activate pagination, -1 thus everything by default )
   * @return a list of deployments
   */

  public Iterable<Deployment> findByApplication(String applicationName, int page, int limit) {
    return getMongoStorage().findByApplication(applicationMongoStorage.findByName(applicationName), new PageRequest(page, limit));
  }

  public Iterable<Deployment> findByApplication(Application application) {
    return deploymentMongoStorage.findByApplication(application);
  }

  public Page<Deployment> findByApplication(Application application, Pageable pageable) {
    return deploymentMongoStorage.findByApplication(application, pageable);
  }
}
