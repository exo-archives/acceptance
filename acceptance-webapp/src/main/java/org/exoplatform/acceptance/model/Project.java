package org.exoplatform.acceptance.model;

import java.net.URL;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represent a Software manageable by acceptance.
 */
@Document(collection = "project")
@TypeAlias("Project")
public class Project {

  @Id
  private String id;

  private String name;

  private String description;

  private URL site;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public URL getSite() {
    return site;
  }

  public void setSite(URL site) {
    this.site = site;
  }
}
