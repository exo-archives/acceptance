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
package org.exoplatform.acceptance.backend.storage;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.exoplatform.acceptance.backend.model.Application;
import org.exoplatform.acceptance.backend.model.Deployment;
import org.exoplatform.acceptance.backend.model.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
public class DevDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  @Inject
  private ApplicationRepository applicationRepository;

  @Inject
  private DeploymentRepository deploymentRepository;

  @Inject
  private ProjectRepository projectRepository;

  /**
   * Handle an application event.
   *
   * @param event the event to respond to
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext context = (ApplicationContext) event.getSource();
    // Load data in the root context
    if (context.getParent() == null) {
      // cleanup the collection if any Tenant
      applicationRepository.deleteAll();
      Application app1 = createApplication("Application #1");
      Application app2 = createApplication("Application #2");
      Application app3 = createApplication("Application #3");
      createDeployment(app1);
      createDeployment(app1);
      createDeployment(app2);
      createDeployment(app2);
      createDeployment(app3);
      createProject("Platform-UI");
      createProject("Commons");
      createProject("Content");
      createProject("Calendar");
      createProject("Social");
      createProject("Wiki");
      createProject("Forum");
      createProject("Integration");
      createProject("Platform");
    }
  }

  private Application createApplication(String name) {
    Application application = new Application();
    application.setName(name);
    log.debug("DevDataLoader - new Application : {}", application);
    return applicationRepository.save(application);
  }

  private Deployment createDeployment(Application app) {
    Deployment deployment = new Deployment();
    deployment.setApplication(app);
    log.debug("DevDataLoader - new Deployment : {}", deployment);
    return deploymentRepository.save(deployment);
  }

  private Project createProject(String name) {
    Project project = new Project();
    project.setName(name);
    try {
      project.setSite(new URL("http://www.exoplatform.org"));
    } catch (MalformedURLException e) {
    }
    project.setDescription("This is the description of " + name);
    log.debug("DevDataLoader - new Project : {}", project);
    return projectRepository.save(project);
  }
}
