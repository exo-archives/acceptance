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
package org.exoplatform.acceptance.frontend.model;

import java.net.URL;
import java.text.DateFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import juzu.request.RequestContext;
import org.exoplatform.acceptance.backend.model.Configuration;

/**
 * Current request context
 */
@Named("context")
public class Context {

  @Inject
  @Named("configuration")
  private Configuration configuration;

  private RequestContext requestContext;

  public void setRequestContext(RequestContext requestContext) {
    this.requestContext = requestContext;
  }

  /**
   * Checks if the the application is using and https scheme
   *
   * @return true if the http context scheme is https
   */
  public boolean isSecured() {
    return requestContext.getHttpContext().getScheme().equalsIgnoreCase("https");
  }

  public String getVersion() {
    return configuration.getVersion();
  }

  public String getBuildDate() {
    return configuration.getBuildDate() != null ? DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(configuration.getBuildDate()) : "";
  }

  public String getBuildTime() {
    return configuration.getBuildDate() != null ? DateFormat.getTimeInstance(DateFormat.LONG, Locale.US).format(configuration.getBuildDate()) : "";
  }

  public String getOrganizationUrl() {
    return configuration.getOrganizationUrl();
  }

  public URL getScmUrl() {
    return configuration.getScmUrl();
  }

  public String getScmRevision() {
    return configuration.getScmRevision();
  }

  public String getOrganizationName() {
    return configuration.getOrganizationName();
  }

  public String getScmRevisionUrl() {
    return configuration.getScmRevisionUrl();
  }

  public String getInceptionYear() {
    return configuration.getInceptionYear();
  }

  public String getShortScmRevision() {
    return configuration.getScmRevision() != null ? configuration.getScmRevision().substring(0, 6) : "";
  }

}
