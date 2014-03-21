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

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * A basic implementation of {@link org.springframework.security.authentication.AuthenticationProvider} which is using our {@link org.exoplatform.acceptance.security.CrowdAuthenticationProviderMock} to manage users.
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class CrowdAuthenticationProviderMock implements AuthenticationProvider {

  private final CrowdUserDetailsServiceMock crowdUserDetailsServiceMock;

  /**
   * <p>Constructor for CrowdAuthenticationProviderMock.</p>
   *
   * @param userDetailsService a {@link org.exoplatform.acceptance.security.CrowdUserDetailsServiceMock} object.
   */
  public CrowdAuthenticationProviderMock(CrowdUserDetailsServiceMock userDetailsService) {
    this.crowdUserDetailsServiceMock = userDetailsService;
  }

  /**
   * {@inheritDoc}
   *
   * Performs authentication with the same contract as {@link
   * org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)}.
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String password = authentication.getCredentials().toString();
    try {
      UserDetails user = crowdUserDetailsServiceMock.loadUserByUsername(name);
      if (user.getPassword().equals(password)) {
        return new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities());
      } else {
        throw new BadCredentialsException("Invalid username or password");
      }
    } catch (UsernameNotFoundException unnfe) {
      throw new BadCredentialsException("Invalid username or password", unnfe);
    }
  }

  /**
   * {@inheritDoc}
   *
   * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the indicated
   * <Code>Authentication</code> object.
   * <p>
   * Returning <code>true</code> does not guarantee an <code>AuthenticationProvider</code> will be able to
   * authenticate the presented instance of the <code>Authentication</code> class. It simply indicates it can support
   * closer evaluation of it. An <code>AuthenticationProvider</code> can still return <code>null</code> from the
   * {@link #authenticate(Authentication)} method to indicate another <code>AuthenticationProvider</code> should be
   * tried.
   * </p>
   * <p>Selection of an <code>AuthenticationProvider</code> capable of performing authentication is
   * conducted at runtime the <code>ProviderManager</code>.</p>
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
