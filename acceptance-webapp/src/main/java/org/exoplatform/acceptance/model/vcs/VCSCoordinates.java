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

import org.exoplatform.acceptance.model.credential.Credential;

import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * A Version Control System repository
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class VCSCoordinates {

  @NotNull
  private String name;
  @NotNull
  private String url;
  @NotNull
  private String credentialId;
  /**
   * The list of references (tags and branches) for this repository
   */
  private List<VCSRef> references = new ArrayList<>();


  /**
   * <p>Constructor for VCSCoordinates.</p>
   */
  public VCSCoordinates() {
  }

  /**
   * <p>Constructor for VCSCoordinates.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param url a {@link java.lang.String} object.
   */
  public VCSCoordinates(@NotNull String name, @NotNull String url) {
    this(name, url, Credential.NONE.getId());
  }

  /**
   * <p>Constructor for VCSCoordinates.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param url a {@link java.lang.String} object.
   * @param credentialId a {@link java.lang.String} object.
   */
  public VCSCoordinates(@NotNull String name, @NotNull String url, @NotNull String credentialId) {
    this.name = name;
    this.url = url;
    this.credentialId = credentialId;
  }

  /**
   * {@inheritDoc}
   *
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
        .add("name", getName())
        .add("url", getUrl())
        .add("credentialId", getCredentialId()).toString();
  }

  /**
   * <p>Getter for the field <code>name</code>.</p>
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
   * Returns the SCM URL used to access to this remote repository.
   */
  public String getUrl() {
    return url;
  }

  /**
   * <p>Setter for the field <code>url</code>.</p>
   *
   * @param url a {@link java.lang.String} object.
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Credential to access to the repository
   *
   * @return the credential to use to authenticate to the repository
   */
  public String getCredentialId() {
    return credentialId;
  }

  /**
   * <p>Setter for the field <code>credentialId</code>.</p>
   *
   * @param credentialId a {@link java.lang.String} object.
   */
  public void setCredentialId(String credentialId) {
    this.credentialId = credentialId;
  }

  /**
   * <p>Getter for the field <code>references</code>.</p>
   */
  public List<VCSRef> getReferences() {
    return references;
  }

  /**
   * <p>Setter for the field <code>references</code>.</p>
   *
   * @param references a {@link java.util.List} object.
   */
  public void setReferences(List<VCSRef> references) {
    this.references = VCSRef.SORT_BY_NAME.sortedCopy(references);
  }

  /**
   * <p>getTags.</p>
   */
  public List<VCSRef> getTags() {
    return FluentIterable.from(getReferences()).filter(VCSRef.IS_TAG).toList();
  }

  /**
   * <p>getBranches.</p>
   */
  public List<VCSRef> getBranches() {
    return FluentIterable.from(getReferences()).filter(VCSRef.IS_BRANCH).toList();
  }
}
