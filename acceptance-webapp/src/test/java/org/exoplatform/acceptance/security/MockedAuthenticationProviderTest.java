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

import javax.inject.Inject;
import javax.inject.Named;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = {"classpath:test-context.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class MockedAuthenticationProviderTest {
  @Inject
  @Named("mockedAuthenticationProvider")
  private MockedAuthenticationProvider authenticationProviderMock;

  @Inject
  @Named("mockedAdministrator")
  private CrowdUserMock administratorMock;

  @Inject
  @Named("mockedUser")
  private CrowdUserMock userMock;

  private void testAuthenticate(CrowdUserMock user) throws Exception {
    Authentication administratorAuthentication = authenticationProviderMock.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    Assert.assertEquals(user.getUsername(), administratorAuthentication.getPrincipal());
    Assert.assertEquals(user.getPassword(), administratorAuthentication.getCredentials().toString());
    for (GrantedAuthority grantedAuthority : administratorAuthentication.getAuthorities()) {
      Assert.assertTrue(user.getAuthorities().contains(grantedAuthority));
    }
    for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
      Assert.assertTrue(administratorAuthentication.getAuthorities().contains(grantedAuthority));
    }
  }

  @Test
  public void testAuthenticateAdministrator() throws Exception {
    testAuthenticate(administratorMock);
  }

  @Test
  public void testAuthenticateUser() throws Exception {
    testAuthenticate(userMock);
  }

  @Test(expected = BadCredentialsException.class)
  public void testAuthenticateUnknownUser() throws Exception {
    authenticationProviderMock.authenticate(new UsernamePasswordAuthenticationToken("foo", "bar"));
  }

}
