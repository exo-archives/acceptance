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
package org.exoplatform.acceptance.controllers;


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
  org.exoplatform.acceptance.templates.index index;

  @Inject
  @Path("profile.gtmpl")
  org.exoplatform.acceptance.templates.profile profile;

  @Inject
  @Path("about.gtmpl")
  org.exoplatform.acceptance.templates.about about;

  @Inject
  @Path("signin.gtmpl")
  org.exoplatform.acceptance.templates.signin signin;

  @Inject
  @Path("error403.gtmpl")
  org.exoplatform.acceptance.templates.error403 error403;

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
  public void login() {
    // fake method for juzu template compilation (ex: @{Home.login()}
    // this url is catch up by the spring security filter and the action method is never call
  }

  @Action
  @Route("/logout")
  public void logout() {
    // fake method for juzu template compilation (ex: @{Home.logout()}
    // this url is catch up by the spring security filter and the action method is never call
  }

  @View
  @Route("/403")
  public Response.Content error403() {
    return makeResponse(error403.with().status(403));
  }

}
