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
package org.exoplatform.acceptance.rest.administration;

import org.exoplatform.acceptance.model.StorableObject;
import org.exoplatform.acceptance.service.CRUDService;

import java.io.Serializable;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Abstract REST Controller
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public abstract class CRUDController<T extends StorableObject, I extends Serializable> {

  /**
   * <p>getCRUDService.</p>
   *
   * @return a {@link org.exoplatform.acceptance.service.CRUDService} object.
   */
  abstract protected CRUDService<T> getCRUDService();

  /**
   * Get a (potentially paginated) list of objects
   *
   * @param offset the page number to get (0 by default is the first page)
   * @param limit  the maximum number of entries in the page of results ( > 0 to activate pagination, -1 thus everything by default )
   * @return a list of objects
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Iterable<T> getObjects(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "-1") int limit) {
    if (limit > 0) {
      return getCRUDService().findAll(offset, limit);
    } else {
      return getCRUDService().findAll();
    }
  }

  /**
   * Get a specific object by its id.
   *
   * @param id       the id of the object
   * @param response a {@link javax.servlet.http.HttpServletResponse} object.
   * @return the object
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ResponseBody
  public T getObject(@PathVariable(value = "id") String id, HttpServletResponse response) {
    return getCRUDService().findOne(id);
  }

  /**
   * Save a new object
   *
   * @param object the object to save
   * @return the saved object
   */
  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public T saveObject(@Valid @RequestBody T object) {
    return getCRUDService().updateOrCreate(object);
  }

  /**
   * Update an existing object
   *
   * @param id       the id of the object to update
   * @param object   the object to update
   * @param response a {@link javax.servlet.http.HttpServletResponse} object.
   * @return the updated object
   */
  @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public T updateObject(@PathVariable(value = "id") I id, @Valid @RequestBody T object, HttpServletResponse response) {
    return getCRUDService().update(object);
  }

  /**
   * Delete an existing object
   *
   * @param id the id of the object to delete
   */
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteObject(@PathVariable(value = "id") String id) {
    getCRUDService().delete(id);
  }
}
