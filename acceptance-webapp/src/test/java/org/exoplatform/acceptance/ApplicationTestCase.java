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

import static org.junit.Assert.assertEquals;

import java.net.URL;

import juzu.arquillian.Helper;
import lombok.extern.slf4j.Slf4j;
import org.exoplatform.acceptance.Application;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RunWith(Arquillian.class)
@Slf4j
public class ApplicationTestCase {

  @Drone
  WebDriver driver;

  @ArquillianResource
  URL deploymentURL;

  @Deployment
  public static WebArchive createDeployment() {
    WebArchive war = Helper.createBaseServletDeployment("spring");
    war.addPackages(true, Application.class.getPackage());
    return war;
  }

  @Test
  @RunAsClient
  public void testFoo() {
    driver.get(deploymentURL.toString());
    ApplicationTestCase.log.info("Source: " + driver.getPageSource());
    //WebElement body = driver.findElement(By.tagName("body"));
    //assertEquals("Hello World", body.getText());
  }
}