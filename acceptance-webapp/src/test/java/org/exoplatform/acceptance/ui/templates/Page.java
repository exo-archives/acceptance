/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
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
package org.exoplatform.acceptance.ui.templates;

import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public abstract class Page {

  private final WebDriver driver;

  private final URL contextPath;

  public Page(WebDriver driver, URL contextPath) {
    this.driver = driver;
    this.contextPath = contextPath;
  }

  abstract protected String getPath();

  abstract protected By getValidationQuery();

  public boolean validate() {
    return getDriver().findElement(getValidationQuery()) != null;
  }

  public WebDriver getDriver() {
    return driver;
  }

  public void goTo() throws Exception {
    getDriver().get(this.contextPath.toURI().resolve(getPath()).toURL().toString());
  }

}
