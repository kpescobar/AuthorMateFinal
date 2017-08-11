package edu.cnm.bootcamp.kelly.authormatefinal.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import java.sql.Timestamp;

/**
 * Created by kelly on 8/8/17.
 */

  @DatabaseTable(tableName = "PROJECT")
  public class Project {

    @DatabaseField(columnName = "PROJECT_ID", generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "TITLE", canBeNull = false, width = 100, unique = true)
    private String title;

    @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", format = "yyyy-MM-dd HH:mm:ss", readOnly = true)
    private Timestamp created;

    @DatabaseField(columnName = "GOAL", canBeNull = false)
    private Integer goal;

    @DatabaseField(columnName = "TARGET", canBeNull = false, format = "yyyy-MM-dd")
    private Date target;

    @DatabaseField(columnName = "NOTES")
    private String notes;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<ProjectUpdate> updates;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Notification> notification;


    public Project() {
    }

    public int getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public Timestamp getCreated() {
      return created;
    }

    public Integer getGoal() {
      return goal;
    }

    public void setGoal(Integer goal) {
      this.goal = goal;
    }

    public Date getTarget() {
      return target;
    }

    public void setTarget(Date target) {
      this.target = target;
    }

    public String getNotes() {
      return notes;
    }

    public void setNotes(String notes) {
      this.notes = notes;
    }

    @Override
    public String toString() {
      return getTitle();
    }

}
