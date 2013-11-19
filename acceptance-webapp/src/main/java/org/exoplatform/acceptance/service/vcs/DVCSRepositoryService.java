package org.exoplatform.acceptance.service.vcs;

import org.exoplatform.acceptance.model.vcs.DVCSRepository;
import org.exoplatform.acceptance.service.AbstractMongoCRUDService;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.service.credential.CredentialService;
import org.exoplatform.acceptance.storage.DVCSRepositoryMongoStorage;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Services to manage DVCS Repositories
 */
@Named
public class DVCSRepositoryService extends AbstractMongoCRUDService<DVCSRepository> implements CRUDService<DVCSRepository> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DVCSRepositoryService.class);
  @Inject
  private DVCSRepositoryMongoStorage dvcsRepositoryMongoStorage;
  @Inject
  private CredentialService credentialService;

  @Override
  protected DVCSRepositoryMongoStorage getMongoStorage() {
    return dvcsRepositoryMongoStorage;
  }

  public DVCSRepository findByName(String name) {
    return dvcsRepositoryMongoStorage.findByName(name);
  }
}
