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
package org.exoplatform.acceptance.ui.controllers;


import static org.apache.http.HttpStatus.SC_FORBIDDEN;

import com.google.common.base.Strings;
import javax.inject.Inject;
import juzu.Action;
import juzu.Path;
import juzu.Response;
import juzu.Route;
import juzu.View;
import juzu.plugin.asset.Assets;

/**
 * <p>HomeController class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class HomeController extends BaseController {

  /**
   * Application homepage
   */
  @Inject
  @Path("index.gtmpl")
  private org.exoplatform.acceptance.ui.templates.index index;

  /**
   * Sources list page
   */
  @Inject
  @Path("sources.gtmpl")
  private org.exoplatform.acceptance.ui.templates.sources sources;

  /**
   * Build jobs list page
   */
  @Inject
  @Path("buildjobs.gtmpl")
  private org.exoplatform.acceptance.ui.templates.buildjobs buildjobs;

  /**
   * Deployments list page
   */
  @Inject
  @Path("deployments.gtmpl")
  private org.exoplatform.acceptance.ui.templates.deployments deployments;

  /**
   * Forbidden access page (403)
   */
  @Inject
  @Path("error403.gtmpl")
  private org.exoplatform.acceptance.ui.templates.error403 error403;

  /**
   * Application homepage route
   *
   * @param error a {@link java.lang.String} object.
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/")
  public Response.Content index(String error) {
    if (!Strings.isNullOrEmpty(error)) {
      getFlash().setError("Erroneous username or password !");
    }
    return makeResponse(index);
  }

  /**
   * Sources list page
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/sources")
  @Assets({"sources.js"})
  public Response.Content sources() {
    return makeResponse(sources);
  }

  /**
   * Build jobs list page
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/buildjobs")
  public Response.Content buildjobs() {
    return makeResponse(buildjobs);
  }

  /**
   * Deployments list route
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/deployments")
  public Response.Content deployments() {
    return makeResponse(deployments);
  }

  /**
   * Login page
   * fake method for juzu template compilation (ex: @{HomeController.login()}
   * this url is catch up by the spring security filter and the action method is never call
   */
  @Action
  @Route("/login")
  public void login() {
  }

  /**
   * Logout page
   * fake method for juzu template compilation (ex: @{HomeController.logout()}
   * this url is catch up by the spring security filter and the action method is never call
   */
  @Action
  @Route("/logout")
  public void logout() {
  }

  /**
   * Forbidden access route
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/403")
  public Response.Content error403() {
    return makeResponse(error403.with().status(SC_FORBIDDEN));
  }

}
