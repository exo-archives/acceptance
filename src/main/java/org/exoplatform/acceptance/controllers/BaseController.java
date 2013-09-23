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
package org.exoplatform.acceptance.controllers;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import juzu.Response;
import juzu.plugin.asset.WithAssets;
import juzu.template.Template;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.exoplatform.acceptance.model.Settings;
import org.exoplatform.acceptance.model.User;
import org.xml.sax.SAXException;

/**
 *
 */
@WithAssets({"acceptance.js", "acceptance.css"})
@Slf4j
public abstract class BaseController {

  @Inject
  @Getter
  private User user;

  @Inject
  @Getter
  private Settings settings;

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
    try {
      return content
          .withHeaderTag("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/favicon.ico\"></link>")
          ;
    } catch (ParserConfigurationException e) {
      log.error("Impossible to insert the favicon header in the page", e);
      return content;
    } catch (SAXException e) {
      log.error("Impossible to insert the favicon header in the page", e);
      return content;
    }
  }

}
