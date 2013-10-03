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
import org.exoplatform.acceptance.model.Deployment;
import org.exoplatform.acceptance.repositories.ApplicationRepository;
import org.exoplatform.acceptance.repositories.DeploymentRepository;
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
 *
 */
@Controller
@RequestMapping(value = "deployment", produces = MediaType.APPLICATION_JSON_VALUE)
public class RESTDeploymentController {

  public static final int DEFAULT_DEPLOYMENT_PAGE_SIZE = 50;

  public static final int MAX_DEPLOYMENT_PAGE_SIZE = 250;

  @Inject
  private DeploymentRepository deploymentRepository;

  @Inject
  private ApplicationRepository applicationRepository;

  /**
   * Get a paginated list of available deployments
   *
   * @param offset the page number to get (0 is the first page)
   * @param limit  the maximum number of entries in the page of results
   * @return a list of deployments
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Deployment> getDeployments(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = DEFAULT_DEPLOYMENT_PAGE_SIZE + "") int limit) {
    return deploymentRepository.findAll(new PageRequest(offset, limit <= MAX_DEPLOYMENT_PAGE_SIZE ? limit : DEFAULT_DEPLOYMENT_PAGE_SIZE));
  }

  /**
   * Get a specific deployment by its id.
   *
   * @param id the id of the deployment
   * @return the deployment
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  @ResponseBody
  public Deployment getDeployment(@PathVariable(value = "id") String id) {
    return deploymentRepository.findOne(id);
  }

  /**
   * Save a new deployment
   *
   * @param deployment the deployment to save
   * @return the saved deployment
   */
  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Deployment saveDeployment(@RequestBody Deployment deployment) {
    return deploymentRepository.save(deployment);
  }

  /**
   * Update an existing deployment
   *
   * @param deployment the deployment to update
   * @return the updated deployment
   */
  @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Deployment updateDeployment(@RequestBody Deployment deployment) {
    return deploymentRepository.save(deployment);
  }

  /**
   * Delete an existing deployment
   *
   * @param deployment the deployment to delete
   */
  @RequestMapping(value = "/", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void deleteDeployment(@RequestBody Deployment deployment) {
    deploymentRepository.delete(deployment);
  }

  /**
   * Get a paginated list of deployment for a particular application name
   *
   * @param applicationName the application name
   * @param offset          the page number to get (0 is the first page)
   * @param limit           the maximum number of entries in the page of results
   * @return a list of deployments
   */
  @RequestMapping(value = "{applicationName}", method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Deployment> getDeploymentsByApplicationName(@PathVariable(value = "applicationName") String applicationName, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = DEFAULT_DEPLOYMENT_PAGE_SIZE + "") int limit) {
    return deploymentRepository.findByApplication(applicationRepository.findByName(applicationName), new PageRequest(offset, limit <= MAX_DEPLOYMENT_PAGE_SIZE ? limit : DEFAULT_DEPLOYMENT_PAGE_SIZE));
  }
}
