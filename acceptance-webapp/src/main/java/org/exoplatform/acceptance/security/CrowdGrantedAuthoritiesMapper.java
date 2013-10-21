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
import java.util.EnumSet;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * Crowd Authorities mapper. Maps Crowd groups to {@link AppAuthority} roles
 */
@Named("crowdGrantedAuthoritiesMapper")
@Singleton
public class CrowdGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CrowdGrantedAuthoritiesMapper.class);

  /**
   * The crowd group used for our application users role
   */
  @Value("ROLE_${crowd.group.users}")
  private String userRole;

  /**
   * The crowd group used for our application administrator role
   */
  @Value("ROLE_${crowd.group.administrators}")
  private String adminRole;

  /**
   * Mapping interface which can be injected into the authentication layer to convert the
   * authorities loaded from storage into those which will be used in the {@code Authentication} object.
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Collection<GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
    //empty EnumSet
    Set roles = EnumSet.noneOf(AppAuthority.class);
    LOGGER.debug("Authorities to check : {}", authorities);
    for (GrantedAuthority authority : authorities) {
      if (userRole.equals(authority.getAuthority())) {
        roles.add(AppAuthority.ROLE_USER);
      } else if (adminRole.equals(authority.getAuthority())) {
        roles.add(AppAuthority.ROLE_ADMIN);
      }
    }
    LOGGER.debug("Computed application roles : {}", roles);
    return roles;
  }
}
