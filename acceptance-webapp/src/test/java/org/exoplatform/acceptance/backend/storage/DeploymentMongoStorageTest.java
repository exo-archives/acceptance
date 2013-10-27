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
package org.exoplatform.acceptance.backend.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import org.exoplatform.acceptance.backend.model.Application;
import org.exoplatform.acceptance.backend.model.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 */
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class DeploymentMongoStorageTest {

  @Inject
  DeploymentMongoStorage deploymentMongoStorage;

  @Before
  public void setUp() {
    // cleanup the collection if any Tenant
    this.deploymentMongoStorage.deleteAll();
  }

  @After
  public void tearDown() {
    // cleanup the collection
    this.deploymentMongoStorage.deleteAll();
  }

  @Test
  public void create() {
    this.deploymentMongoStorage.deleteAll();
    Application application = new Application();
    application.setName("my application");
    Deployment deployment = new Deployment();
    deployment.setApplication(application);
    Deployment savedDeployment = deploymentMongoStorage.save(deployment);
    assertNotNull("The deployment ID should not be null", savedDeployment.getId());
    assertEquals("We should have exactly 1 deployment", 1, deploymentMongoStorage.count());
  }

  @Test
  public void delete() {
    this.deploymentMongoStorage.deleteAll();
    Application application = new Application();
    application.setName("my application");
    Deployment deployment = new Deployment();
    deployment.setApplication(application);
    Deployment savedDeployment = deploymentMongoStorage.save(deployment);
    deploymentMongoStorage.delete(savedDeployment);
    assertEquals("We should have exactly 0 deployment", 0, deploymentMongoStorage.count());
  }

  @Test
  public void update() {
    this.deploymentMongoStorage.deleteAll();
    Application application = new Application();
    application.setName("my application");
    Deployment deployment = new Deployment();
    deployment.setApplication(application);
    Deployment savedDeployment = deploymentMongoStorage.save(deployment);
    deployment.getApplication().setName("my deployment 2");
    deploymentMongoStorage.save(savedDeployment);
    assertEquals("We should have exactly 1 deployment", 1, deploymentMongoStorage.count());
    assertEquals("my deployment 2", deploymentMongoStorage.findOne(savedDeployment.getId()).getApplication().getName());
  }

}
