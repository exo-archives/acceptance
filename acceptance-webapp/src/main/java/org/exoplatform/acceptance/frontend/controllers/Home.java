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


import com.google.common.base.Strings;
import javax.inject.Inject;
import juzu.Action;
import juzu.Path;
import juzu.Response;
import juzu.Route;
import juzu.View;
import juzu.plugin.asset.WithAssets;
import org.apache.commons.httpclient.HttpStatus;

public class Home extends BaseController {

  /**
   * Application homepage
   */
  @Inject
  @Path("index.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.index index;

  /**
   * Deployments list page
   */
  @Inject
  @Path("deployments.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.deployments deployments;

  /**
   * Sources list page
   */
  @Inject
  @Path("sources.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.sources sources;

  /**
   * User profile page
   */
  @Inject
  @Path("profile.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.profile profile;

  /**
   * About the app page
   */
  @Inject
  @Path("about.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.about about;

  /**
   * Signin page
   */
  @Inject
  @Path("signin.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.signin signin;

  /**
   * Forbidden access page (403)
   */
  @Inject
  @Path("error403.gtmpl")
  private org.exoplatform.acceptance.frontend.templates.error403 error403;

  /**
   * Application homepage route
   */
  @View
  @Route("/")
  public Response.Content index() {
    return makeResponse(index);
  }

  /**
   * User profile route
   */
  @View
  @Route("/profile")
  public Response.Content profile() {
    return makeResponse(profile);
  }

  /**
   * About the app route
   */
  @View
  @Route("/about")
  public Response.Content about() {
    return makeResponse(about);
  }

  /**
   * Deployments list route
   */
  @View
  @Route("/deployments")
  public Response.Content deployments() {
    return makeResponse(deployments);
  }

  /**
   * Sources list page
   */
  @View
  @Route("/sources")
  public Response.Content sources() {
    return makeResponse(sources);
  }

  /**
   * Signin route
   */
  @View
  @Route("/signin")
  @WithAssets("signin.css")
  public Response.Content signin(String error) {
    if (!Strings.isNullOrEmpty(error)) {
      getFlash().setError("Erroneous username or password !");
    }
    return makeResponse(signin.with().ok());
  }

  /**
   * Login page
   * fake method for frontend template compilation (ex: @{Home.login()}
   * this url is catch up by the spring security filter and the action method is never call
   */
  @Action
  @Route("/login")
  public void login() {
  }

  /**
   * Logout page
   * fake method for frontend template compilation (ex: @{Home.logout()}
   * this url is catch up by the spring security filter and the action method is never call
   */
  @Action
  @Route("/logout")
  public void logout() {
  }

  /**
   * Forbidden access route
   */
  @View
  @Route("/403")
  public Response.Content error403() {
    return makeResponse(error403.with().status(HttpStatus.SC_FORBIDDEN));
  }

}
