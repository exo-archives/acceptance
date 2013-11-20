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
import org.exoplatform.acceptance.service.AcceptanceException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * a Version Control System Repository
 */
@Document(collection = "vcsrepositories")
public class VCSRepository extends StorableObject {

  public final static String DEFAULT_REMOTE = "origin";
  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(VCSRepository.class);
  /**
   * The type of credential
   */
  @NotNull
  private Type type = Type.GIT;
  @NotNull
  private ArrayList<VCSCoordinates> remoteRepositories = new ArrayList<>();

  @JsonCreator
  public VCSRepository(@NotNull @JsonProperty("name") String name) {
    super(name);
  }

  public Type getType() {
    return type;
  }

  public void setType(@NotNull Type type) {
    this.type = type;
  }

  public void addRemoteRepository(String url) throws AcceptanceException {
    addRemoteRepository(new VCSCoordinates(DEFAULT_REMOTE, url));
  }

  public void addRemoteRepository(String name, String url) throws AcceptanceException {
    addRemoteRepository(new VCSCoordinates(name, url));
  }

  public void addRemoteRepository(String name, String url, String credentialId) throws AcceptanceException {
    addRemoteRepository(new VCSCoordinates(name, url, credentialId));
  }

  public void addRemoteRepository(VCSCoordinates VCSCoordinates) throws AcceptanceException {
    assert VCSCoordinates.getName() != null;
    remoteRepositories.add(VCSCoordinates);
  }

  public Collection<VCSCoordinates> getRemoteRepositories() {
    return remoteRepositories;
  }

  public void setRemoteRepositories(Collection<VCSCoordinates> VCSCoordinatesList) throws AcceptanceException {
    remoteRepositories.clear();
    for (VCSCoordinates VCSCoordinates : VCSCoordinatesList) {
      addRemoteRepository(VCSCoordinates);
    }
  }

  /**
   * VCS Repository types
   */
  public enum Type {
    GIT
  }

}
