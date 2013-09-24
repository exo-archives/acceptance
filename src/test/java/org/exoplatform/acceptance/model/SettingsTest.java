package org.exoplatform.acceptance.model;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsTest {

  @Inject
  Settings settings;

  @Test
  public void testMavenProperties() throws Exception {
    Assert.assertEquals(System.getProperty("project.version"), settings.getVersion());
    Assert.assertEquals(System.getProperty("project.inceptionYear"), settings.getInceptionYear());
    Assert.assertEquals(System.getProperty("project.organization.name"), settings.getOrganizationName());
    Assert.assertEquals(System.getProperty("project.organization.url"), settings.getOrganizationUrl());
  }

}
