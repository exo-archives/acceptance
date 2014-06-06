/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.acceptance.model.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.constraints.NotNull;

/**
 * <p>UsernamePasswordCredential class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@JsonTypeName("PASSWORD")
public class UsernamePasswordCredential extends Credential {
  @NotNull
  private String username;
  @NotNull
  private String password;

  /**
   * <p>Constructor for UsernamePasswordCredential.</p>
   *
   * @param name     a {@link java.lang.String} object.
   * @param username a {@link java.lang.String} object.
   * @param password a {@link java.lang.String} object.
   */
  @JsonCreator
  public UsernamePasswordCredential(
      @NotNull @JsonProperty("name") String name,
      @NotNull @JsonProperty("username") String username,
      @NotNull @JsonProperty("password") String password) {
    super(Type.PASSWORD, name);
    this.username = username;
    this.password = password;
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

}
