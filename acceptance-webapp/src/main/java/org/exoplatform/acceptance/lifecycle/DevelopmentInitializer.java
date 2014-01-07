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
package org.exoplatform.acceptance.lifecycle;

import org.exoplatform.acceptance.annotation.Development;
import org.exoplatform.acceptance.service.DevDataLoaderService;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Bootstrap service is used to initialize the application in a development environment
 */
@Named
@Development
public class DevelopmentInitializer extends ProductionInitializer implements ApplicationListener<ContextRefreshedEvent> {
  private static final Logger LOGGER = LoggerFactory.getLogger(DevelopmentInitializer.class);
  @Inject
  private DevDataLoaderService devDataLoaderService;

  /**
   * Handle an application event.
   *
   * @param event the event to respond to
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    super.onApplicationEvent(event);
    ApplicationContext context = (ApplicationContext) event.getSource();
    // Load data in the root context only
    if (context.getParent() == null) {
      devDataLoaderService.initializeData();
    }
    LOGGER.info("Data are ready for development.");
  }

}
