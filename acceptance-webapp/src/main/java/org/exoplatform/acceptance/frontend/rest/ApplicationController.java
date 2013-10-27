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
package org.exoplatform.acceptance.frontend.rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import org.exoplatform.acceptance.backend.model.Application;
import org.exoplatform.acceptance.backend.storage.ApplicationMongoStorage;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to manage REST services for applications
 */
@Controller
@RequestMapping(value = "application", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController extends RestController {

  @Inject
  private ApplicationMongoStorage applicationMongoStorage;

  /**
   * Get a paginated list of available applications
   *
   * @param offset the page number to get (0 is the first page)
   * @param limit  the maximum number of entries in the page of results
   * @return a list of applications
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Application> getApplications(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = DEFAULT_STORAGE_PAGE_SIZE + "") int limit) {
    // TODO : Filter content depending of the caller role/right
    return applicationMongoStorage.findAll(new PageRequest(offset, limit <= MAX_STORAGE_PAGE_SIZE ? limit : DEFAULT_STORAGE_PAGE_SIZE));
  }

  /**
   * Get a specific application by its id.
   *
   * @param id the id of the application
   * @return the application
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ResponseBody
  public Application getApplication(@PathVariable(value = "id") String id) {
    // TODO : Filter content depending of the caller role/right
    return applicationMongoStorage.findOne(id);
  }

  /**
   * Save a new application
   *
   * @param application the application to save
   * @return the saved application
   */
  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @RolesAllowed("ROLE_ADMIN")
  public Application saveApplication(@RequestBody Application application) {
    return applicationMongoStorage.save(application);
  }

  /**
   * Update an existing application
   *
   * @param application the application to update
   * @return the updated application
   */
  @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @RolesAllowed("ROLE_ADMIN")
  public Application updateApplication(@RequestBody Application application) {
    return applicationMongoStorage.save(application);
  }

  /**
   * Delete an existing application
   *
   * @param application the application to delete
   */
  @RequestMapping(value = "/", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @RolesAllowed("ROLE_ADMIN")
  public void deleteApplication(@RequestBody Application application) {
    applicationMongoStorage.delete(application);
  }
}
