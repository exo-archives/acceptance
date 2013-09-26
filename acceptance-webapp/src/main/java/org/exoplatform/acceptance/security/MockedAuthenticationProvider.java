package org.exoplatform.acceptance.security;

import javax.inject.Named;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Named("mockedAuthenticationProvider")
public class MockedAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String password = authentication.getCredentials().toString();
    if (name.equals(MockedAcceptanceUser.ADMIN.getUsername())
        && password.equals(MockedAcceptanceUser.ADMIN.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          MockedAcceptanceUser.ADMIN.getUsername(),
          MockedAcceptanceUser.ADMIN.getPassword(),
          MockedAcceptanceUser.ADMIN.getAuthorities());
    } else if (name.equals(MockedAcceptanceUser.USER.getUsername())
        && password.equals(MockedAcceptanceUser.USER.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          MockedAcceptanceUser.USER.getUsername(),
          MockedAcceptanceUser.USER.getPassword(),
          MockedAcceptanceUser.USER.getAuthorities());
    } else {
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
