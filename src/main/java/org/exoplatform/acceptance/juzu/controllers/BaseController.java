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
package org.exoplatform.acceptance.juzu.controllers;

import javax.inject.Inject;
import javax.inject.Named;
import juzu.Response;
import juzu.template.Template;
import lombok.Getter;
import org.exoplatform.acceptance.juzu.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public abstract class BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

  @Named("user")
  @Inject
  @Getter
  private User user;

  protected void render(Template template) {
    this.render(template.with());
  }

  protected void render(Template.Builder builder) {
    builder.ok();
  }

  protected Response.Content makeResponse(Template template) {
    return this.makeResponse(template.with());
  }

  protected Response.Content makeResponse(Template.Builder builder) {
    return this.makeResponse(builder.ok());
  }

  protected Response.Content makeResponse(Response.Content content) {
//    try {
    return content
        // FIXME Currently this is not working with Juzu 0.7.0-beta15 (we need a fix)
//          .withHeader("viewport", "width=device-width, initial-scale=1.0")
//          .withHeaderTag("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/a/favicon.ico\"></link>")
        ;
//    } catch (ParserConfigurationException e) {
//      LOGGER.error("Impossible to insert the favicon header in the page", e);
//      return content;
//    } catch (SAXException e) {
//      LOGGER.error("Impossible to insert the favicon header in the page", e);
//      return content;
//    }
  }

}
