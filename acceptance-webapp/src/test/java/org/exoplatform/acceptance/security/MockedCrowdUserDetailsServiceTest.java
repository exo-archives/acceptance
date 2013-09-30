package org.exoplatform.acceptance.security;

import javax.inject.Inject;
import javax.inject.Named;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = {"classpath:test-context.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class MockedCrowdUserDetailsServiceTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(MockedCrowdUserDetailsServiceTest.class);

  @Inject
  @Named("mockedAdministrator")
  CrowdUser administrator;

  @Inject
  @Named("mockedUser")
  CrowdUser user;

  @Inject
  @Named("mockedAnonymous")
  CrowdUser anonymous;

  @Inject
  @Named("crowdUserDetailsService")
  UserDetailsService userDetailsService;

  @Test
  public void testLoadUserByUsernameAdministrator() throws Exception {
    Assert.assertEquals(administrator, userDetailsService.loadUserByUsername(administrator.getUsername()));
  }

  @Test
  public void testLoadUserByUsernameUser() throws Exception {
    Assert.assertEquals(user, userDetailsService.loadUserByUsername(user.getUsername()));
  }

  @Test
  public void testLoadUserByUsernameAnonymous() throws Exception {
    Assert.assertEquals(anonymous, userDetailsService.loadUserByUsername(anonymous.getUsername()));
  }

  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUnknownUserByUsername() throws Exception {
    userDetailsService.loadUserByUsername("foo");
  }
}
