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

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.Activity;
import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.arquillian.warp.servlet.AfterServlet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
@WarpTest
@Slf4j
public class ApplicationIT {

  @Drone
  WebDriver driver;

  @ArquillianResource
  URL deploymentURL;

  @Deployment
  public static WebArchive createDeployment() {
    WebArchive war = ShrinkWrap.create(ZipImporter.class, "ROOT.war").importFrom(new File(System.getProperty("targetDir"), System.getProperty("archiveName"))).as(WebArchive.class);
    //log.debug("Archive content : \n {}", war.toString(true));
    return war;
  }

  @Test
  @RunAsClient
  @Ignore
  /**
   * Need to manage authentication before being able to do such tests
   */
  public void testFoo() {
    driver.get(deploymentURL.toString()+"/signin");
    log.info("Source: " + driver.getPageSource());
    //WebElement body = driver.findElement(By.tagName("body"));
    //assertEquals("Hello World", body.getText());
  }

  @Test
  @Ignore
  public void shouldReturnUnAuthorizedOnAuthFailure() throws Exception {
    Warp.initiate(new Activity() {

      @Override
      public void perform() {
        try {
          final HttpURLConnection conn = (HttpURLConnection) deploymentURL.openConnection();
          conn.setInstanceFollowRedirects(false);
          Assert.assertEquals(401, conn.getResponseCode());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }).inspect(new Inspection() {
      @AfterServlet
      public void validate() {
        Assert.assertTrue(true);
      }

      @Override
      public int hashCode() {
        return super.hashCode();
      }
    });
  }
}