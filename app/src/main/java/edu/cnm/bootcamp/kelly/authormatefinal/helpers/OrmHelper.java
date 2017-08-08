package edu.cnm.bootcamp.kelly.authormatefinal.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.Notification;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.Project;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.ProjectUpdate;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kelly on 8/8/17.
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "authormate.db";

  private static ConnectionSource source;

  private Dao<Project, Integer> projectDao = null;
  private Dao<ProjectUpdate, Integer> projectUpdateDao = null;
  private Dao<Notification, Integer> notificationDao = null;




  public OrmHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      source = connectionSource;
      TableUtils.createTable(connectionSource, Project.class);
      TableUtils.createTable(connectionSource, ProjectUpdate.class);
      TableUtils.createTable(connectionSource, Notification.class);
      populateDB();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {

  }

  private void populateDB() throws SQLException {
    Project project = new Project();
    project.setTitle("Prologue");
    project.setGoal(82000);
    project.setTarget(new Date(2018, 03, 15));
    getProjectDao().create(project);

    project = new Project();
    project.setTitle("Hello There");
    project.setGoal(94500);
    project.setTarget(new Date(2018, 06, 10));
    getProjectDao().create(project);

    project = new Project();
    project.setTitle("Epilogue");
    project.setGoal(32000);
    project.setTarget(new Date(2017, 11, 02));
    getProjectDao().create(project);

    ProjectUpdate prup = new ProjectUpdate();
    prup.setProject(project);
    prup.setCount(1000);
    getProjectUpdateDao().create(prup);

    Notification noti = new Notification();
    noti.setProject(project);
    noti.setTime("06:00");
    getNotificationDao().create(noti);

    prup = new ProjectUpdate();
    prup.setProject(project);
    prup.setCount(1200);
    prup.setNotes("Blah blah blah");
    getProjectUpdateDao().create(prup);

    noti = new Notification();
    noti.setProject(project);
    noti.setEnabled(true);
    noti.setTime("08:00");
    getNotificationDao().create(noti);

  }

  public synchronized Dao<Project, Integer> getProjectDao() throws SQLException {
    if (projectDao == null) {
      projectDao = getDao(Project.class);
    }
    return projectDao;
  }

  public synchronized Dao<ProjectUpdate, Integer> getProjectUpdateDao() throws SQLException {
    if (projectUpdateDao == null) {
      projectUpdateDao = getDao(ProjectUpdate.class);
    }
    return projectUpdateDao;
  }

  public synchronized Dao<Notification, Integer> getNotificationDao() throws SQLException {
    if (notificationDao == null) {
      notificationDao = getDao(Notification.class);
    }
    return notificationDao;
  }


  public ArrayList<Cursor> getData(String Query){
    //get writable database
    SQLiteDatabase sqlDB = this.getWritableDatabase();
    String[] columns = new String[] { "message" };
    //an array list of cursor to save two cursors one has results from the query
    //other cursor stores error message if any errors are triggered
    ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
    MatrixCursor Cursor2= new MatrixCursor(columns);
    alc.add(null);
    alc.add(null);

    try{
      String maxQuery = Query ;
      //execute the query results will be save in Cursor c
      Cursor c = sqlDB.rawQuery(maxQuery, null);

      //add value to cursor2
      Cursor2.addRow(new Object[] { "Success" });

      alc.set(1,Cursor2);
      if (null != c && c.getCount() > 0) {

        alc.set(0,c);
        c.moveToFirst();

        return alc ;
      }
      return alc;
    } catch(Exception ex){
      Log.d("printing exception", ex.getMessage());

      //if any exceptions are triggered save the error message to cursor an return the arraylist
      Cursor2.addRow(new Object[] { ""+ex.getMessage() });
      alc.set(1,Cursor2);
      return alc;
    }
  }

}
