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
import org.exoplatform.acceptance.backend.model.Project;
import org.exoplatform.acceptance.backend.storage.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller to manage REST services for projects
 */
@Controller
@RequestMapping(value = "project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController extends RestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

  @Inject
  private ProjectRepository projectRepository;

  /**
   * Get a paginated list of available projects
   *
   * @param offset the page number to get (0 is the first page)
   * @param limit  the maximum number of entries in the page of results
   * @return a list of projects
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Project> getProjects(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = DEFAULT_STORAGE_PAGE_SIZE + "") int limit) {
    // TODO : Filter content depending of the caller role/right
    return projectRepository.findAll(new PageRequest(offset, limit <= MAX_STORAGE_PAGE_SIZE ? limit : DEFAULT_STORAGE_PAGE_SIZE));
  }

  /**
   * Get a specific project by its id.
   *
   * @param id the id of the project
   * @return the project
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ResponseBody
  public Project getProject(@PathVariable(value = "id") String id) {
    // TODO : Filter content depending of the caller role/right
    return projectRepository.findOne(id);
  }

  /**
   * Save a new project
   *
   * @param project the project to save
   * @return the saved project
   */
  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @RolesAllowed("ROLE_ADMIN")
  public Project saveProject(@RequestBody Project project) {
    LOGGER.debug("Creating project {}", project.getName());
    return projectRepository.save(project);
  }

  /**
   * Update an existing project
   *
   * @param id      the id of the project to update
   * @param project the project to update
   * @return the updated project
   */
  @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @RolesAllowed("ROLE_ADMIN")
  public Project updateProject(@PathVariable(value = "id") String id, @RequestBody Project project) {
    LOGGER.debug("Updating project {} with ID {}", project.getName(), project.getId());
    return projectRepository.save(project);
  }

  /**
   * Delete an existing project
   *
   * @param id the id of the project to delete
   */
  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @RolesAllowed("ROLE_ADMIN")
  public void deleteProject(@PathVariable(value = "id") String id) {
    LOGGER.debug("Deleting project with ID {}", id);
    projectRepository.delete(id);
  }
}
