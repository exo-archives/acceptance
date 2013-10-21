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
package org.exoplatform.acceptance.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.model.credential.TokenCredential;
import org.exoplatform.acceptance.model.vcs.DVCSRepository;
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
public class DVCSRepositoryMongoStorageTest {

  @Inject
  private DVCSRepositoryMongoStorage dvcsRepositoryMongoStorage;
  @Inject
  private CredentialMongoStorage credentialMongoStorage;

  @Before
  public void setUp() {
    // cleanup the collection if any Tenant
    dvcsRepositoryMongoStorage.deleteAll();
    credentialMongoStorage.deleteAll();
  }

  @After
  public void tearDown() {
    // cleanup the collection
    dvcsRepositoryMongoStorage.deleteAll();
    credentialMongoStorage.deleteAll();
  }

  @Test
  public void create() throws AcceptanceException {
    credentialMongoStorage.deleteAll();
    DVCSRepository savedDVCSRepository = createAndSaveDvcsRepository();
    assertNotNull("The dvcsRepository ID should not be null", savedDVCSRepository.getId());
    assertEquals("We should have exactly 1 dvcsRepository", 1, dvcsRepositoryMongoStorage.count());
  }

  @Test
  public void delete() throws AcceptanceException {
    credentialMongoStorage.deleteAll();
    DVCSRepository savedDVCSRepository = createAndSaveDvcsRepository();
    dvcsRepositoryMongoStorage.delete(savedDVCSRepository);
    assertEquals("We should have exactly 0 dvcsRepository", 0, dvcsRepositoryMongoStorage.count());
  }

  @Test
  public void update() throws AcceptanceException {
    credentialMongoStorage.deleteAll();
    DVCSRepository savedDVCSRepository = createAndSaveDvcsRepository();
    savedDVCSRepository.setName("my dvcsRepository 2");
    savedDVCSRepository.addRemoteRepository("other", "git@github.com:other/acceptance.git",
                                            credentialMongoStorage.findByName("abcdef"));
    dvcsRepositoryMongoStorage.save(savedDVCSRepository);
    assertEquals("We should have exactly 1 dvcsRepository", 1, dvcsRepositoryMongoStorage.count());
    assertEquals("my dvcsRepository 2", dvcsRepositoryMongoStorage.findOne(savedDVCSRepository.getId()).getName());
    assertEquals("dvcsRepository should have 2 remotes", 2,
                 dvcsRepositoryMongoStorage.findOne(savedDVCSRepository.getId()).getRemoteRepositories().size());
  }

  private DVCSRepository createAndSaveDvcsRepository() throws AcceptanceException {
    Credential credential = credentialMongoStorage.save(new TokenCredential("token", "abcdef"));
    DVCSRepository dvcsRepository = new DVCSRepository("acceptance");
    dvcsRepository.addRemoteRepository("origin", "git@github.com:exoplatform/acceptance.git", credential);
    return dvcsRepositoryMongoStorage.save(dvcsRepository);
  }

}
