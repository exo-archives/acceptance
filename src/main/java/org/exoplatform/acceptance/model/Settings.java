package org.exoplatform.acceptance.model;

import javax.inject.Named;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Acceptance Settings
 */
@Named("settings")
@Data
public class Settings {

  @Value("acceptance.version")
  private String version;

}
