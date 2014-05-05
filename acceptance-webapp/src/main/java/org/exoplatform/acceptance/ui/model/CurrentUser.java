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
package org.exoplatform.acceptance.ui.model;

import org.exoplatform.acceptance.security.AppAuthority;
import org.exoplatform.acceptance.security.ICrowdUserDetails;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>CurrentUser class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named("user")
public class CurrentUser {
  @Inject
  @Named("userDetailsService")
  private UserDetailsService userDetailsService;

  private ICrowdUserDetails currentUser;

  /**
   * Returns the user's first name
   *
   * @return a {@link java.lang.String} object.
   */
  public String getFirstName() {
    return getCurrentUser().getFirstName();
  }

  /**
   * Returns the user's last name
   *
   * @return a {@link java.lang.String} object.
   */
  public String getLastName() {
    return getCurrentUser().getLastName();
  }

  /**
   * Returns the user's fullname
   *
   * @return a {@link java.lang.String} object.
   */
  public String getFullName() {
    return getCurrentUser().getFullName();
  }

  /**
   * Returns the user's email
   *
   * @return a {@link java.lang.String} object.
   */
  public String getEmail() {
    return getCurrentUser().getEmail();
  }

  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getCurrentUser().getAuthorities();
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  public String getPassword() {
    return getCurrentUser().getPassword();
  }

  /**
   * Indicates whether the user's account has expired. An expired account cannot be authenticated.
   *
   * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
   * (ie expired)
   */
  public boolean isAccountNonExpired() {
    return getCurrentUser().isAccountNonExpired();
  }

  /**
   * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
   *
   * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
   */
  public boolean isAccountNonLocked() {
    return getCurrentUser().isAccountNonLocked();
  }

  /**
   * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
   * authentication.
   *
   * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
   * valid (ie expired)
   */
  public boolean isCredentialsNonExpired() {
    return getCurrentUser().isCredentialsNonExpired();
  }

  /**
   * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
   *
   * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
   */
  public boolean isEnabled() {
    return getCurrentUser().isEnabled();
  }

  /**
   * Is the user authenticated ?
   *
   * @return true if the user is authenticated
   */
  public boolean isAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
  }

  /**
   * Returns the username used to authenticate the user. Cannot return <code>null</code>.
   *
   * @return the username (never <code>null</code>)
   */
  public String getUsername() {
    if (isAuthenticated()) {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof UserDetails) {
        return ((UserDetails) principal).getUsername();
      } else {
        return principal.toString();
      }
    } else {
      throw new UsernameNotFoundException("User not authenticated");
    }
  }

  /**
   * Simple searches for an exactly matching {@link org.springframework.security.core.GrantedAuthority#getAuthority()}.
   * Will always return false if the SecurityContextHolder contains an Authentication with nullprincipal and/or GrantedAuthority[] objects.
   *
   * @param role the GrantedAuthorityString representation to check for
   * @return true if an exact (case sensitive) matching granted authority is located, false otherwise
   */
  public boolean hasRole(String role) {
    if (isAuthenticated()) {
      for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
        if (authority.getAuthority().equals(role)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks if the current user is anonymous.
   *
   * @return true only if the user isn't authenticated or has the role ANONYMOUS
   */
  public boolean isAnonymous() {
    return !isAuthenticated() || hasRole(AppAuthority.ROLE_ANONYMOUS.getAuthority());
  }

  /**
   * Checks if the current user has the application USER role.
   *
   * @return true only if the user has the application USER role.
   */
  public boolean isUser() {
    return hasRole(AppAuthority.ROLE_USER.getAuthority());
  }

  /**
   * Checks if the current user has the application ADMIN role.
   *
   * @return true only if the user has the application ADMIN role.
   */
  public boolean isAdmin() {
    return hasRole(AppAuthority.ROLE_ADMIN.getAuthority());
  }

  /**
   * Computes the gravatar URL associated to the user email
   *
   * @param size  The size (width) of the image to generate
   * @param https If the URL must be in HTTPs or no
   * @return The URL of the gravatar
   * @throws java.security.NoSuchAlgorithmException If MD5 Algorithm isn't available
   */
  public String getGravatarUrl(int size, boolean https) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("MD5");
    digest.update(getCurrentUser().getEmail().trim().toLowerCase().getBytes(Charset.defaultCharset()));
    String hash = Strings.padStart(new BigInteger(1, digest.digest()).toString(16), 32, '0');
    if (https) {
      return "https://secure.gravatar.com/avatar/" + hash + "?s=" + size + "&d=mm";
    } else {
      return "http://www.gravatar.com/avatar/" + hash + "?s=" + size + "&d=mm";
    }
  }

  /**
   * Retrieves the current crowd user.
   *
   * @return a {@link org.exoplatform.acceptance.security.ICrowdUserDetails} object.
   * @throws org.springframework.security.core.userdetails.UsernameNotFoundException if any.
   */
  private ICrowdUserDetails getCurrentUser() throws UsernameNotFoundException {
    if (currentUser == null) {
      currentUser = (ICrowdUserDetails) userDetailsService.loadUserByUsername(getUsername());
    }
    return currentUser;
  }

  /**
   * {@inheritDoc}
   * Returns a string representation of the object. In general, the
   * {@code toString} method returns a string that
   * "textually represents" this object. The result should
   * be a concise but informative representation that is easy for a
   * person to read.
   * It is recommended that all subclasses override this method.
   * The {@code toString} method for class {@code Object}
   * returns a string consisting of the name of the class of which the
   * object is an instance, the at-sign character `{@code @}', and
   * the unsigned hexadecimal representation of the hash code of the
   * object. In other words, this method returns a string equal to the
   * value of:
   * <blockquote>
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre></blockquote>
   */
  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("username", getUsername()).add("roles", getAuthorities()).toString();
  }

}
