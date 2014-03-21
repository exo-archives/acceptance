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

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This Spring {@link org.springframework.security.core.userdetails.UserDetailsService} defines two accounts to use in the app for tests and dev.
 * An account with administrator role (admin/admin) and one with user role (user/user)
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class CrowdUserDetailsServiceMock implements UserDetailsService {
  private final CrowdUserDetailsMock administrator = new CrowdUserDetailsMock();

  private final CrowdUserDetailsMock user = new CrowdUserDetailsMock();

  /**
   * <p>Constructor for CrowdUserDetailsServiceMock.</p>
   */
  public CrowdUserDetailsServiceMock() {
    administrator.setUsername("admin");
    administrator.setPassword("admin");
    administrator.setFirstName("Super");
    administrator.setLastName("Administrator");
    administrator.setEmail("noreply@exoplatform.com");
    administrator.getAuthorities().add(AppAuthority.ROLE_USER);
    administrator.getAuthorities().add(AppAuthority.ROLE_ADMIN);
    user.setUsername("user");
    user.setPassword("user");
    user.setFirstName("Famous");
    user.setLastName("User");
    user.setEmail("noreply@exoplatform.com");
    user.getAuthorities().add(AppAuthority.ROLE_USER);
  }

  /**
   * {@inheritDoc}
   *
   * Locates the user based on the username. In the actual implementation, the search may possibly be case
   * insensitive, or case insensitive depending on how the implementation instance is configured. In this case, the
   * <code>UserDetails</code> object that comes back may have a username that is of a different case than what was
   * actually requested..
   */
  @Override
  public ICrowdUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (user.getUsername().equals(username)) {
      return user;
    } else if (administrator.getUsername().equals(username)) {
      return administrator;
    } else {
      throw new UsernameNotFoundException("Unknown user : " + username);
    }
  }
}
