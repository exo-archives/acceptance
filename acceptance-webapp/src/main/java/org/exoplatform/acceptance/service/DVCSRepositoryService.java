package org.exoplatform.acceptance.service;

import org.exoplatform.acceptance.model.vcs.DVCSRepository;
import org.exoplatform.acceptance.model.vcs.VCSCoordinates;
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
  DVCSRepositoryMongoStorage dvcsRepositoryMongoStorage;
  @Inject
  CredentialService credentialService;

  @Override
  DVCSRepositoryMongoStorage getMongoStorage() {
    return dvcsRepositoryMongoStorage;
  }

  /**
   * Updates an existing entity with all its related entities. Use the returned instance for further operations as the save
   * operation might have changed the entity instance completely.
   * This default implementation only saves the given entity.
   *
   * @param entity the entity to update
   * @return the saved entity
   * @throws EntityNotFoundException in case there is no entity with the given {@code id}
   */
  @Override
  public <S extends DVCSRepository> S cascadingUpdate(S entity) throws EntityNotFoundException {
    LOGGER.debug("Cascading update of repository {}", entity.getName());
    for (VCSCoordinates remoteRepository : entity.getRemoteRepositories()) {
      // We save each credential used to access to a remote repository
      credentialService.cascadingUpdate(remoteRepository.getCredential());
    }
    // We save our repository
    update(entity);
    return entity;
  }

  /**
   * Saves a given entity (creates it if it doesn't exist) and all its related entities. Use the returned instance for further
   * operations as the save operation might have changed the entity instance completely.
   * This default implementation only creates or saves the given entity.
   *
   * @param entity the entity to update
   * @return the saved entity
   */
  @Override
  public <S extends DVCSRepository> S cascadingUpdateOrCreate(S entity) {
    LOGGER.debug("Cascading update or creation of repository {}", entity.getName());
    for (VCSCoordinates remoteRepository : entity.getRemoteRepositories()) {
      // We save each credential used to access to a remote repository
      credentialService.cascadingUpdateOrCreate(remoteRepository.getCredential());
    }
    // We save our repository
    updateOrCreate(entity);
    return entity;
  }

  public DVCSRepository findByName(String name) {
    return dvcsRepositoryMongoStorage.findByName(name);
  }
}
