/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.acceptance.ui.controllers;


import javax.inject.Inject;
import juzu.Path;
import juzu.Response;
import juzu.Route;
import juzu.View;
import juzu.plugin.asset.Assets;

/**
 * Administration tasks controller
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
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
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/admin")
  public Response.Content index() {
    return makeResponse(index);
  }

  /**
   * Credentials administration route
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/admin/credential")
  @Assets({"administration/credential/index.js"})
  public Response.Content credential() {
    return makeResponse(credentialIndex);
  }

  /**
   * DVCS repositories administration route
   *
   * @return a {@link juzu.Response.Content} object.
   */
  @View
  @Route("/admin/vcs/repository")
  @Assets({"administration/vcs/repository.js"})
  public Response.Content vcsRepository() {
    return makeResponse(dvcsRepository);
  }
}
