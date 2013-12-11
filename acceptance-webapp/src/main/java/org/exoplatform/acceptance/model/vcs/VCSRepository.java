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
package org.exoplatform.acceptance.model.vcs;

import org.exoplatform.acceptance.model.StorableObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * a Version Control System Repository
 */
@Document(collection = "vcsrepositories")
public class VCSRepository extends StorableObject {

  /**
   * The type of credential
   */
  @NotNull
  private Type type = Type.GIT;
  @NotNull
  @Size(min = 1)
  private ArrayList<VCSCoordinates> remoteRepositories = new ArrayList<>();


  public VCSRepository() {
    super();
  }

  @JsonCreator
  public VCSRepository(@NotNull @JsonProperty("name") String name) {
    super(name);
  }

  public VCSRepository(@NotNull String name, @NotNull String id) {
    super(name, id);
  }

  public Type getType() {
    return type;
  }

  public void setType(@NotNull Type type) {
    this.type = type;
  }

  public void addRemoteRepository(String name, String url) {
    addRemoteRepository(new VCSCoordinates(name, url));
  }

  public void addRemoteRepository(String name, String url, String credentialId) {
    addRemoteRepository(new VCSCoordinates(name, url, credentialId));
  }

  public void addRemoteRepository(VCSCoordinates VCSCoordinates) {
    assert VCSCoordinates.getName() != null;
    remoteRepositories.add(VCSCoordinates);
  }

  public Collection<VCSCoordinates> getRemoteRepositories() {
    return remoteRepositories;
  }

  public void setRemoteRepositories(Collection<VCSCoordinates> VCSCoordinatesList) {
    remoteRepositories.clear();
    for (VCSCoordinates VCSCoordinates : VCSCoordinatesList) {
      addRemoteRepository(VCSCoordinates);
    }
  }

  /**
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
   *
   * @return a string representation of the object.
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
   * VCS Repository types
   */
  public enum Type {
    GIT
  }

}