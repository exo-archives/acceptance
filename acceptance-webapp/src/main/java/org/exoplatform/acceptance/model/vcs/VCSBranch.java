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
package org.exoplatform.acceptance.model.vcs;

import org.exoplatform.acceptance.model.StorableObject;

import com.google.common.base.Objects;
import javax.validation.constraints.NotNull;

/**
 * <p>VCSBranch class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class VCSBranch {

  /**
   * The name to humanly identify the credential
   */
  @NotNull
  protected String name;

  /**
   * <p>Constructor for VCSBranch.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public VCSBranch(@NotNull String name) {
    this.name = name;
  }

  /**
   * <p>Getter for the field <code>name</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return name;
  }

  /**
   * <p>Setter for the field <code>name</code>.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public void setName(@NotNull String name) {
    this.name = name;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("name", getName()).toString();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final StorableObject other = (StorableObject) obj;
    return java.util.Objects.equals(this.getName(), other.getName());
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return java.util.Objects.hash(this.getName());
  }
}
