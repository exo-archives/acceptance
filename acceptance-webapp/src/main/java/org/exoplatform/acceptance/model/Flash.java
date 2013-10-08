/*
 * Copyright (C) 2011-2013 eXo Platform SAS.
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
package org.exoplatform.acceptance.model;

import java.io.Serializable;

import javax.inject.Named;

@Named("flash")
public class Flash implements Serializable {

  private String success = "";

  private String error = "";

  private String username = "";

  public String getSuccess() {
    return success;
  }

  public void setSuccess(String success) {
    this.success = success;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Flash)) return false;

    Flash flash = (Flash) o;

    if (error != null ? !error.equals(flash.error) : flash.error != null) return false;
    if (success != null ? !success.equals(flash.success) : flash.success != null) return false;
    if (username != null ? !username.equals(flash.username) : flash.username != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = success != null ? success.hashCode() : 0;
    result = 31 * result + (error != null ? error.hashCode() : 0);
    result = 31 * result + (username != null ? username.hashCode() : 0);
    return result;
  }
}
