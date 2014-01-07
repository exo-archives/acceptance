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
package org.exoplatform.acceptance.security;

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 * This wrapper is used to redefine user's authorities from groups retrieved by crowd to application roles defined in {@link AppAuthority}
 *
 * @see AppAuthority
 */
public class CrowdUserDetailsWrapper implements ICrowdUserDetails {

  // This class is serializable
  private static final long serialVersionUID = 1L;

  private final CrowdUserDetails crowdUserDetails;

  private final CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper;

  public CrowdUserDetailsWrapper(CrowdUserDetails crowdUserDetails, CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper) {
    this.crowdUserDetails = crowdUserDetails;
    this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return grantedAuthoritiesMapper.mapAuthorities(crowdUserDetails.getAuthorities());
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  @Override
  public String getPassword() {
    return crowdUserDetails.getPassword();
  }

  /**
   * Returns the username used to authenticate the user. Cannot return <code>null</code>.
   *
   * @return the username (never <code>null</code>)
   */
  @Override
  public String getUsername() {
    return crowdUserDetails.getUsername();
  }

  /**
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
   * (ie expired)
   */
  @Override
  public boolean isAccountNonExpired() {
    return crowdUserDetails.isAccountNonExpired();
  }

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  @Override
  public boolean isAccountNonLocked() {
    return crowdUserDetails.isAccountNonLocked();
  }

  /**
   * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
   * authentication.
   *
   * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
   * valid (ie expired)
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return crowdUserDetails.isCredentialsNonExpired();
  }

  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  @Override
  public boolean isEnabled() {
    return crowdUserDetails.isEnabled();
  }

  /**
   * Returns the user's first name
   */
  @Override
  public String getFirstName() {
    return crowdUserDetails.getFirstName();
  }

  /**
   * Returns the user's last name
   */
  @Override
  public String getLastName() {
    return crowdUserDetails.getLastName();
  }

  /**
   * Returns the user's fullname
   */
  @Override
  public String getFullName() {
    return crowdUserDetails.getFullName();
  }

  /**
   * Returns the user's email
   */
  @Override
  public String getEmail() {
    return crowdUserDetails.getEmail();
  }

}
