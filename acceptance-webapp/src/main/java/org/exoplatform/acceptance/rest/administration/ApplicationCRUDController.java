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

import org.exoplatform.acceptance.model.Application;
import org.exoplatform.acceptance.service.ApplicationService;

import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to manage REST services for applications
 */
@Controller
@RequestMapping(value = "admin/application", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationCRUDController extends CRUDController<Application, String> {

  @Inject
  private ApplicationService applicationService;

  @Override
  protected ApplicationService getCRUDService() {
    return applicationService;
  }

}
