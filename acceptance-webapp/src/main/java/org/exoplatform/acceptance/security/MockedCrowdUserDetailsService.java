package org.exoplatform.acceptance.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class MockedCrowdUserDetailsService implements UserDetailsService {
  private MockedAcceptanceUser administrator;

  private MockedAcceptanceUser user;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (user.getUsername().equals(username))
      return user;
    else if (administrator.getUsername().equals(username))
      return administrator;
    else
      throw new UsernameNotFoundException("Unknown user : " + username);
  }
}
