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

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface to represent a User managed by Crowd.
 * CrowdUserDetails doesn't implement a dedicated interface to easily provide another implementation (without using a mock library)
 */
public interface ICrowdUserDetails extends UserDetails {
  /**
   * Returns the user's first name
   */
  String getFirstName();

  /**
   * Returns the user's last name
   */
  String getLastName();

  /**
   * Returns the user's fullname
   */
  String getFullName();

  /**
   * Returns the user's email
   */
  String getEmail();
}
