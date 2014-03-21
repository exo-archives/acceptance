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

import org.exoplatform.acceptance.annotation.Production;
import org.exoplatform.acceptance.model.credential.Credential;
import org.exoplatform.acceptance.service.credential.CredentialService;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Bootstrap service is used to initialize the application in a production environment
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named
@Production
public class ProductionInitializer implements ApplicationListener<ContextRefreshedEvent> {
  /** Constant <code>LOGGER</code> */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductionInitializer.class);
  @Inject
  private CredentialService credentialService;

  /**
   * {@inheritDoc}
   *
   * Handle an application event.
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext context = (ApplicationContext) event.getSource();
    // Execute once at the root context only
    if (context.getParent() == null) {
      if (credentialService.findByType(Credential.Type.NONE).size() == 0) {
        // Always load the NO CREDENTIAL entry in the repository if it doesn't exist
        credentialService.updateOrCreate(Credential.NONE);
      }
      // Otherwise we load its ID in our Singleton
      Credential.NONE.setId(credentialService.findByName(Credential.NONE.getName()).getId());
      LOGGER.info("NoCredential ID : {}", Credential.NONE.getId());
      // We may add an upgrade system here if necessary
      LOGGER.info("Data are ready for production.");
    }
  }

}
