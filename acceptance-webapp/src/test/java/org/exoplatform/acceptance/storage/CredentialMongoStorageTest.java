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

import org.exoplatform.acceptance.model.credential.TokenCredential;

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
public class CredentialMongoStorageTest {

  @Inject
  private CredentialMongoStorage credentialMongoStorage;

  @Before
  public void setUp() {
    // cleanup the collection if any Tenant
    this.credentialMongoStorage.deleteAll();
  }

  @After
  public void tearDown() {
    // cleanup the collection
    this.credentialMongoStorage.deleteAll();
  }

  @Test
  public void create() {
    TokenCredential credential = new TokenCredential("my credential", "my token");
    TokenCredential savedCredential = credentialMongoStorage.save(credential);
    assertNotNull("The credential ID should not be null", savedCredential.getId());
    assertEquals("We should have exactly 1 credential", 1, credentialMongoStorage.count());
  }

  @Test
  public void delete() {
    TokenCredential credential = new TokenCredential("my credential", "my token");
    TokenCredential savedCredential = credentialMongoStorage.save(credential);
    credentialMongoStorage.delete(savedCredential);
    assertEquals("We should have exactly 0 credential", 0, credentialMongoStorage.count());
  }

  @Test
  public void update() {
    TokenCredential credential = new TokenCredential("my credential", "my token");
    TokenCredential savedCredential = credentialMongoStorage.save(credential);
    credential.setName("my credential 2");
    credentialMongoStorage.save(savedCredential);
    assertEquals("We should have exactly 1 credential", 1, credentialMongoStorage.count());
    assertEquals("my credential 2", credentialMongoStorage.findOne(savedCredential.getId()).getName());
  }

}
