/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.acceptance.security;

import javax.inject.Inject;
import javax.inject.Named;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/org/exoplatform/acceptance/security/spring-test.xml"})
@ActiveProfiles("test")
public class CrowdUserDetailsServiceMockTest {

  @Inject
  @Named("userDetailsService")
  UserDetailsService userDetailsService;

  @Test
  public void testLoadAdministrator() throws Exception {
    UserDetails account = userDetailsService.loadUserByUsername("admin");
    Assert.assertNotNull(account);
    Assert.assertTrue(account.getAuthorities().contains(AppAuthority.ROLE_USER));
    Assert.assertTrue(account.getAuthorities().contains(AppAuthority.ROLE_ADMIN));
  }

  @Test
  public void testLoadUserByUsernameUser() throws Exception {
    UserDetails account = userDetailsService.loadUserByUsername("user");
    Assert.assertNotNull(account);
    Assert.assertTrue(account.getAuthorities().contains(AppAuthority.ROLE_USER));
    Assert.assertFalse(account.getAuthorities().contains(AppAuthority.ROLE_ADMIN));
  }

  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUnknownUserByUsername() throws Exception {
    UserDetails account = userDetailsService.loadUserByUsername("foo");
    Assert.assertNotNull(account);
  }
}
