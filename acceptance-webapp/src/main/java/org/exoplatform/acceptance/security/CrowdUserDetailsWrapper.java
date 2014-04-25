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
 * This wrapper is used to redefine user's authorities from groups retrieved by crowd to application roles defined in {@link org.exoplatform.acceptance.security.AppAuthority}
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @see AppAuthority
 * @since 2.0.0
 */
public class CrowdUserDetailsWrapper implements ICrowdUserDetails {


  /**
   * Constant <code>serialVersionUID=1141084636446878309L</code>
   */
  private static final long serialVersionUID = 1141084636446878309L;

  private final CrowdUserDetails crowdUserDetails;

  private final CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper;

  /**
   * <p>Constructor for CrowdUserDetailsWrapper.</p>
   *
   * @param crowdUserDetails         a {@link com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails} object.
   * @param grantedAuthoritiesMapper a {@link org.exoplatform.acceptance.security.CrowdGrantedAuthoritiesMapper} object.
   * @since 2.0.0
   */
  public CrowdUserDetailsWrapper(CrowdUserDetails crowdUserDetails, CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper) {
    this.crowdUserDetails = crowdUserDetails;
    this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return grantedAuthoritiesMapper.mapAuthorities(crowdUserDetails.getAuthorities());
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the password used to authenticate the user.
   */
  @Override
  public String getPassword() {
    return crowdUserDetails.getPassword();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the username used to authenticate the user. Cannot return <code>null</code>.
   */
  @Override
  public String getUsername() {
    return crowdUserDetails.getUsername();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   */
  @Override
  public boolean isAccountNonExpired() {
    return crowdUserDetails.isAccountNonExpired();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   */
  @Override
  public boolean isAccountNonLocked() {
    return crowdUserDetails.isAccountNonLocked();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
   * authentication.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return crowdUserDetails.isCredentialsNonExpired();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   */
  @Override
  public boolean isEnabled() {
    return crowdUserDetails.isEnabled();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the user's first name
   */
  @Override
  public String getFirstName() {
    return crowdUserDetails.getFirstName();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the user's last name
   */
  @Override
  public String getLastName() {
    return crowdUserDetails.getLastName();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the user's fullname
   */
  @Override
  public String getFullName() {
    return crowdUserDetails.getFullName();
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns the user's email
   */
  @Override
  public String getEmail() {
    return crowdUserDetails.getEmail();
  }

}
