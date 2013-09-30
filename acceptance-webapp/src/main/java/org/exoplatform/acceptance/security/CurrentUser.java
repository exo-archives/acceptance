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

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Named("user")
public class CurrentUser implements CrowdUser {
  private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUser.class);

  @Inject
  @Named("crowdUserDetailsService")
  private UserDetailsService userDetailsService;

  private CrowdUser currentUser;

  /**
   * Returns the user's first name
   */
  @Override
  public String getFirstName() {
    return getCurrentUser().getFirstName();
  }

  /**
   * Returns the user's last name
   */
  @Override
  public String getLastName() {
    return getCurrentUser().getLastName();
  }

  /**
   * Returns the user's fullname
   */
  @Override
  public String getFullName() {
    return getCurrentUser().getFullName();
  }

  /**
   * Returns the user's email
   */
  @Override
  public String getEmail() {
    return getCurrentUser().getEmail();
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

  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getCurrentUser().getAuthorities();
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  @Override
  public String getPassword() {
    return getCurrentUser().getPassword();
  }

  /**
   * Returns the username used to authenticate the user. Cannot return <code>null</code>.
   *
   * @return the username (never <code>null</code>)
   */
  @Override
  public String getUsername() {
    if (isAuthenticated()) {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {
        return ((UserDetails) principal).getUsername();
      } else {
        return principal.toString();
      }
    } else throw new UsernameNotFoundException("User not authenticated");
  }

  /**
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
   * (ie expired)
   */
  @Override
  public boolean isAccountNonExpired() {
    return getCurrentUser().isAccountNonExpired();
  }

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  @Override
  public boolean isAccountNonLocked() {
    return getCurrentUser().isAccountNonLocked();
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
    return getCurrentUser().isCredentialsNonExpired();
  }

  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  @Override
  public boolean isEnabled() {
    return getCurrentUser().isEnabled();
  }

  /**
   * Provides a fully-constructed and injected instance of {@code T}.
   *
   * @throws UsernameNotFoundException if the injector encounters an error while
   *                                   providing an instance. For example, if an injectable member on
   * @throws RuntimeException          if the injector encounters an error while
   *                                   providing an instance. For example, if an injectable member on
   *                                   {@code T} throws an exception, the injector may wrap the exception
   *                                   and throw it to the caller of {@code get()}. Callers should not try
   *                                   to handle such exceptions as the behavior may vary across injector
   *                                   implementations and even different configurations of the same injector.
   */
  private CrowdUser getCurrentUser() {
    if (currentUser == null) {
      // Try to load it
      if (isAuthenticated()) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername());
        if (userDetails instanceof CrowdUserDetails) {
          currentUser = new CrowdUserImpl((CrowdUserDetails) userDetails);
        } else if (userDetails instanceof CrowdUserMock) {
          currentUser = (CrowdUserMock) userDetails;
        } else {
          LOGGER.error("Unknown UserDetails implementation : {}", userDetails.getClass().getName());
        }
      }
    }
    return currentUser;
  }

  public boolean hasRole(String role) {
    for (GrantedAuthority authority : getCurrentUser().getAuthorities()) {
      if (authority.getAuthority().equalsIgnoreCase(role)) return true;
    }
    return false;
  }

}
