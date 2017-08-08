package edu.cnm.bootcamp.kelly.authormatefinal.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;

/**
 * Created by kelly on 8/8/17.
 */


@DatabaseTable(tableName = "PROJECTUPDATE")
public class ProjectUpdate {

  @DatabaseField(columnName = "PROJECT_ID", canBeNull = false, foreign = true)
  private Project project;

  @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", format = "yyyy-MM-dd HH:mm:ss", readOnly = true)
  private Timestamp created;

  @DatabaseField(columnName = "COUNT", canBeNull = false)
  private Integer count;

  @DatabaseField(columnName = "NOTES")
  private String notes;

  public ProjectUpdate() {
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Timestamp getCreated() {
    return created;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

}
