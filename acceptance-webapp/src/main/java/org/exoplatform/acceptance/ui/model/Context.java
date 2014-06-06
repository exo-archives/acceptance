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
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named("context")
public class Context {

  @Inject
  private ConfigurationService configurationService;

  private RequestContext requestContext;

  /**
   * <p>Setter for the field <code>requestContext</code>.</p>
   *
   * @param requestContext a {@link juzu.request.RequestContext} object.
   */
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

  /**
   * <p>getVersion.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getVersion() {
    return configurationService.getVersion();
  }

  /**
   * <p>getBuildDate.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getBuildDate() {
    return configurationService.getBuildDate() != null ? DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(
        configurationService.getBuildDate()) : "";
  }

  /**
   * <p>getBuildTime.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getBuildTime() {
    return configurationService.getBuildDate() != null ? DateFormat.getTimeInstance(DateFormat.LONG, Locale.US).format(
        configurationService.getBuildDate()) : "";
  }

  /**
   * <p>getOrganizationUrl.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getOrganizationUrl() {
    return configurationService.getOrganizationUrl();
  }

  /**
   * <p>getScmUrl.</p>
   *
   * @return a {@link java.net.URL} object.
   */
  public URL getScmUrl() {
    return configurationService.getScmUrl();
  }

  /**
   * <p>getScmRevision.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getScmRevision() {
    return configurationService.getScmRevision();
  }

  /**
   * <p>getOrganizationName.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getOrganizationName() {
    return configurationService.getOrganizationName();
  }

  /**
   * <p>getScmRevisionUrl.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getScmRevisionUrl() {
    return configurationService.getScmRevisionUrl();
  }

  /**
   * <p>getInceptionYear.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getInceptionYear() {
    return configurationService.getInceptionYear();
  }

  /**
   * <p>getShortScmRevision.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getShortScmRevision() {
    return configurationService.getScmRevision() != null ? configurationService.getScmRevision().substring(0, 6) : "";
  }

}
