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
package org.exoplatform.acceptance.frontend.controllers;

import java.io.File;
import java.net.URL;

import junit.framework.AssertionFailedError;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.openqa.selenium.WebDriver;

public class ArquillianSetup {

  @Drone
  private WebDriver driver;

  @ArquillianResource
  private URL deploymentURL;

  /**
   * Creates a testing WAR of using ShrinkWrap
   *
   * @return WebArchive to be tested
   */
  @Deployment(testable = false)
  public static WebArchive deployment() {
    WebArchive war = ShrinkWrap.create(WebArchive.class);
    war.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    war.addAsWebInfResource(new File("src/main/webapp/WEB-INF/applicationContext.xml"));
    war.addAsWebInfResource(new File("src/main/webapp/WEB-INF/rest-servlet.xml"));
    war.addPackages(true, "org.exoplatform.acceptance");
    return war;
  }

  public String getPathURL(String path) {
    try {
      return deploymentURL.toURI().resolve(path).toURL().toString();
    } catch (Exception e) {
      AssertionFailedError afe = new AssertionFailedError();
      afe.initCause(e);
      throw afe;
    }
  }

  public WebDriver getDriver() {
    return driver;
  }

  public URL getDeploymentURL() {
    return deploymentURL;
  }
}
