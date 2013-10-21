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

import org.exoplatform.acceptance.model.StorableObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Set of services to manage entities stored in a Mongo repository
 */
public abstract class AbstractMongoCRUDService<T extends StorableObject> implements CRUDService<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoCRUDService.class);

  abstract MongoRepository<T, String> getMongoStorage();

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   * @throws EntityNotFoundException  in case there is no entity with the given {@code id}
   */
  public T findOne(String id) throws EntityNotFoundException {
    if (!exists(id)) {
      throw new EntityNotFoundException(id);
    }
    return getMongoStorage().findOne(id);
  }

  /**
   * Updates a given entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   *
   * @param entity
   * @return the saved entity
   * @throws EntityNotFoundException in case there is no entity with the given {@code id}
   */
  @Override
  public <S extends T> S update(S entity) throws EntityNotFoundException {
    LOGGER.debug("Updating entity {}", entity);
    if (!exists(entity.getId())) {
      throw new EntityNotFoundException(entity.getId());
    }
    return getMongoStorage().save(entity);
  }

  /**
   * Updates an existing entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   *
   * @param entity the entity to update
   * @return the saved entity
   * @throws EntityNotFoundException in case there is no entity with the given {@code id}
   */
  @Override
  public <S extends T> S updateOrCreate(S entity) {
    LOGGER.debug("Updating or saving entity {}", entity);
    return getMongoStorage().save(entity);
  }

  /**
   * Updates an existing entity with all its related entities. Use the returned instance for further operations as the save
   * operation might have changed the entity instance completely.
   * This default implementation only saves the given entity.
   *
   * @param entity the entity to update
   * @return the saved entity
   * @throws EntityNotFoundException in case there is no entity with the given {@code id}
   */
  @Override
  public <S extends T> S cascadingUpdate(S entity) throws EntityNotFoundException {
    return update(entity);
  }

  /**
   * Saves a given entity (creates it if it doesn't exist) and all its related entities. Use the returned instance for further
   * operations as the save operation might have changed the entity instance completely.
   * This default implementation only creates or saves the given entity.
   *
   * @param entity the entity to update
   * @return the saved entity
   */
  @Override
  public <S extends T> S cascadingUpdateOrCreate(S entity) {
    return updateOrCreate(entity);
  }

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  @Override
  public boolean exists(String id) {
    return getMongoStorage().exists(id);
  }

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Override
  public Iterable<T> findAll() {
    return getMongoStorage().findAll();
  }

  /**
   * Returns all instances of the given page. Pages are zero indexed, thus providing 0 for {@code page} will return the first
   * page.
   *
   * @param page
   * @param size
   * @return entities for the given page
   */
  @Override
  public Iterable<T> findAll(int page, int size) {
    return getMongoStorage().findAll(new PageRequest(page, size));
  }

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  @Override
  public long count() {
    return getMongoStorage().count();
  }

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
   */
  @Override
  public void delete(String id) throws EntityNotFoundException {
    LOGGER.debug("Deleting entity with ID {}", id);
    if (!exists(id)) {
      throw new EntityNotFoundException(id);
    }
    getMongoStorage().delete(id);
  }

  /**
   * Deletes all entities managed by the repository.
   */
  @Override
  public void deleteAll() {
    getMongoStorage().deleteAll();
  }

}
