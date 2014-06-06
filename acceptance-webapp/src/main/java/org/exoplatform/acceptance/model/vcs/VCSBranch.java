/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.acceptance.model.vcs;

import javax.validation.constraints.NotNull;

/**
 * A VCS Branch
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public class VCSBranch extends VCSRef {
  /**
   * <p>Constructor for VCSBranch.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @param id   a {@link java.lang.String} object.
   */
  public VCSBranch(@NotNull String name, @NotNull String id) {
    super(Type.BRANCH, name, id);
  }
}
