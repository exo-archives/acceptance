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

import org.exoplatform.acceptance.model.credential.Credential;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Version Control System repository
 */
public class VCSCoordinates {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(VCSCoordinates.class);
  private String name;
  private String url;
  private String credentialId;

  public VCSCoordinates() {
  }

  public VCSCoordinates(String name, String url) {
    this(name, url, Credential.NONE.getId());
  }

  public VCSCoordinates(String name, String url, String credentialId) {
    this.name = name;
    this.url = url;
    this.credentialId = credentialId;
    LOGGER.debug("New VCSCoordinates : {}, {}", url, credentialId);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the SCM URL used to access to this remote repository.
   */
  public String getUrl() {
    return url;
  }

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

  public void setCredentialId(String credentialId) {
    this.credentialId = credentialId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VCSCoordinates)) return false;

    VCSCoordinates that = (VCSCoordinates) o;

    if (!credentialId.equals(that.credentialId)) return false;
    if (!name.equals(that.name)) return false;
    if (!url.equals(that.url)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + url.hashCode();
    result = 31 * result + credentialId.hashCode();
    return result;
  }
}
