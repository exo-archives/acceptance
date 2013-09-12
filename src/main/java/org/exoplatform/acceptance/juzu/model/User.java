/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
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
package org.exoplatform.acceptance.juzu.model;

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetails;
import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetailsService;
import javax.inject.Inject;
import javax.inject.Named;
import juzu.SessionScoped;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A User object which is a wrapper on the current CrowdUserDetails
 */
@Named("user")
@SessionScoped
public class User {

  @Inject
  @Named("crowdUserDetailsService")
  private CrowdUserDetailsService userDetailsService;

  private CrowdUserDetails userDetails;

  public String getUsername() {
    return getUserDetails().getUsername();
  }

  public String getFirstName() {
    return getUserDetails().getFirstName();
  }

  public String getLastName() {
    return getUserDetails().getLastName();
  }

  public String getEmail() {
    return getUserDetails().getEmail();
  }

  public String getFullName() {

    return getUserDetails().getFullName();
  }

  public boolean isAuthenticated() {
    try {
      return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    } catch (NullPointerException npe) {
      return false;
    }
  }

  private CrowdUserDetails getUserDetails() {
    if (userDetails != null) {
      return userDetails;
    }

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username;
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else if (principal instanceof CrowdUserDetails) {
      username = ((CrowdUserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    userDetails = userDetailsService.loadUserByUsername(username);

    return userDetails;
  }
}
