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
package org.exoplatform.acceptance.service.vcs;

import org.exoplatform.acceptance.model.vcs.VCSFileSet;
import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.AbstractMongoCRUDService;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.service.ConfigurationService;
import org.exoplatform.acceptance.service.credential.CredentialService;
import org.exoplatform.acceptance.storage.vcs.VCSRepositoryMongoStorage;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Services to manage VCS Repositories
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named
public class VCSRepositoryService extends AbstractMongoCRUDService<VCSRepository> implements CRUDService<VCSRepository> {

  /** Constant <code>LOGGER</code> */
  private static final Logger LOGGER = LoggerFactory.getLogger(VCSRepositoryService.class);
  @Inject
  private ConfigurationService configurationService;
  @Inject
  private VCSRepositoryMongoStorage vcsRepositoryMongoStorage;
  @Inject
  private CredentialService credentialService;

  /** {@inheritDoc} */
  @Override
  protected VCSRepositoryMongoStorage getMongoStorage() {
    return vcsRepositoryMongoStorage;
  }

  /**
   * <p>findByName.</p>
   *
   * @param name a {@link java.lang.String} object.
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   */
  public VCSRepository findByName(String name) {
    return vcsRepositoryMongoStorage.findByName(name);
  }

  /**
   * <p>getFileSet.</p>
   *
   * @param basedir a {@link java.io.File} object.
   * @param repository a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSFileSet} object.
   */
  public VCSFileSet getFileSet(
      @NotNull File basedir,
      @NotNull VCSRepository repository) {
    return new VCSFileSet(basedir, repository);
  }

  // Every minute
  /**
   * <p>extractSourceStatistics.</p>
   */
  @Scheduled(fixedRate = 60000)
  public void extractSourceStatistics() {
    LOGGER.debug("Starting to process source repositories");
    for (VCSRepository VCSRepository : vcsRepositoryMongoStorage.findAll()) {
      LOGGER.debug("Processing sources repository {}", VCSRepository.getName());
      VCSFileSet localFileSet = getFileSet(new File(getCheckoutDirectory(), VCSRepository.getName()), VCSRepository);
    }
    LOGGER.debug("Source repositories processed");
  }

  /**
   * <p>getCheckoutDirectory.</p>
   *
   * @return a {@link java.io.File} object.
   */
  public File getCheckoutDirectory() {
    File checkoutDirectory = new File(configurationService.getDataDir(), "checkout");
    if (!checkoutDirectory.exists()) {
      boolean result = checkoutDirectory.mkdirs();
      LOGGER.info("Checkout directory {} creation [{}]", checkoutDirectory, result ? "OK" : "KO");
    }
    return checkoutDirectory;
  }

}
