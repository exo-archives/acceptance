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
package org.exoplatform.acceptance.model;

import java.util.Collection;

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import javax.inject.Inject;
import javax.inject.Named;
import juzu.SessionScoped;
import lombok.Getter;
import org.exoplatform.acceptance.security.CrowdUser;
import org.exoplatform.acceptance.security.ICrowdUser;
import org.exoplatform.acceptance.security.MockedUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * A User object which is a wrapper on the current CrowdUserDetails
 */
@Named("user")
@SessionScoped
public class User {

  @Getter(lazy = true)
  private final ICrowdUser userImpl = loadUserDetails();

  @Inject
  @Named("crowdUserDetailsService")
  private UserDetailsService userDetailsService;

  public boolean isAuthenticated() {
    try {
      return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    } catch (NullPointerException npe) {
      return false;
    }
  }

  private ICrowdUser loadUserDetails() {
    if (isAuthenticated()) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername());
      if (userDetails instanceof CrowdUserDetails)
        return new CrowdUser((CrowdUserDetails) userDetails);
      else if (userDetails instanceof MockedUser)
        return (MockedUser) userDetails;
      else
        throw new RuntimeException("Unknown UserDetails implementation : " + userDetails.getClass().getName());
    } else return null;
  }

  private String getUsername() {
    if (isAuthenticated()) {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {
        return ((UserDetails) principal).getUsername();
      } else {
        return principal.toString();
      }
    } else return null;
  }

  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getUserImpl().getAuthorities();
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  public String getPassword() {
    return getUserImpl().getPassword();
  }

  /**
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
   * (ie expired)
   */
  public boolean isAccountNonExpired() {
    return getUserImpl().isAccountNonExpired();
  }

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  public boolean isAccountNonLocked() {
    return getUserImpl().isAccountNonLocked();
  }

  /**
   * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
   * authentication.
   *
   * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
   * valid (ie expired)
   */
  public boolean isCredentialsNonExpired() {
    return getUserImpl().isCredentialsNonExpired();
  }

  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  public boolean isEnabled() {
    return getUserImpl().isEnabled();
  }

  /**
   * Returns the user's first name
   */
  public String getFirstName() {
    return getUserImpl().getFirstName();
  }

  /**
   * Returns the user's fullname
   */
  public String getFullName() {
    return getUserImpl().getFullName();
  }

  /**
   * Returns the user's last name
   */
  public String getLastName() {
    return getUserImpl().getLastName();
  }
}
