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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * a Version Control System Repository
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Document(collection = "vcsrepositories")
@JsonIgnoreProperties({"tags", "references", "branches"})
public class VCSRepository extends StorableObject {

  @NotNull
  @Size(min = 1)
  private final List<VCSCoordinates> remoteRepositories = new ArrayList<>();

  /**
   * The type of VCS
   */
  @NotNull
  private Type type = Type.GIT;

  /**
   * <p>Constructor for VCSRepository.</p>
   */
  public VCSRepository() {
  }

  /**
   * <p>Constructor for VCSRepository.</p>
   *
   * @param name a {@link java.lang.String} object.
   */
  @JsonCreator
  public VCSRepository(@NotNull @JsonProperty("name") String name) {
    super(name);
  }

  /**
   * <p>Constructor for VCSRepository.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param id   a {@link java.lang.String} object.
   */
  public VCSRepository(@NotNull String name, @NotNull String id) {
    super(name, id);
  }

  /**
   * <p>addRemoteRepository.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param url  a {@link java.lang.String} object.
   */
  public void addRemoteRepository(String name, String url) {
    addRemoteRepository(new VCSCoordinates(name, url));
  }

  /**
   * <p>addRemoteRepository.</p>
   *
   * @param coordinates a {@link org.exoplatform.acceptance.model.vcs.VCSCoordinates} object.
   */
  public void addRemoteRepository(VCSCoordinates coordinates) {
    assert coordinates.getName() != null;
    remoteRepositories.add(coordinates);
  }

  /**
   * <p>addRemoteRepository.</p>
   *
   * @param name         a {@link java.lang.String} object.
   * @param url          a {@link java.lang.String} object.
   * @param credentialId a {@link java.lang.String} object.
   */
  public void addRemoteRepository(String name, String url, String credentialId) {
    addRemoteRepository(new VCSCoordinates(name, url, credentialId));
  }

  /**
   * <p>getTags.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<VCSRef> getTags() {
    return VCSRef.SORT_BY_NAME.sortedCopy(FluentIterable.from(getReferences()).filter(VCSRef.IS_TAG).toList());
  }

  /**
   * <p>getReferences.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<VCSRef> getReferences() {
    return VCSRef.SORT_BY_NAME.sortedCopy(
        FluentIterable.from(getRemoteRepositories()).transformAndConcat(new Function<VCSCoordinates,
            Iterable<VCSRef>>() {
          // TODO : Juzu throws a NPE in live mode when using @Nullable annotation
          //@Nullable
          @Override
          //public Iterable<VCSRef> apply(@Nullable coordinates input) {
          public Iterable<VCSRef> apply(VCSCoordinates input) {
            return input.getReferences();
          }
        }).toList()
    );
  }

  /**
   * <p>getBranches.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<VCSRef> getBranches() {
    return VCSRef.SORT_BY_NAME.sortedCopy(FluentIterable.from(getReferences()).filter(VCSRef.IS_BRANCH).toList());
  }

  /**
   * {@inheritDoc}
   * <p/>
   * Returns a string representation of the object. In general, the
   * {@code toString} method returns a string that
   * "textually represents" this object. The result should
   * be a concise but informative representation that is easy for a
   * person to read.
   * It is recommended that all subclasses override this method.
   * <p/>
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
    return Objects.toStringHelper(this)
        .add("id", getId())
        .add("type", getType())
        .add("name", getName())
        .add("remotes", getRemoteRepositories())
        .toString();
  }

  /**
   * <p>Getter for the field <code>type</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSRepository.Type} object.
   */
  public Type getType() {
    return type;
  }

  /**
   * <p>Setter for the field <code>type</code>.</p>
   *
   * @param type a {@link org.exoplatform.acceptance.model.vcs.VCSRepository.Type} object.
   */
  public void setType(@NotNull Type type) {
    this.type = type;
  }

  /**
   * <p>Getter for the field <code>remoteRepositories</code>.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<VCSCoordinates> getRemoteRepositories() {
    return remoteRepositories;
  }

  /**
   * <p>Setter for the field <code>remoteRepositories</code>.</p>
   *
   * @param coordinatesList a {@link java.util.List} object.
   */
  public void setRemoteRepositories(List<VCSCoordinates> coordinatesList) {
    remoteRepositories.clear();
    for (VCSCoordinates coordinates : coordinatesList) {
      addRemoteRepository(coordinates);
    }
  }

  /**
   * VCS Repository types
   */
  public enum Type {
    GIT
  }


}
