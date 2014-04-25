/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
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

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

/**
 * This is a POJO to implement a CrowdUserDetails
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class CrowdUserDetailsMock implements ICrowdUserDetails {


  /**
   * Constant <code>serialVersionUID=7557021962716626298L</code>
   */
  private static final long serialVersionUID = 7557021962716626298L;
  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private String email;

  private boolean accountNonExpired = true;

  private boolean accountNonLocked = true;

  private boolean credentialsNonExpired = true;

  private boolean enabled = true;

  private Collection<GrantedAuthority> authorities = new ArrayList<>();

  /**
   * <p>Constructor for CrowdUserDetailsMock.</p>
   */
  public CrowdUserDetailsMock() {
  }

  /**
   * <p>Getter for the field <code>username</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getUsername() {
    return username;
  }

  /**
   * <p>Setter for the field <code>username</code>.</p>
   *
   * @param username a {@link java.lang.String} object.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * <p>Getter for the field <code>password</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPassword() {
    return password;
  }

  /**
   * <p>Setter for the field <code>password</code>.</p>
   *
   * @param password a {@link java.lang.String} object.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * <p>Getter for the field <code>firstName</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * <p>Setter for the field <code>firstName</code>.</p>
   *
   * @param firstName a {@link java.lang.String} object.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * <p>Getter for the field <code>lastName</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * <p>Setter for the field <code>lastName</code>.</p>
   *
   * @param lastName a {@link java.lang.String} object.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * <p>Getter for the field <code>email</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getEmail() {
    return email;
  }

  /**
   * <p>Setter for the field <code>email</code>.</p>
   *
   * @param email a {@link java.lang.String} object.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * <p>isAccountNonExpired.</p>
   *
   * @return a boolean.
   */
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  /**
   * <p>Setter for the field <code>accountNonExpired</code>.</p>
   *
   * @param accountNonExpired a boolean.
   */
  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  /**
   * <p>isAccountNonLocked.</p>
   *
   * @return a boolean.
   */
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  /**
   * <p>Setter for the field <code>accountNonLocked</code>.</p>
   *
   * @param accountNonLocked a boolean.
   */
  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  /**
   * <p>isCredentialsNonExpired.</p>
   *
   * @return a boolean.
   */
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  /**
   * <p>Setter for the field <code>credentialsNonExpired</code>.</p>
   *
   * @param credentialsNonExpired a boolean.
   */
  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  /**
   * <p>isEnabled.</p>
   *
   * @return a boolean.
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * <p>Setter for the field <code>enabled</code>.</p>
   *
   * @param enabled a boolean.
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * <p>Getter for the field <code>authorities</code>.</p>
   *
   * @return a {@link java.util.Collection} object.
   */
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * <p>Setter for the field <code>authorities</code>.</p>
   *
   * @param authorities a {@link java.util.Collection} object.
   */
  public void setAuthorities(Collection<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  /**
   * <p>getFullName.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }

}
