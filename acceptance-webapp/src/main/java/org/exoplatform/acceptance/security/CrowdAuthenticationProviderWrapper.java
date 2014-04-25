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

import com.atlassian.crowd.integration.springsecurity.RemoteCrowdAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * This authentication provider is wrapping the one used by Crowd to replace Crowd groups by Application Roles from {@link org.exoplatform.acceptance.security.AppAuthority}
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @see CrowdGrantedAuthoritiesMapper
 * @since 2.0.0
 */
public class CrowdAuthenticationProviderWrapper implements AuthenticationProvider {
  private final RemoteCrowdAuthenticationProvider crowdAuthenticationProvider;

  private final CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper;

  /**
   * <p>Constructor for CrowdAuthenticationProviderWrapper.</p>
   *
   * @param crowdAuthenticationProvider a {@link com.atlassian.crowd.integration.springsecurity.RemoteCrowdAuthenticationProvider} object.
   * @param grantedAuthoritiesMapper    a {@link org.exoplatform.acceptance.security.CrowdGrantedAuthoritiesMapper} object.
   */
  public CrowdAuthenticationProviderWrapper(RemoteCrowdAuthenticationProvider crowdAuthenticationProvider, CrowdGrantedAuthoritiesMapper grantedAuthoritiesMapper) {
    this.crowdAuthenticationProvider = crowdAuthenticationProvider;
    this.grantedAuthoritiesMapper = grantedAuthoritiesMapper;
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Performs authentication with the same contract as {@link
   * org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)}.
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Authentication crowdAuthentication = crowdAuthenticationProvider.authenticate(authentication);
    return new UsernamePasswordAuthenticationToken(
        crowdAuthentication.getPrincipal(),
        crowdAuthentication.getCredentials(),
        grantedAuthoritiesMapper.mapAuthorities(crowdAuthentication.getAuthorities()));
  }

  /**
   * {@inheritDoc}
   * <p/>
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
    return crowdAuthenticationProvider.supports(authentication);
  }
}
