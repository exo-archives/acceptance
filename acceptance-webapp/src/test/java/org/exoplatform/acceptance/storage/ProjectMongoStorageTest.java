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

import org.exoplatform.acceptance.model.Project;
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
public class ProjectMongoStorageTest {

  @Inject
  private ProjectMongoStorage projectMongoStorage;
  @Inject
  private DVCSRepositoryMongoStorage dvcsRepositoryMongoStorage;
  @Inject
  private CredentialMongoStorage credentialMongoStorage;

  @Before
  public void setUp() {
    // cleanup the collection if any Tenant
    projectMongoStorage.deleteAll();
    dvcsRepositoryMongoStorage.deleteAll();
    credentialMongoStorage.deleteAll();
  }

  @After
  public void tearDown() {
    // cleanup the collection
    projectMongoStorage.deleteAll();
    dvcsRepositoryMongoStorage.deleteAll();
    credentialMongoStorage.deleteAll();
  }

  @Test
  public void create() throws AcceptanceException {
    Project savedProject = createAndSaveProject();
    assertNotNull("The project ID should not be null", savedProject.getId());
    assertEquals("We should have exactly 1 project", 1, projectMongoStorage.count());
  }

  @Test
  public void delete() throws AcceptanceException {
    Project savedProject = createAndSaveProject();
    projectMongoStorage.delete(savedProject);
    assertEquals("We should have exactly 0 project", 0, projectMongoStorage.count());
  }

  @Test
  public void update() throws AcceptanceException {
    Project savedProject = createAndSaveProject();
    savedProject.setName("my project 2");
    projectMongoStorage.save(savedProject);
    assertEquals("We should have exactly 1 project", 1, projectMongoStorage.count());
    assertEquals("my project 2", projectMongoStorage.findOne(savedProject.getId()).getName());
    assertEquals("The project should have one source repository", 1,
                 projectMongoStorage.findOne(savedProject.getId()).getSourceRepositories().size());
    assertEquals("dvcsRepository should have 2 remotes", 2, projectMongoStorage.findOne(savedProject.getId()).getSourceRepository(
        "acceptance").getRemoteRepositories().size());
  }

  private Project createAndSaveProject() throws AcceptanceException {
    credentialMongoStorage.save(Credential.NONE);
    Credential tokenCredential = credentialMongoStorage.save(new TokenCredential("token", "abcdef"));
    DVCSRepository dvcsRepository = new DVCSRepository("acceptance");
    dvcsRepository.addRemoteRepository("origin", "git@github.com:exoplatform/acceptance.git", tokenCredential);
    dvcsRepository.addRemoteRepository("other", "git@github.com:other/acceptance.git", Credential.NONE);
    Project project = new Project("my project");
    project.addSourceRepository(dvcsRepositoryMongoStorage.save(dvcsRepository));
    return projectMongoStorage.save(project);
  }
}
