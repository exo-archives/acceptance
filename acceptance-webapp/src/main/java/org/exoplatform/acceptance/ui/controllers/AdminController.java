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
package org.exoplatform.acceptance.ui.controllers;


import javax.inject.Inject;
import juzu.Path;
import juzu.Response;
import juzu.Route;
import juzu.View;
import juzu.plugin.asset.WithAssets;

/**
 * Administration tasks controller
 */
public class AdminController extends BaseController {

  /**
   * Administration home page
   */
  @Inject
  @Path("administration/index.gtmpl")
  private org.exoplatform.acceptance.ui.templates.administration.index index;
  /**
   * Credentials administration page
   */
  @Inject
  @Path("administration/credential/index.gtmpl")
  private org.exoplatform.acceptance.ui.templates.administration.credential.index credentialIndex;
  /**
   * DVCS Repositories administration page
   */
  @Inject
  @Path("administration/vcs/repository.gtmpl")
  private org.exoplatform.acceptance.ui.templates.administration.vcs.repository dvcsRepository;

  /**
   * Administration home route
   */
  @View
  @Route("/admin")
  public Response.Content index() {
    return makeResponse(index);
  }

  /**
   * Credentials administration route
   */
  @View
  @Route("/admin/credential")
  @WithAssets({"credential-admin.js"})
  public Response.Content credential() {
    return makeResponse(credentialIndex);
  }

  /**
   * DVCS repositories administration route
   */
  @View
  @Route("/admin/vcs/repository")
  @WithAssets({"vcsRepository-admin.js"})
  public Response.Content vcsRepository() {
    return makeResponse(dvcsRepository);
  }
}
