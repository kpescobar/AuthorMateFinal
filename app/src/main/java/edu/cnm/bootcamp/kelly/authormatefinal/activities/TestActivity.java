package edu.cnm.bootcamp.kelly.authormatefinal.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.Project;
import edu.cnm.bootcamp.kelly.authormatefinal.helpers.OrmHelper;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kelly on 8/9/17.
 */

public class TestActivity extends AppCompatActivity {
  private OrmHelper ormHelper = null;

  private synchronized OrmHelper getOrmHelper() {
    if (ormHelper == null) {
      ormHelper = OpenHelperManager.getHelper(this, OrmHelper.class);
    }
    return ormHelper;
  }

  private synchronized void releaseHelper() {
    if (ormHelper != null) {
      OpenHelperManager.releaseHelper();
      ormHelper = null;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    try {
      Dao<Project, Integer> dao = getOrmHelper().getProjectDao();
      List<Project> projects = dao.queryForAll();
      ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, projects);
      ((ListView) findViewById(R.id.queryList)).setAdapter(adapter);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  protected void onDestroy() {
    releaseHelper();
    super.onDestroy();
  }
}
