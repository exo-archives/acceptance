package org.exoplatform.acceptance.service.vcs;

import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.AbstractMongoCRUDService;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.service.credential.CredentialService;
import org.exoplatform.acceptance.storage.vcs.VCSRepositoryMongoStorage;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
