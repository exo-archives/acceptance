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
package org.exoplatform.acceptance.service;

import org.exoplatform.acceptance.model.vcs.DVCSFileSet;
import org.exoplatform.acceptance.model.vcs.DVCSRepository;

import groovy.lang.Singleton;
import java.io.File;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Named
@Singleton
public class GITService {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(GITService.class);

  public DVCSFileSet initLocalFileSet(@NotNull String name,
                                      @NotNull File basedir,
                                      @NotNull DVCSRepository repository) throws AcceptanceException {
    return new DVCSFileSet(name, basedir, repository.getId());
  }


  //private ScmManager scmManager = new BasicScmManager();

  //public DVCSService() {
  //super();
  // Reference all supported implementations
  //scmManager.setScmProvider("git", new JGitScmProvider());
  //}

//  private ScmRepository getScmRepository(String scmUrl) throws ScmException {
//    try {
//      return scmManager.makeScmRepository(scmUrl);
//    } catch (NoSuchScmProviderException ex) {
//      throw new ScmException("Could not find a provider.", ex);
//    } catch (ScmRepositoryException ex) {
//      throw new ScmException("Error while connecting to the repository", ex);
//    }
//  }

//  public GITFileSet checkOut(GITRepository vcs, File workingDirectory) throws VCSException {
//    if (workingDirectory.exists()) {
//      LOGGER.error("The working directory already exist: '" + workingDirectory.getAbsolutePath() + "'.");
//
//      return;
//    }
//
//    if (!workingDirectory.mkdirs()) {
//      LOGGER.error(
//          "Error while making the working directory: '" + workingDirectory.getAbsolutePath() + "'.");
//
//      return;
//    }
//
//    CheckOutScmResult result = scmManager.checkOut(scmRepository, new ScmFileSet(workingDirectory));
//
//    checkResult(result);
//
//    List checkedOutFiles = result.getCheckedOutFiles();
//
//    LOGGER.info("Checked out these files: ");
//
//    for (Iterator it = checkedOutFiles.iterator(); it.hasNext(); ) {
//      ScmFile file = (ScmFile) it.next();
//
//      LOGGER.info(" " + file.getPath());
//    }
//    return new GITFileSet(vcs,workingDirectory);
//  }

//  public void update(GITFileSet GITFileSet)
//      throws VCSException {
//    if (!workingDirectory.exists()) {
//      LOGGER.error("The working directory doesn't exist: '" + workingDirectory.getAbsolutePath() + "'.");
//
//      return;
//    }
//
//    UpdateScmResult result = scmManager.update(scmRepository, new ScmFileSet(workingDirectory));
//
//    checkResult(result);
//
//    List updatedFiles = result.getUpdatedFiles();
//
//    LOGGER.info("Updated these files: ");
//
//    for (Iterator it = updatedFiles.iterator(); it.hasNext(); ) {
//      ScmFile file = (ScmFile) it.next();
//
//      LOGGER.info(" " + file.getPath());
//    }
//  }

//  private void checkResult(ScmResult result)
//      throws ScmException {
//    if (!result.isSuccess()) {
//      LOGGER.error("Provider message:");
//
//      LOGGER.error(result.getProviderMessage() == null ? "" : result.getProviderMessage());
//
//      LOGGER.error("Command output:");
//
//      LOGGER.error(result.getCommandOutput() == null ? "" : result.getCommandOutput());
//
//      throw new ScmException(
//          "Command failed." + StringUtils.defaultString(result.getProviderMessage()));
//    }
//  }
}
