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
package org.exoplatform.acceptance.model;

import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * an acceptance deployment
 */
@Document(collection = "deployments")
public class Deployment extends StorableObject {

  private Application application;
  private Agent agent;
  private Environment environment;

  public Deployment(@NotNull String name) {
    super(name);
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Agent getAgent() {
    return agent;
  }

  public void setAgent(Agent agent) {
    this.agent = agent;
  }

  public Environment getEnvironment() {
    return environment;
  }

  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
}
