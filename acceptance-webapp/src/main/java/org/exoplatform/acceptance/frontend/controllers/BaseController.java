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
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import juzu.Response;
import juzu.request.RequestContext;
import juzu.request.RequestLifeCycle;
import juzu.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.exoplatform.acceptance.frontend.model.Context;
import org.exoplatform.acceptance.frontend.model.CurrentUser;
import org.exoplatform.acceptance.frontend.model.Flash;
import org.xml.sax.SAXException;

/**
 * Common controller stuffs
 */
@Slf4j
public abstract class BaseController implements RequestLifeCycle {

  /**
   * Flash object to display messages
   */
  @Inject
  @Named("flash")
  protected Flash flash;

  /**
   * Current user
   */
  @Inject
  @Named("user")
  protected CurrentUser user;

  /**
   * Current execution context
   */
  @Inject
  @Named("context")
  protected Context context;

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
          .withHeaderTag("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/assets/org/exoplatform/acceptance/frontend/assets/images/favicon.ico\"></link>")
          ;
    } catch (ParserConfigurationException e) {
      log.error("Impossible to insert the favicon header in the page", e);
      return content;
    } catch (SAXException e) {
      log.error("Impossible to insert the favicon header in the page", e);
      return content;
    }
  }

  /**
   * <p>Signals to the controller that a request begins. During the invocation of this method, if a {@link juzu.Response}
   * is set on the request context, the request will be considered as terminated.</p>
   * <p/>
   * <p>When this method throws a runtime exception, a {@link juzu.Response.Error} response will be set on the request
   * context, thus terminating the request.</p>
   *
   * @param requestContext the request context
   */
  @Override
  public void beginRequest(RequestContext requestContext) {
    // Let's inject the requestContext into our context object
    context.setRequestContext(requestContext);
  }

  /**
   * <p>Signals to the controller that a request ends. During the invocation of this method, the response set during
   * the dispatch of the request is available via the {@link juzu.request.RequestContext#getResponse()} method, this
   * method is free to override it and provide a new response instead.</p>
   * <p/>
   * <p>When this method throws a runtime exception, a {@link juzu.Response.Error} response will be set on the request
   * requestContext, thus terminating the request.</p>
   *
   * @param requestContext the request requestContext
   */
  @Override
  public void endRequest(RequestContext requestContext) {
  }

}
