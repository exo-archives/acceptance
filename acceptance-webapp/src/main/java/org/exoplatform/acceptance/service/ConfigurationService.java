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
package org.exoplatform.acceptance.service;

import com.google.common.base.Strings;
import java.io.File;
import java.net.URL;
import java.util.Date;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Acceptance configuration
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named
@Singleton
public class ConfigurationService {
  /** Constant <code>LOGGER</code> */
  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationService.class);
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
  @Value("${acceptance.data.dir}")
  private String dataDirPath;
  @Value("${acceptance.tmp.dir}")
  private String tmpDirPath;

  /**
   * <p>Getter for the field <code>version</code>.</p>
   */
  public String getVersion() {
    return version;
  }

  /**
   * <p>Getter for the field <code>scmUrl</code>.</p>
   */
  public URL getScmUrl() {
    return scmUrl;
  }

  /**
   * <p>Getter for the field <code>scmRevision</code>.</p>
   */
  public String getScmRevision() {
    return scmRevision;
  }

  /**
   * <p>Getter for the field <code>scmRevisionUrl</code>.</p>
   */
  public String getScmRevisionUrl() {
    return scmRevisionUrl;
  }

  /**
   * <p>Getter for the field <code>buildDate</code>.</p>
   */
  public Date getBuildDate() {
    return buildDate;
  }

  /**
   * <p>Getter for the field <code>inceptionYear</code>.</p>
   */
  public String getInceptionYear() {
    return inceptionYear;
  }

  /**
   * <p>Getter for the field <code>organizationName</code>.</p>
   */
  public String getOrganizationName() {
    return organizationName;
  }

  /**
   * <p>Getter for the field <code>organizationUrl</code>.</p>
   */
  public String getOrganizationUrl() {
    return organizationUrl;
  }

  /**
   * Returns the directory where the application checks out VCS repositories.
   *
   * @return The directory after having created it if necessary
   */
  public File getVCSCheckoutDirectory() {
    File checkoutDirectory = new File(getDataDir(), "vcs/checkout");
    if (!checkoutDirectory.exists()) {
      if (checkoutDirectory.mkdirs()) {
        LOGGER.info("VCS Checkout directory {} creation [OK]", checkoutDirectory);
      } else {
        LOGGER.error("VCS Checkout directory {} creation [KO]", checkoutDirectory);
      }
    }
    return checkoutDirectory;
  }

  /**
   * <p>getDataDir.</p>
   */
  public File getDataDir() {
    if (Strings.isNullOrEmpty(dataDirPath)) {
      this.dataDirPath = System.getProperty("user.home") + "/.acceptance";
    }
    File dataDir = new File(dataDirPath);
    if (!dataDir.exists()) {
      if (dataDir.mkdirs()) {
        LOGGER.info("Data directory {} creation [OK]");
      } else {
        LOGGER.error("Data directory {} creation [KO]");
      }
    }
    return dataDir;
  }

  /**
   * <p>getTmpDir.</p>
   */
  public File getTmpDir() {
    if (Strings.isNullOrEmpty(tmpDirPath)) {
      tmpDirPath = System.getProperty("java.io.tmpdir") + "/acceptance";
    }
    File tmpDir = new File(tmpDirPath);
    if (!tmpDir.exists()) {
      if (tmpDir.mkdirs()) {
        LOGGER.info("Temporary directory {} creation [OK]");
      } else {
        LOGGER.error("Temporary directory {} creation [KO]");
      }
    }
    return tmpDir;
  }
}
