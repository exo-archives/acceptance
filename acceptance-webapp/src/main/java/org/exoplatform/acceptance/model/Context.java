package org.exoplatform.acceptance.model;

import java.io.Serializable;


import javax.inject.Named;
import juzu.request.RequestContext;

@Named("context")
/**
 * Current request context
 */
public class Context implements Serializable {

  transient private RequestContext requestContext;

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
}
