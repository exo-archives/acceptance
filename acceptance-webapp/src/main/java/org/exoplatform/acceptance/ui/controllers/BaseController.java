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

import org.exoplatform.acceptance.ui.model.Context;
import org.exoplatform.acceptance.ui.model.CurrentUser;
import org.exoplatform.acceptance.ui.model.Flash;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import juzu.Response;
import juzu.request.RequestContext;
import juzu.request.RequestLifeCycle;
import juzu.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Common controller stuffs
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public abstract class BaseController implements RequestLifeCycle {
  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

  /**
   * Flash object to display messages
   */
  @Inject
  @Named("flash")
  private Flash flash;

  /**
   * Current user
   */
  @Inject
  @Named("user")
  private CurrentUser user;

  /**
   * Current execution context
   */
  @Inject
  @Named("context")
  private Context context;

  /**
   * <p>Getter for the field <code>flash</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.ui.model.Flash} object.
   */
  public Flash getFlash() {
    return flash;
  }

  /**
   * <p>Setter for the field <code>flash</code>.</p>
   *
   * @param flash a {@link org.exoplatform.acceptance.ui.model.Flash} object.
   */
  public void setFlash(Flash flash) {
    this.flash = flash;
  }

  /**
   * <p>Getter for the field <code>user</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.ui.model.CurrentUser} object.
   */
  public CurrentUser getUser() {
    return user;
  }

  /**
   * <p>Setter for the field <code>user</code>.</p>
   *
   * @param user a {@link org.exoplatform.acceptance.ui.model.CurrentUser} object.
   */
  public void setUser(CurrentUser user) {
    this.user = user;
  }

  /**
   * <p>Getter for the field <code>context</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.ui.model.Context} object.
   */
  public Context getContext() {
    return context;
  }

  /**
   * <p>Setter for the field <code>context</code>.</p>
   *
   * @param context a {@link org.exoplatform.acceptance.ui.model.Context} object.
   */
  public void setContext(Context context) {
    this.context = context;
  }

  /**
   * <p>render.</p>
   *
   * @param template a {@link juzu.template.Template} object.
   */
  protected void render(Template template) {
    render(template.with());
  }

  /**
   * <p>render.</p>
   *
   * @param builder a {@link juzu.template.Template.Builder} object.
   */
  protected void render(Template.Builder builder) {
    builder.ok();
  }

  /**
   * <p>makeResponse.</p>
   *
   * @param template a {@link juzu.template.Template} object.
   * @return a {@link juzu.Response.Content} object.
   */
  protected Response.Content makeResponse(Template template) {
    return makeResponse(template.with());
  }

  /**
   * <p>makeResponse.</p>
   *
   * @param builder a {@link juzu.template.Template.Builder} object.
   * @return a {@link juzu.Response.Content} object.
   */
  protected Response.Content makeResponse(Template.Builder builder) {
    return makeResponse(builder.ok());
  }

  /**
   * <p>makeResponse.</p>
   *
   * @param content a {@link juzu.Response.Content} object.
   * @return a {@link juzu.Response.Content} object.
   */
  protected Response.Content makeResponse(Response.Content content) {
    try {
      return content
          .withHeaderTag(
              "<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/assets/org/exoplatform/acceptance/ui/assets/images/favicon.ico\"></link>")
          .withMetaTag("viewport", "width=device-width, initial-scale=1.0")
          ;
    } catch (ParserConfigurationException e) {
      LOGGER.error("Impossible to insert the favicon header in the page", e);
      return content;
    } catch (SAXException e) {
      LOGGER.error("Impossible to insert the favicon header in the page", e);
      return content;
    }
  }

  /**
   * {@inheritDoc}
   * <p/>
   * <p>Signals to the controller that a request begins. During the invocation of this method, if a {@link juzu.Response}
   * is set on the request context, the request will be considered as terminated.</p>
   * <p/>
   * <p>When this method throws a runtime exception, a {@link juzu.Response.Error} response will be set on the request
   * context, thus terminating the request.</p>
   */
  @Override
  public void beginRequest(RequestContext requestContext) {
    // Let's inject the requestContext into our context object
    context.setRequestContext(requestContext);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * <p>Signals to the controller that a request ends. During the invocation of this method, the response set during
   * the dispatch of the request is available via the {@link juzu.request.RequestContext#getResponse()} method, this
   * method is free to override it and provide a new response instead.</p>
   * <p/>
   * <p>When this method throws a runtime exception, a {@link juzu.Response.Error} response will be set on the request
   * requestContext, thus terminating the request.</p>
   */
  @Override
  public void endRequest(RequestContext requestContext) {
  }

}
