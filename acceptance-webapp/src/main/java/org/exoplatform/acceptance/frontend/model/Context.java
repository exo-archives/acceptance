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

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import juzu.request.RequestContext;
import org.exoplatform.acceptance.backend.model.Configuration;

/**
 * Current request context
 */
@Named("context")
public class Context implements Serializable {

  @Inject
  @Named("configuration")
  Configuration configuration;

  private transient RequestContext requestContext;

  public void setRequestContext(RequestContext requestContext) {
    this.requestContext = requestContext;
  }

  /**
   * Checks if the the application is using and https scheme
   *
   * @return true if the http cotext scheme is https
   */
  public boolean isSecured() {
    return requestContext.getHttpContext().getScheme().equalsIgnoreCase("https");
  }

  /**
   * Simple searches for an exactly matching {@link org.springframework.security.core.GrantedAuthority.getAuthority()}.
   * Will always return false if the SecurityContextHolder contains an Authentication with nullprincipal and/or GrantedAuthority[] objects.
   *
   * @param role the GrantedAuthorityString representation to check for
   * @return true if an exact (case sensitive) matching granted authority is located, false otherwise
   */
  public boolean isUserInRole(String role) {
    // NOT WORKING FOR NOW. requestContext.getSecurityContext() always null in Juzu.
    return requestContext.getSecurityContext() != null ? requestContext.getSecurityContext().isUserInRole(role) : false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Context)) return false;

    Context context = (Context) o;

    if (requestContext != null ? !requestContext.equals(context.requestContext) : context.requestContext != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return requestContext != null ? requestContext.hashCode() : 0;
  }

  public String getVersion() {
    return configuration.getVersion();
  }

  public String getAdminRole() {
    return configuration.getAdminRole();
  }

  public String getUserRole() {
    return configuration.getUserRole();
  }

  public Date getBuildDate() {
    return configuration.getBuildDate();
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
}
