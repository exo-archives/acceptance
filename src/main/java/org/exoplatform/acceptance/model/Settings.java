package org.exoplatform.acceptance.model;

import javax.inject.Named;
import javax.inject.Singleton;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Acceptance Settings
 */
@Named("settings")
@Singleton
@Data
public class Settings {

  @Value("acceptance.version")
  private String version;
}
