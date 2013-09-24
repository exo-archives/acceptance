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
  /** Validate that we inject and retrieve correctly the current version of the project */
  public void testGetVersion() throws Exception {
    log.info(">>>> Settings : {}", settings);
    Assert.assertEquals(System.getProperty("project.version"), settings.getVersion());

  }
}
