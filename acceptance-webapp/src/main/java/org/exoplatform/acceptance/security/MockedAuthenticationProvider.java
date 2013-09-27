package org.exoplatform.acceptance.security;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Data
public class MockedAuthenticationProvider implements AuthenticationProvider {
  private MockedAcceptanceUser administrator;
  private MockedAcceptanceUser user;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String password = authentication.getCredentials().toString();
    if (name.equals(administrator.getUsername())
        && password.equals(administrator.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          administrator.getUsername(),
          administrator.getPassword(),
          administrator.getAuthorities());
    } else if (name.equals(user.getUsername())
        && password.equals(user.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          user.getUsername(),
          user.getPassword(),
          user.getAuthorities());
    } else {
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
