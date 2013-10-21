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

import org.exoplatform.acceptance.model.Project;
import org.exoplatform.acceptance.model.vcs.DVCSRepository;
import org.exoplatform.acceptance.storage.ProjectMongoStorage;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Services to manage Projects
 */
@Named
public class ProjectService extends AbstractMongoCRUDService<Project> implements CRUDService<Project> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);
  @Inject
  ProjectMongoStorage projectMongoStorage;
  @Inject
  DVCSRepositoryService dvcsRepositoryService;

  @Override
  ProjectMongoStorage getMongoStorage() {
    return projectMongoStorage;
  }

  /**
   * Updates an existing entity with all its related entities. Use the returned instance for further operations as the save
   * operation might have changed the entity instance completely.
   *
   * @param entity the entity to update
   * @return the saved entity
   * @throws EntityNotFoundException in case there is no entity with the given {@code id}
   */
  @Override
  public <S extends Project> S cascadingUpdate(S entity) throws EntityNotFoundException {
    LOGGER.debug("Cascading update of project {}", entity.getName());
    for (DVCSRepository repository : entity.getSourceRepositories()) {
      // We save each repository
      dvcsRepositoryService.cascadingUpdate(repository);
    }
    // We save our project
    update(entity);
    return entity;
  }

  /**
   * Saves a given entity (creates it if it doesn't exist) and all its related entities. Use the returned instance for further
   * operations as the save operation might have changed the entity instance completely.
   *
   * @param entity the entity to update
   * @return the saved entity
   */
  @Override
  public <S extends Project> S cascadingUpdateOrCreate(S entity) {
    LOGGER.debug("Cascading update or creation of project {}", entity.getName());
    for (DVCSRepository repository : entity.getSourceRepositories()) {
      // We save each repository
      dvcsRepositoryService.cascadingUpdateOrCreate(repository);
    }
    // We save our project
    updateOrCreate(entity);
    return entity;
  }

  public Project findByName(String name) {
    return projectMongoStorage.findByName(name);
  }

}
