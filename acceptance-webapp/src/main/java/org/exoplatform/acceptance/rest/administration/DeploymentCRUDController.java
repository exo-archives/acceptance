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
package org.exoplatform.acceptance.rest.administration;

import org.exoplatform.acceptance.model.Deployment;
import org.exoplatform.acceptance.service.DeploymentService;

import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping(value = "admin/deployment", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeploymentCRUDController extends CRUDController<Deployment, String> {

  @Inject
  private DeploymentService deploymentService;

  @Override
  protected DeploymentService getCRUDService() {
    return deploymentService;
  }

  /**
   * Get a paginated list of deployment for a particular application name
   *
   * @param applicationName the application name
   * @param offset          the page number to get (0 by default is the first page)
   * @param limit           the maximum number of entries in the page of results ( > 0 to activate pagination, -1 thus everything by default )
   * @return a list of deployments
   */
  @RequestMapping(value = "{applicationName}", method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Deployment> getDeploymentsByApplicationName(@PathVariable(
      value = "applicationName") String applicationName, @RequestParam(value = "offset",
                                                                       defaultValue = "0") int offset, @RequestParam(
      value = "limit", defaultValue = "-1") int limit) {
    // TODO : Filter content depending of the caller role/right
    if (limit > 0) {
      return deploymentService.findByApplication(applicationName, offset, limit);
    } else {
      return deploymentService.findByApplication(applicationName);
    }
  }
}
