package edu.cnm.bootcamp.kelly.authormatefinal.entities;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kelly on 8/8/17.
 */

public class Notification {

  @DatabaseField(columnName = "PROJECT_ID", canBeNull = false, foreign = true)
  private Project project;

  @DatabaseField(columnName = "ENABLED", canBeNull = false, defaultValue = "false")
  private Boolean enabled;

  @DatabaseField(columnName = "TIME", canBeNull = false)
  private String time;

  public Notification() {
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
