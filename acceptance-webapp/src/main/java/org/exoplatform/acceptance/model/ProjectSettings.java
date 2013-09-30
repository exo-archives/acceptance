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

import java.net.URL;
import java.util.Date;


import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Miscellaneous details about the project
 */
@Named("projectSettings")
@Singleton
public class ProjectSettings {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectSettings.class);
  @Value("${project.version}")
  private String version;
  @Value("${project.scm.url}")
  private URL scmUrl;
  @Value("${project.scm.revision}")
  private String scmRevision;
  @Value("${project.scm.revision.url}")
  private String scmRevisionUrl;
  @Value("#{new java.text.SimpleDateFormat('${project.build.timestamp.format}').parse('${project.build.timestamp}')}")
  private Date buildDate;
  @Value("${project.inceptionYear}")
  private String inceptionYear;
  @Value("${project.organizationName}")
  private String organizationName;
  @Value("${project.organizationUrl}")
  private String organizationUrl;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public URL getScmUrl() {
    return scmUrl;
  }

  public void setScmUrl(URL scmUrl) {
    this.scmUrl = scmUrl;
  }

  public String getScmRevision() {
    return scmRevision;
  }

  public void setScmRevision(String scmRevision) {
    this.scmRevision = scmRevision;
  }

  public String getScmRevisionUrl() {
    return scmRevisionUrl;
  }

  public void setScmRevisionUrl(String scmRevisionUrl) {
    this.scmRevisionUrl = scmRevisionUrl;
  }

  public Date getBuildDate() {
    return buildDate;
  }

  public void setBuildDate(Date buildDate) {
    this.buildDate = buildDate;
  }

  public String getInceptionYear() {
    return inceptionYear;
  }

  public void setInceptionYear(String inceptionYear) {
    this.inceptionYear = inceptionYear;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getOrganizationUrl() {
    return organizationUrl;
  }

  public void setOrganizationUrl(String organizationUrl) {
    this.organizationUrl = organizationUrl;
  }
}
