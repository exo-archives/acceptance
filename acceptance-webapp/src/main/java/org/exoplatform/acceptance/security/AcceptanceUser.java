package org.exoplatform.acceptance.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AcceptanceUser extends UserDetails {
  /**
   * Returns the user's first name
   */
  String getFirstName();

  /**
   * Returns the user's last name
   */
  String getLastName();

  /**
   * Returns the user's fullname
   */
  String getFullName();
}
