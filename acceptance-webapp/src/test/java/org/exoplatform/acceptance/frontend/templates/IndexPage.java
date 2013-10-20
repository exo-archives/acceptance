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
package org.exoplatform.acceptance.frontend.templates;


import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndexPage extends Page {

  static final By LOGOUT_LINK = By.id("logoutLink");

  public IndexPage(WebDriver driver, URL contextPath) {
    super(driver, contextPath);
  }

  @Override
  protected String getPath() {
    return "";
  }

  @Override
  protected By getValidationQuery() {
    return By.id("indexPage");
  }

  public void logout() throws Exception {
    getDriver().findElement(LOGOUT_LINK).click();
  }
}