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

import com.atlassian.crowd.integration.springsecurity.user.CrowdUserDetailsServiceImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This Spring {@link UserDetailsService} is used to wrap the user created by the {@link CrowdUserDetailsServiceImpl} to replace its crowd groups by our application roles
 *
 * @see CrowdGrantedAuthoritiesMapper
 */
public class CrowdUserDetailsServiceWrapper implements UserDetailsService {

  private final CrowdUserDetailsServiceImpl crowdUserDetailsServiceImpl;

  private final CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper;

  public CrowdUserDetailsServiceWrapper(CrowdUserDetailsServiceImpl crowdUserDetailsServiceImpl, CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper) {
    this.crowdUserDetailsServiceImpl = crowdUserDetailsServiceImpl;
    this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
  }

  /**
   * Locates the user based on the username. In the actual implementation, the search may possibly be case
   * insensitive, or case insensitive depending on how the implementation instance is configured. In this case, the
   * <code>UserDetails</code> object that comes back may have a username that is of a different case than what was
   * actually requested..
   *
   * @param username the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
   */
  @Override
  public ICrowdUserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    return new CrowdUserDetailsWrapper(crowdUserDetailsServiceImpl.loadUserByUsername(username), grantedAuthoritiesMapper);
  }

}
