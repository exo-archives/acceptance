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

public class Home extends BaseController {

  @Inject
  @Path("index.gtmpl")
  org.exoplatform.acceptance.frontend.templates.index index;

  @Inject
  @Path("deployments.gtmpl")
  org.exoplatform.acceptance.frontend.templates.deployments deployments;

  @Inject
  @Path("sources.gtmpl")
  org.exoplatform.acceptance.frontend.templates.sources sources;

  @Inject
  @Path("profile.gtmpl")
  org.exoplatform.acceptance.frontend.templates.profile profile;

  @Inject
  @Path("about.gtmpl")
  org.exoplatform.acceptance.frontend.templates.about about;

  @Inject
  @Path("signin.gtmpl")
  org.exoplatform.acceptance.frontend.templates.signin signin;

  @Inject
  @Path("error403.gtmpl")
  org.exoplatform.acceptance.frontend.templates.error403 error403;

  @View
  @Route("/")
  public Response.Content index() {
    return makeResponse(index);
  }

  @View
  @Route("/profile")
  public Response.Content profile() {
    return makeResponse(profile);
  }

  @View
  @Route("/about")
  public Response.Content about() {
    return makeResponse(about);
  }

  @View
  @Route("/deployments")
  public Response.Content deployments() {
    return makeResponse(deployments);
  }

  @View
  @Route("/sources")
  public Response.Content sources() {
    return makeResponse(sources);
  }

  @View
  @Route("/signin")
  @WithAssets("signin.css")
  public Response.Content signin(String error) {
    if (!Strings.isNullOrEmpty(error)) {
      flash.setError("Erroneous username or password !");
    }
    return makeResponse(signin.with().ok());
  }

  @Action
  @Route("/login")
  // fake method for frontend template compilation (ex: @{Home.login()}
  // this url is catch up by the spring security filter and the action method is never call
  public void login() {
  }

  @Action
  @Route("/logout")
  // fake method for frontend template compilation (ex: @{Home.logout()}
  // this url is catch up by the spring security filter and the action method is never call
  public void logout() {
  }

  @View
  @Route("/403")
  public Response.Content error403() {
    return makeResponse(error403.with().status(403));
  }

}
