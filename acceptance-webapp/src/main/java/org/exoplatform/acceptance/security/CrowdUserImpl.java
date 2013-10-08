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
package org.exoplatform.acceptance.security;

import java.util.Collection;

import com.atlassian.crowd.integration.soap.SOAPPrincipal;
import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CrowdUserImpl implements CrowdUser {

  private CrowdUserDetails crowdUserDetails;

  public CrowdUserImpl(CrowdUserDetails crowdUserDetails) {
    this.crowdUserDetails = crowdUserDetails;
  }

  @Override
  public String getFullName() {
    return crowdUserDetails.getFullName();
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return crowdUserDetails.getAuthorities();
  }

  public SOAPPrincipal getRemotePrincipal() {
    return crowdUserDetails.getRemotePrincipal();
  }

  @Override
  public String getPassword() {
    return crowdUserDetails.getPassword();
  }

  @Override
  public String getUsername() {
    return crowdUserDetails.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return crowdUserDetails.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return crowdUserDetails.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return crowdUserDetails.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return crowdUserDetails.isEnabled();
  }

  @Override
  public String getFirstName() {
    return crowdUserDetails.getFirstName();
  }

  @Override
  public String getLastName() {
    return crowdUserDetails.getLastName();
  }

  @Override
  public String getEmail() {
    return crowdUserDetails.getEmail();
  }

  public String getAttribute(String attributeName) {
    return crowdUserDetails.getAttribute(attributeName);
  }

  /**
   * Is the user authenticated ?
   *
   * @return true if the user is authenticated
   */
  @Override
  public boolean isAuthenticated() {
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    } else {
      return false;
    }
  }
}
