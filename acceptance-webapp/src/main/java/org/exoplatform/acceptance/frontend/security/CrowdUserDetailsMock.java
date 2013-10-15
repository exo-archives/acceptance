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
package org.exoplatform.acceptance.frontend.security;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * This is a POJO to implement a CrowdUserDetails
 */
@Data
public class CrowdUserDetailsMock implements ICrowdUserDetails {
  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private String email;

  private boolean accountNonExpired = true;

  private boolean accountNonLocked = true;

  private boolean credentialsNonExpired = true;

  private boolean enabled = true;

  private Collection<GrantedAuthority> authorities = new ArrayList<>();

  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }
}
