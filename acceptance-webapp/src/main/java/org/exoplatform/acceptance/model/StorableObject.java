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
package org.exoplatform.acceptance.model;

import com.google.common.base.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * <p>StorableObject class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class StorableObject {

  /**
   * A readable/self-explainable name to identify the object
   */
  @Indexed(unique = true)
  @NotNull
  @Size(min = 3, max = 64)
  private String name;
  /**
   * A unique technical ID used to identify the object.
   * (Can be null while the object is in memory and not yet stored)
   */
  @Id
  private String id;

  /**
   * <p>Constructor for StorableObject.</p>
   */
  public StorableObject() {
  }

  /**
   * <p>Constructor for StorableObject.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  public StorableObject(@NotNull String name) {
    this.name = name;
  }

  /**
   * <p>Constructor for StorableObject.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param id   a {@link java.lang.String} object.
   */
  public StorableObject(@NotNull String name, @NotNull String id) {
    this.name = name;
    this.id = id;
  }

  /**
   * <p>Getter for the field <code>id</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getId() {
    return id;
  }

  /**
   * <p>Setter for the field <code>id</code>.</p>
   *
   * @param id a {@link java.lang.String} object.
   */
  public void setId(String id) {
    this.id = id;
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
    return Objects.toStringHelper(this).add("id", getId()).add("name", getName()).toString();
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
