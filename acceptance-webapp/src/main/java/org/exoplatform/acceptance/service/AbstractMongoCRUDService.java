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

import org.exoplatform.acceptance.model.StorableObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Set of services to manage entities stored in a Mongo repository
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public abstract class AbstractMongoCRUDService<T extends StorableObject> implements CRUDService<T> {

  /**
   * Constant <code>LOGGER</code>
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoCRUDService.class);

  /**
   * <p>getMongoStorage.</p>
   *
   * @return a {@link org.springframework.data.mongodb.repository.MongoRepository} object.
   */
  protected abstract MongoRepository<T, String> getMongoStorage();

  /**
   * {@inheritDoc}
   * <p/>
   * Retrieves an entity by its id.
   */
  public T findOne(String id) throws EntityNotFoundException {
    if (!exists(id)) {
      throw new EntityNotFoundException(id);
    }
    return getMongoStorage().findOne(id);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Updates a given entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
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
   * {@inheritDoc}
   * <p/>
   * Updates an existing entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   */
  @Override
  public <S extends T> S updateOrCreate(S entity) {
    LOGGER.debug("Updating or saving entity {}", entity);
    return getMongoStorage().save(entity);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns whether an entity with the given id exists.
   */
  @Override
  public boolean exists(String id) {
    return getMongoStorage().exists(id);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns all instances of the type.
   */
  @Override
  public Iterable<T> findAll() {
    return getMongoStorage().findAll();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns all instances of the given page. Pages are zero indexed, thus providing 0 for {@code page} will return the first
   * page.
   */
  @Override
  public Iterable<T> findAll(int page, int size) {
    return getMongoStorage().findAll(new PageRequest(page, size));
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the number of entities available.
   */
  @Override
  public long count() {
    return getMongoStorage().count();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Deletes the entity with the given id.
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
   * {@inheritDoc}
   * <p/>
   * Deletes all entities managed by the repository.
   */
  @Override
  public void deleteAll() {
    getMongoStorage().deleteAll();
  }

}
