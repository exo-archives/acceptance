package org.exoplatform.acceptance.mongo;

import javax.inject.Inject;
import org.exoplatform.acceptance.controllers.BaseController;
import org.exoplatform.acceptance.model.Application;
import org.exoplatform.acceptance.model.Deployment;
import org.exoplatform.acceptance.repositories.ApplicationRepository;
import org.exoplatform.acceptance.repositories.DeploymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class DevDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

  @Inject
  ApplicationRepository applicationRepository;

  @Inject
  DeploymentRepository deploymentRepository;

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
      Deployment dep1 = createDeployment(app1);
      Deployment dep2 = createDeployment(app1);
      Deployment dep3 = createDeployment(app2);
      Deployment dep4 = createDeployment(app2);
      Deployment dep5 = createDeployment(app3);
    }
  }

  private Application createApplication(String name) {
    Application application = new Application();
    application.setName(name);
    LOGGER.debug("DevDataLoader - new Application : {}", application);
    return applicationRepository.save(application);
  }

  private Deployment createDeployment(Application app) {
    Deployment deployment = new Deployment();
    deployment.setApplication(app);
    LOGGER.debug("DevDataLoader - new Deployment : {}", deployment);
    return deploymentRepository.save(deployment);
  }

}
