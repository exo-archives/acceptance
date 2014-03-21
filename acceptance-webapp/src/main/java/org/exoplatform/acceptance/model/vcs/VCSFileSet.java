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

import com.google.common.base.Objects;
import java.io.File;
import javax.validation.constraints.NotNull;

/**
 * <p>VCSFileSet class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class VCSFileSet {
  /**
   * Local base directory where the clone is created
   */
  @NotNull
  private File baseDir;
  /**
   * Identifier of the VCSRepository from which this local copy was created
   */
  @NotNull
  private VCSRepository repository;

  /**
   * <p>Constructor for VCSFileSet.</p>
   *
   * @param baseDir a {@link java.io.File} object.
   * @param repository a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   */
  public VCSFileSet(@NotNull File baseDir, @NotNull VCSRepository repository) {
    this.baseDir = baseDir;
    this.repository = repository;
  }

  /**
   * <p>Getter for the field <code>baseDir</code>.</p>
   *
   * @return a {@link java.io.File} object.
   */
  public File getBaseDir() {
    return baseDir;
  }

  /**
   * <p>Setter for the field <code>baseDir</code>.</p>
   *
   * @param baseDir a {@link java.io.File} object.
   */
  public void setBaseDir(File baseDir) {
    this.baseDir = baseDir;
  }

  /**
   * <p>Getter for the field <code>repository</code>.</p>
   *
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   */
  public VCSRepository getRepository() {
    return repository;
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
        .add("baseDir", getBaseDir())
        .add("repository", getRepository())
        .toString();
  }
}
