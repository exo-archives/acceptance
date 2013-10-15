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
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = {"classpath:test-context.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
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
