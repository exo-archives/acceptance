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

import org.exoplatform.acceptance.model.vcs.DVCSRepository;
import org.exoplatform.acceptance.service.AcceptanceException;

import com.google.common.base.Objects;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represent a Software manageable by acceptance.
 */
@Document(collection = "projects")
public class Project extends StorableObject {

  private String description;
  private URL site;
  @DBRef
  private Map<String, DVCSRepository> sourceRepositories = new HashMap<>();

  public Project(@NotNull String name) {
    super(name);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public URL getSite() {
    return site;
  }

  public void setSite(URL site) {
    this.site = site;
  }

  public void addSourceRepository(DVCSRepository repository) throws AcceptanceException {
    if (!sourceRepositories.containsKey(repository.getName())) {
      sourceRepositories.put(repository.getName(), repository);
    } else {
      throw new AcceptanceException(
          "Project <" + getName() + "> has already a source repository named <" + repository.getName() + ">");
    }
  }

  public Collection<DVCSRepository> getSourceRepositories() {
    return sourceRepositories.values();
  }

  public void setSourceRepositories(Collection<DVCSRepository> repositories) throws AcceptanceException {
    sourceRepositories.clear();
    for (DVCSRepository repository : repositories) {
      addSourceRepository(repository);
    }
  }

  public DVCSRepository getSourceRepository(String name) {
    return sourceRepositories.get(name);
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
    return Objects.toStringHelper(this).add("id", getId()).add("name", getName()).toString();
  }
}
