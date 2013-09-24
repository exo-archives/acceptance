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
package org.exoplatform.acceptance.rest;

import javax.inject.Inject;
import org.exoplatform.acceptance.model.Application;
import org.exoplatform.acceptance.repositories.ApplicationRepository;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping(value = "application", produces = "application/json")
public class RESTApplicationController {

  public static final int DEFAULT_STORAGE_PAGE_SIZE = 50;

  public static final int MAX_STORAGE_PAGE_SIZE = 500;

  @Inject
  private ApplicationRepository applicationRepository;

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
    return applicationRepository.findAll(new PageRequest(offset, limit <= MAX_STORAGE_PAGE_SIZE ? limit : DEFAULT_STORAGE_PAGE_SIZE));
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
    return applicationRepository.findOne(id);
  }

  /**
   * Save a new application
   *
   * @param application the application to save
   * @return the saved application
   */
  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
  @ResponseBody
  public Application saveApplication(@RequestBody Application application) {
    return applicationRepository.save(application);
  }

  /**
   * Update an existing application
   *
   * @param application the application to update
   * @return the updated application
   */
  @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = "application/json")
  @ResponseBody
  public Application updateApplication(@RequestBody Application application) {
    return applicationRepository.save(application);
  }

  /**
   * Delete an existing application
   *
   * @param application the application to delete
   */
  @RequestMapping(value = "/", method = RequestMethod.DELETE, consumes = "application/json")
  public void deleteApplication(@RequestBody Application application) {
    applicationRepository.delete(application);
  }
}
