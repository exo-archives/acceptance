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
package org.exoplatform.acceptance.model;

import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = {"classpath:test-context.xml"})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class ProjectSettingsTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectSettingsTest.class);

  @Inject
  ProjectSettings projectSettings;

  @Test
  public void testMavenProperties() throws Exception {
    Assert.assertEquals(System.getProperty("project.version"), projectSettings.getVersion());
    Assert.assertEquals(System.getProperty("project.inceptionYear"), projectSettings.getInceptionYear());
    Assert.assertEquals(System.getProperty("project.organization.name"), projectSettings.getOrganizationName());
    Assert.assertEquals(System.getProperty("project.organization.url"), projectSettings.getOrganizationUrl());
  }

}
