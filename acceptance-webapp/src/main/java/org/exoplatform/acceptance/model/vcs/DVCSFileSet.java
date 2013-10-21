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

import java.io.File;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document(collection = "dvcsfilesets")
public class DVCSFileSet {
  @Id
  private String id;

  /**
   * Local base directory where the clone is created
   */
  private File baseDir;

  @DBRef
  private DVCSRepository repository;

  public DVCSFileSet() {
  }

  public DVCSFileSet(DVCSRepository repository, File baseDir) {
    this.repository = repository;
    this.baseDir = baseDir;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public File getBaseDir() {
    return baseDir;
  }

  public void setBaseDir(File baseDir) {
    this.baseDir = baseDir;
  }

  public DVCSRepository getRepository() {
    return repository;
  }

  public void setRepository(DVCSRepository repository) {
    this.repository = repository;
  }
}
