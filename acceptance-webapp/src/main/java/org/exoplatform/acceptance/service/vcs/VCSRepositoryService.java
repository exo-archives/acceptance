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

import org.exoplatform.acceptance.model.vcs.VCSCoordinates;
import org.exoplatform.acceptance.model.vcs.VCSRef;
import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.AbstractMongoCRUDService;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.service.ConfigurationService;
import org.exoplatform.acceptance.storage.vcs.VCSRepositoryMongoStorage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
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
  /** Constant <code>REF_TO_VCSREF_FUNCTION</code> */
  private static final Function<Ref, VCSRef> REF_TO_VCSREF_FUNCTION = new Function<Ref, VCSRef>() {
    @Override
    // TODO : Juzu throws a NPE in live mode when using @Nullable annotation
    //@Nullable
    //public VCSRef apply(@Nullable Ref input) {
    public VCSRef apply(Ref input) {
      if (null == input) return null;
      if (input.getName().startsWith(Constants.R_HEADS)) {
        return new VCSRef(VCSRef.Type.BRANCH, input.getName().substring(Constants.R_HEADS.length()),
                          input.getObjectId().getName());
      }
      if (input.getName().startsWith(Constants.R_TAGS)) {
        return new VCSRef(VCSRef.Type.TAG, input.getName().substring(Constants.R_TAGS.length()), input.getObjectId().getName());
      }
      // Git notes and remotes refs aren't supported
      return null;
    }
  };

  @Inject
  private ConfigurationService configurationService;
  @Inject
  private VCSRepositoryMongoStorage vcsRepositoryMongoStorage;

  /** {@inheritDoc} */
  @Override
  protected VCSRepositoryMongoStorage getMongoStorage() {
    return vcsRepositoryMongoStorage;
  }

  /**
   * Returns the directory where is stored the clone of the repository to extract data
   *
   * @param repository The repository object
   * @return The directory
   */
  private File getCheckoutDir(VCSRepository repository) {
    return getCheckoutDir(repository.getId());
  }

  /**
   * Returns the directory where is stored the clone of the repository to extract data
   *
   * @param repositoryId The repository identifier
   * @return The directory
   */
  private File getCheckoutDir(String repositoryId) {
    return new File(configurationService.getVCSCheckoutDirectory(), repositoryId);
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

  // Every minute
  /**
   * <p>updateRepositories.</p>
   */
  @Scheduled(fixedRate = 120000)
  public void updateRepositories() {
    for (VCSRepository repository : vcsRepositoryMongoStorage.findAll()) {
      loadData(repository);
    }
    LOGGER.info("VCS data updated.");
  }

  /**
   * <p>loadData.</p>
   *
   * @param repository a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   */
  private void loadData(VCSRepository repository) {
    switch (repository.getType()) {
      case GIT:
        for (VCSCoordinates remote : repository.getRemoteRepositories()) {
          try {
            remote.setReferences(loadReferencesFromRemote(remote));
          } catch (GitAPIException e) {
            LOGGER.warn("Error while listing branches and tags of repository {} from its remote {} : {}",
                        repository.getName(), remote, e.getMessage());
          }
        }
        break;
      default:
        LOGGER.error("Unknown VCS repository type {}", repository.getType());
        return;
    }
    vcsRepositoryMongoStorage.save(repository);
  }

  /**
   * <p>loadReferencesFromRemote.</p>
   *
   * @param remote a {@link org.exoplatform.acceptance.model.vcs.VCSCoordinates} object.
   * @throws org.eclipse.jgit.api.errors.GitAPIException if any.
   * @return a {@link java.util.List} object.
   */
  private List<VCSRef> loadReferencesFromRemote(VCSCoordinates remote) throws GitAPIException {
    return FluentIterable.from(Git.lsRemoteRepository()
                                   .setHeads(true)
                                   .setTags(true)
                                   .setRemote(remote.getUrl())
                                   .call()).transform(REF_TO_VCSREF_FUNCTION).toList();
  }
}
