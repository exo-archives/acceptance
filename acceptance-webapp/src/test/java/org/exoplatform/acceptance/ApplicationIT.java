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
package org.exoplatform.acceptance;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import junit.framework.AssertionFailedError;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class ApplicationIT {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationIT.class);

  @Drone
  WebDriver driver;

  @ArquillianResource
  URL deploymentURL;

  @Deployment
  public static WebArchive deployment() {
    WebArchive war = ShrinkWrap.create(WebArchive.class);
    war.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    war.addAsWebInfResource(new File("src/main/webapp/WEB-INF/applicationContext.xml"));
    war.addAsWebInfResource(new File("src/main/webapp/WEB-INF/rest-servlet.xml"));
    war.addPackages(true, "org.exoplatform.acceptance");
    return war;
  }

  public URL getApplicationURL(String application) {
    try {
      return deploymentURL.toURI().resolve(application).toURL();
    } catch (Exception e) {
      AssertionFailedError afe = new AssertionFailedError();
      afe.initCause(e);
      throw afe;
    }
  }

  @Test
  @RunAsClient
  public void testAboutTitle() throws Exception {
    URL url = getApplicationURL("about");
    driver.get(url.toString());
    assertTrue(driver.getTitle().contains("About eXo Acceptance"));
  }

}
