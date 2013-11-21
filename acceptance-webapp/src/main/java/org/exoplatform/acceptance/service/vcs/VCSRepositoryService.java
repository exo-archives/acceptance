package org.exoplatform.acceptance.service.vcs;

import org.exoplatform.acceptance.model.vcs.VCSFileSet;
import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.AbstractMongoCRUDService;
import org.exoplatform.acceptance.service.CRUDService;
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
 */
@Named
public class VCSRepositoryService extends AbstractMongoCRUDService<VCSRepository> implements CRUDService<VCSRepository> {

  private static final Logger LOGGER = LoggerFactory.getLogger(VCSRepositoryService.class);
  @Inject
  private VCSRepositoryMongoStorage vcsRepositoryMongoStorage;
  @Inject
  private CredentialService credentialService;

  @Override
  protected VCSRepositoryMongoStorage getMongoStorage() {
    return vcsRepositoryMongoStorage;
  }

  public VCSRepository findByName(String name) {
    return vcsRepositoryMongoStorage.findByName(name);
  }

  public VCSFileSet initLocalFileSet(
      @NotNull File basedir,
      @NotNull VCSRepository repository) {
    return new VCSFileSet(basedir, repository);
  }

  // Every minute
  @Scheduled(fixedRate = 60000)
  public void extractSourceStatistics() {
    LOGGER.debug("Starting to process source repositories");
    for (VCSRepository VCSRepository : vcsRepositoryMongoStorage.findAll()) {
      LOGGER.debug("Processing sources repository {}", VCSRepository.getName());
      initLocalFileSet(
          new File(System.getProperty("java.io.tmpdir"), VCSRepository.getName()),
          VCSRepository);
    }
    LOGGER.debug("Source repositories processed");
  }

}
