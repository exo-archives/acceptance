/*
 * Copyright 2013 eXo Platform SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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