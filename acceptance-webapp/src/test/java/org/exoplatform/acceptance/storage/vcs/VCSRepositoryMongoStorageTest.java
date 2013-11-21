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
package org.exoplatform.acceptance.storage.vcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.service.AcceptanceException;

import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/org/exoplatform/acceptance/storage/spring-test.xml"})
@ActiveProfiles("test")
public class VCSRepositoryMongoStorageTest {

  @Inject
  private VCSRepositoryMongoStorage vcsRepositoryMongoStorage;

  @Before
  public void setUp() {
    // cleanup the collection if any Tenant
    vcsRepositoryMongoStorage.deleteAll();
  }

  @After
  public void tearDown() {
    // cleanup the collection
    vcsRepositoryMongoStorage.deleteAll();
  }

  @Test
  public void create() throws AcceptanceException {
    VCSRepository savedVCSRepository = createAndSaveVcsRepository();
    assertNotNull("The repository ID should not be null", savedVCSRepository.getId());
    assertEquals("We should have exactly 1 repository", 1, vcsRepositoryMongoStorage.count());
  }

  @Test
  public void delete() throws AcceptanceException {
    VCSRepository savedVCSRepository = createAndSaveVcsRepository();
    vcsRepositoryMongoStorage.delete(savedVCSRepository);
    assertEquals("We should have exactly 0 repository", 0, vcsRepositoryMongoStorage.count());
  }

  @Test
  public void update() throws AcceptanceException {
    VCSRepository savedVCSRepository = createAndSaveVcsRepository();
    savedVCSRepository.setName("my repository 2");
    savedVCSRepository.addRemoteRepository("other", "git@github.com:other/acceptance.git", "fooCredentialId");
    vcsRepositoryMongoStorage.save(savedVCSRepository);
    assertEquals("We should have exactly 1 repository", 1, vcsRepositoryMongoStorage.count());
    assertEquals("my repository 2", vcsRepositoryMongoStorage.findOne(savedVCSRepository.getId()).getName());
    assertEquals("repository should have 2 remotes", 2,
                 vcsRepositoryMongoStorage.findOne(savedVCSRepository.getId()).getRemoteRepositories().size());
  }

  private VCSRepository createAndSaveVcsRepository() throws AcceptanceException {
    VCSRepository VCSRepository = new VCSRepository("acceptance");
    VCSRepository.addRemoteRepository("origin", "git@github.com:exoplatform/acceptance.git", "fooCredentialId");
    return vcsRepositoryMongoStorage.save(VCSRepository);
  }

}
