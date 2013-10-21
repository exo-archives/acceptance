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
package org.exoplatform.acceptance.ui.model;

import org.exoplatform.acceptance.service.ConfigurationService;

import java.net.URL;
import java.text.DateFormat;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Named;
import juzu.request.RequestContext;

/**
 * Current request context
 */
@Named("context")
public class Context {

  @Inject
  private ConfigurationService configurationService;

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
    return configurationService.getVersion();
  }

  public String getBuildDate() {
    return configurationService.getBuildDate() != null ? DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(
        configurationService.getBuildDate()) : "";
  }

  public String getBuildTime() {
    return configurationService.getBuildDate() != null ? DateFormat.getTimeInstance(DateFormat.LONG, Locale.US).format(
        configurationService.getBuildDate()) : "";
  }

  public String getOrganizationUrl() {
    return configurationService.getOrganizationUrl();
  }

  public URL getScmUrl() {
    return configurationService.getScmUrl();
  }

  public String getScmRevision() {
    return configurationService.getScmRevision();
  }

  public String getOrganizationName() {
    return configurationService.getOrganizationName();
  }

  public String getScmRevisionUrl() {
    return configurationService.getScmRevisionUrl();
  }

  public String getInceptionYear() {
    return configurationService.getInceptionYear();
  }

  public String getShortScmRevision() {
    return configurationService.getScmRevision() != null ? configurationService.getScmRevision().substring(0, 6) : "";
  }

}
