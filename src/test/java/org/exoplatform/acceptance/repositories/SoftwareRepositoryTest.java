/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
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
package org.exoplatform.acceptance.repositories;

import javax.inject.Inject;
import org.exoplatform.acceptance.model.Software;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SoftwareRepositoryTest {

  @Inject
  SoftwareRepository softwareRepository;

  @Before
  public void setUp() {
    // cleanup the collection if any Tenant
    this.softwareRepository.deleteAll();
  }

  @After
  public void tearDown() {
    // cleanup the collection
    this.softwareRepository.deleteAll();
  }

  @Test
  public void create() {
    Software software = new Software();
    software.setName("my software");

    Software saveSoftware = softwareRepository.save(software);

    Assert.assertNotNull("The software ID should not be null", saveSoftware.getId());

    Assert.assertEquals("We should have exactly 1 Software", 1, softwareRepository.count());
  }

}
