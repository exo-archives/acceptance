/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
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
import org.exoplatform.acceptance.model.Software;
import org.exoplatform.acceptance.repositories.SoftwareRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping(value = "software", produces = "application/json")
public class RESTSoftwareController {

  public static final int DEFAULT_STORAGE_PAGE_SIZE = 50;

  public static final int MAX_STORAGE_PAGE_SIZE = 500;

  @Inject
  private SoftwareRepository softwareRepository;

  /**
   * Get a paginated list of available software
   *
   * @param offset the page number to get (0 is the first page)
   * @param limit  the maximum number of entries in the page of results
   * @return a list of softwares
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Software> getSoftwares(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = DEFAULT_STORAGE_PAGE_SIZE + "") int limit) {
    Page<Software> softwares = softwareRepository.findAll(new PageRequest(offset, limit <= MAX_STORAGE_PAGE_SIZE ? limit : DEFAULT_STORAGE_PAGE_SIZE));
    return softwares;
  }

  /**
   * Get a specific software by its id.
   *
   * @param id the id of the software
   * @return the software
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ResponseBody
  public Software getSoftware(@PathVariable(value = "id") String id) {
    Software software = softwareRepository.findOne(id);
    return software;
  }

  /**
   * Save a new software
   *
   * @param software the software to save
   * @return the saved software
   */
  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
  @ResponseBody
  public Software saveSoftware(@RequestBody Software software) {
    Software savedSoftware = softwareRepository.save(software);
    return savedSoftware;
  }

  /**
   * Update an existing software
   *
   * @param software the software to update
   * @return the updated software
   */
  @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = "application/json")
  @ResponseBody
  public Software updateSoftware(@RequestBody Software software) {
    Software savedSoftware = softwareRepository.save(software);
    return savedSoftware;
  }

  /**
   * Delete an existing software
   *
   * @param software the software to delete
   */
  @RequestMapping(value = "/", method = RequestMethod.DELETE, consumes = "application/json")
  public void deleteSoftware(@RequestBody Software software) {
    softwareRepository.delete(software);
  }
}
