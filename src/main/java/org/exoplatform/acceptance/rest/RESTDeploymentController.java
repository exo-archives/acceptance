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
import org.exoplatform.acceptance.model.Application;
import org.exoplatform.acceptance.model.Deployment;
import org.exoplatform.acceptance.repositories.ApplicationRepository;
import org.exoplatform.acceptance.repositories.DeploymentRepository;
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
@RequestMapping(value = "deployment", produces = "application/json")
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
    Page<Deployment> deployments = deploymentRepository.findAll(new PageRequest(offset, limit <= MAX_DEPLOYMENT_PAGE_SIZE ? limit : DEFAULT_DEPLOYMENT_PAGE_SIZE));
    return deployments;
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
    Deployment deployment = deploymentRepository.findOne(id);
    return deployment;
  }

  /**
   * Save a new deployment
   *
   * @param deployment the deployment to save
   * @return the saved deployment
   */
  @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
  @ResponseBody
  public Deployment saveDeployment(@RequestBody Deployment deployment) {
    Deployment savedDeployment = deploymentRepository.save(deployment);
    return savedDeployment;
  }

  /**
   * Update an existing deployment
   *
   * @param deployment the deployment to update
   * @return the updated deployment
   */
  @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = "application/json")
  @ResponseBody
  public Deployment updateDeployment(@RequestBody Deployment deployment) {
    Deployment savedDeployment = deploymentRepository.save(deployment);
    return savedDeployment;
  }

  /**
   * Delete an existing deployment
   *
   * @param deployment the deployment to delete
   */
  @RequestMapping(value = "/", method = RequestMethod.DELETE, consumes = "application/json")
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
    Application application = applicationRepository.findByName(applicationName);
    Iterable<Deployment> deployments = deploymentRepository.findByApplication(application, new PageRequest(offset, limit <= MAX_DEPLOYMENT_PAGE_SIZE ? limit : DEFAULT_DEPLOYMENT_PAGE_SIZE));
    return deployments;
  }
}
