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
package org.exoplatform.acceptance.model.vcs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import javax.validation.constraints.NotNull;

/**
 * <p>VCSRef class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class VCSRef {

  /**
   * Constant <code>IS_TAG</code>
   */
  public static final Predicate<VCSRef> IS_TAG = new Predicate<VCSRef>() {
    @Override
    // TODO : Juzu throws a NPE in live mode when using @Nullable annotation
    //public boolean apply(@Nullable VCSRef input) {
    public boolean apply(VCSRef input) {
      return null != input && null != input.getType() && Type.TAG.equals(input.getType());
    }
  };
  /**
   * Constant <code>IS_BRANCH</code>
   */
  public static final Predicate<VCSRef> IS_BRANCH = new Predicate<VCSRef>() {
    @Override
    // TODO : Juzu throws a NPE in live mode when using @Nullable annotation
    //public boolean apply(@Nullable VCSRef input) {
    public boolean apply(VCSRef input) {
      return null != input && null != input.getType() && Type.BRANCH.equals(input.getType());
    }
  };
  /**
   * Constant <code>SORT_BY_NAME</code>
   */
  public static final Ordering<VCSRef> SORT_BY_NAME = Ordering.natural().nullsFirst().onResultOf(new Function<VCSRef, String>() {
    public String apply(VCSRef ref) {
      return ref.getName();
    }
  });
  @NotNull
  private String name;
  @NotNull
  private String id;
  /**
   * The type of reference
   */
  @NotNull
  private Type type;

  /**
   * <p>Constructor for VCSRef.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.vcs.VCSRef.Type} object.
   * @param name a {@link java.lang.String} object.
   * @param id   a {@link java.lang.String} object.
   */
  @JsonCreator
  public VCSRef(@NotNull @JsonProperty("type") Type type,
                @NotNull @JsonProperty("name") String name,
                @NotNull @JsonProperty("id") String id) {
    this.type = type;
    this.name = name;
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
  public void setName(String name) {
    this.name = name;
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
   * <p>Getter for the field <code>type</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSRef.Type} object.
   */
  public Type getType() {
    return type;
  }

  /**
   * <p>Setter for the field <code>type</code>.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.vcs.VCSRef.Type} object.
   */
  public void setType(Type type) {
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("type", type)
        .add("name", name)
        .add("id", id)
        .toString();
  }

  /**
   * VCS Ref types
   */
  public enum Type {
    TAG, BRANCH
  }
}
