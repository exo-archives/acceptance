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
package org.exoplatform.acceptance.ui.controllers;

import static org.junit.Assert.assertTrue;

import org.exoplatform.acceptance.ui.templates.IndexPage;
import org.exoplatform.acceptance.ui.templates.administration.AdminIndexPage;

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
    IndexPage indexPage = new IndexPage(getDriver(), getDeploymentURL());
    indexPage.goTo();
    indexPage.login("foo", "foo");
    assertTrue(indexPage.validate());
    assertTrue(indexPage.displayLoginError());
  }

  /**
   * Validates that an anonymous user cannot display the home page and is redirected to the authentication page.
   */
  @Test
  @InSequence(2)
  public void index() throws Exception {
    IndexPage indexPage = new IndexPage(getDriver(), getDeploymentURL());
    indexPage.goTo();
    assertTrue(indexPage.validate());
  }

  /**
   * Validates that an anonymous user cannot display the administration home page and is redirected to the authentication page.
   */
  @Test
  @InSequence(3)
  public void admin() throws Exception {
    AdminIndexPage adminIndexPage = new AdminIndexPage(getDriver(), getDeploymentURL());
    IndexPage signinPage = new IndexPage(getDriver(), getDeploymentURL());
    adminIndexPage.goTo();
    assertTrue(signinPage.validate());
  }
}
