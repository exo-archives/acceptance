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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * Miscellaneous details about the project
 */
@Named("projectSettings")
@Singleton
@Data
@Slf4j
public class ProjectSettings {

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
}
