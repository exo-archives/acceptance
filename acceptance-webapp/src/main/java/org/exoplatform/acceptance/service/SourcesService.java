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

import org.exoplatform.acceptance.model.vcs.DVCSRepository;
import org.exoplatform.acceptance.service.vcs.GITService;
import org.exoplatform.acceptance.storage.DVCSRepositoryMongoStorage;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Named
@Singleton
public class SourcesService {
  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SourcesService.class);
  @Inject
  private GITService gitService;
  @Inject
  private DVCSRepositoryMongoStorage dvcsRepositoryMongoStorage;

  // Every night at 1 AM
//  @Scheduled(cron = "* * 1 * * ?")
  @Scheduled(fixedRate = 60000)
  public void generateAndEmailReport() {
    LOGGER.debug("Starting to process source repositories");
    for (DVCSRepository dvcsRepository : dvcsRepositoryMongoStorage.findAll()) {
      LOGGER.debug("Processing sources repository {}", dvcsRepository.getName());
      try {
        gitService.initLocalFileSet(dvcsRepository.getName() + "-statistics",
                                    new File(System.getProperty("java.io.tmpdir"), dvcsRepository.getName()),
                                    dvcsRepository);
      } catch (AcceptanceException e) {
        LOGGER.error("Error while extracting statistics from repository {}", dvcsRepository.getName());
      }
    }
    LOGGER.debug("Source repositories processed");
  }
}
