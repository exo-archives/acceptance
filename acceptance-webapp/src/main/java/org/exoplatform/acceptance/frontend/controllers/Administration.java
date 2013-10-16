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
package org.exoplatform.acceptance.frontend.controllers;


import javax.inject.Inject;
import juzu.Path;
import juzu.Response;
import juzu.Route;
import juzu.View;
import juzu.plugin.asset.WithAssets;

/**
 * Administration tasks controller
 */
public class Administration extends BaseController {

  /**
   * Administration home page
   */
  @Inject
  @Path("administration/index.gtmpl")
  protected org.exoplatform.acceptance.frontend.templates.administration.index index;

  /**
   * Projects administration page
   */
  @Inject
  @Path("administration/project.gtmpl")
  protected org.exoplatform.acceptance.frontend.templates.administration.project project;

  /**
   * Administration home route
   */
  @View
  @Route("/admin")
  public Response.Content index() {
    return makeResponse(index);
  }

  /**
   * Projects administration route
   */
  @View
  @Route("/admin/project")
  @WithAssets({"projects-admin.js"})
  public Response.Content project() {
    return makeResponse(project);
  }

}