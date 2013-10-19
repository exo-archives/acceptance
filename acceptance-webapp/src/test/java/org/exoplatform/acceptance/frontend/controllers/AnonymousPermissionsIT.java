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

import static org.junit.Assert.assertTrue;

import org.exoplatform.acceptance.frontend.templates.AboutPage;
import org.exoplatform.acceptance.frontend.templates.IndexPage;
import org.exoplatform.acceptance.frontend.templates.SigninPage;
import org.exoplatform.acceptance.frontend.templates.admin.AdminIndexPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AnonymousPermissionsIT extends ArquillianSetup {
  /**
   * Validates that an unknown user cannot log in.
   */
  @Test
  @InSequence(1)
  public void login() throws Exception {
    SigninPage signinPage = new SigninPage(getDriver(), getDeploymentURL());
    signinPage.goTo();
    signinPage.login("foo", "foo");
    assertTrue(signinPage.validate());
  }

  /**
   * Validates that an anonymous user cannot display the home page and is redirected to the authentication page.
   */
  @Test
  @InSequence(2)
  public void index() throws Exception {
    IndexPage indexPage = new IndexPage(getDriver(), getDeploymentURL());
    SigninPage signinPage = new SigninPage(getDriver(), getDeploymentURL());
    indexPage.goTo();
    assertTrue(signinPage.validate());
  }

  /**
   * Validates that an anonymous user can display the about page.
   */
  @Test
  @InSequence(3)
  public void about() throws Exception {
    AboutPage aboutPage = new AboutPage(getDriver(), getDeploymentURL());
    aboutPage.goTo();
    assertTrue(aboutPage.validate());
  }

  /**
   * Validates that an anonymous user cannot display the administration home page and is redirected to the authentication page.
   */
  @Test
  @InSequence(4)
  public void admin() throws Exception {
    AdminIndexPage adminIndexPage = new AdminIndexPage(getDriver(), getDeploymentURL());
    SigninPage signinPage = new SigninPage(getDriver(), getDeploymentURL());
    adminIndexPage.goTo();
    assertTrue(signinPage.validate());
  }
}
