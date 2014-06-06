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
package org.exoplatform.acceptance.ui.model;

import javax.inject.Named;

/**
 * <p>Flash class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named("flash")
public class Flash {

  private String success = "";

  private String error = "";

  private String username = "";

  /**
   * <p>Getter for the field <code>success</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getSuccess() {
    return success;
  }

  /**
   * <p>Setter for the field <code>success</code>.</p>
   *
   * @param success a {@link java.lang.String} object.
   */
  public void setSuccess(String success) {
    this.success = success;
  }

  /**
   * <p>Getter for the field <code>error</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getError() {
    return error;
  }

  /**
   * <p>Setter for the field <code>error</code>.</p>
   *
   * @param error a {@link java.lang.String} object.
   */
  public void setError(String error) {
    this.error = error;
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
}
