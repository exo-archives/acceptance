package org.exoplatform.acceptance.security;

import java.util.Collection;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
public class MockedAcceptanceUser implements AcceptanceUser {

  @NonNull
  private String username;

  @NonNull
  private String password;

  @NonNull
  private String firstName;

  @NonNull
  private String lastName;

  private boolean accountNonExpired = true;

  private boolean accountNonLocked = true;

  private boolean credentialsNonExpired = true;

  private boolean enabled = true;

  @NonNull
  private Collection<GrantedAuthority> authorities;

  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }

}
