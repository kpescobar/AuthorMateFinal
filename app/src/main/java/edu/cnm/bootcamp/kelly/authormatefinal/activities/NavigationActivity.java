package edu.cnm.bootcamp.kelly.authormatefinal.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.Project;
import edu.cnm.bootcamp.kelly.authormatefinal.fragments.HomeFragment;
import edu.cnm.bootcamp.kelly.authormatefinal.fragments.ProjectFragment;
import edu.cnm.bootcamp.kelly.authormatefinal.helpers.AndroidDatabaseManager;
import edu.cnm.bootcamp.kelly.authormatefinal.helpers.OrmHelper;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Created by kelly on 8/8/17.
 */

public class NavigationActivity extends AppCompatActivity {

  private DrawerLayout drawerLayout;
  private ListView drawerList;
  private NavigationView drawer;
  private ActionBarDrawerToggle drawerToggle;

  private CharSequence drawerTitle;
  private CharSequence title;
//  private String[] projectTitles;

  private OrmHelper helper = null;

  public synchronized OrmHelper getHelper() {
    if (helper == null) {
        helper = OpenHelperManager.getHelper(this, OrmHelper.class);
    }
    return helper;
  }

  public synchronized void releaseHelper() {
    if (helper != null) {
      OpenHelperManager.releaseHelper();
      helper = null;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getHelper().getWritableDatabase().close();
    setContentView(R.layout.activity_navigation);

//    FloatingActionButton newprButton = (FloatingActionButton) findViewById(R.id.newprButton);
//    newprButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent createIntent = new Intent(NavigationActivity.this, NewProject.class);
//        startActivity(createIntent);
//      }
//    });

//    Button databaseButton = (Button) findViewById(R.id.databaseButton);
//    databaseButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent testIntent = new Intent(NavigationActivity.this, AndroidDatabaseManager.class);
//        startActivity(testIntent);
//      }
//    });
//
//    Button listButton = (Button) findViewById(R.id.listButton);
//    listButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent listActivity = new Intent(NavigationActivity.this, TestActivity.class);
//        startActivity(listActivity);
//      }
//    });


    title = drawerTitle = getTitle();
//    projectTitles = getResources().getStringArray(R.array.project_array);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer = (NavigationView) findViewById(R.id.nav_view);
    drawerList = (ListView) findViewById(R.id.left_drawer_list);

    // set up the drawer's list view with items and click listener
    setNavigationProjects();
    drawerList.setOnItemClickListener(new DrawerItemClickListener());

    // enable ActionBar app icon to behave as action to toggle nav drawer
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    // ActionBarDrawerToggle ties together the the proper interactions
    // between the sliding drawer and the action bar app icon
    drawerToggle = new ActionBarDrawerToggle(
        this,                  /* host Activity */
        drawerLayout,         /* DrawerLayout object */
        R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
        R.string.drawer_open,  /* "open drawer" description for accessibility */
        R.string.drawer_close  /* "close drawer" description for accessibility */
    ) {
      public void onDrawerClosed(View view) {
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
      }

      public void onDrawerOpened(View drawerView) {
        getSupportActionBar().setTitle(drawerTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
      }
    };
    drawerLayout.setDrawerListener(drawerToggle);

    drawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer); // added
    Fragment fragment = new HomeFragment();
    switchFragment(fragment);

//    if (savedInstanceState == null) {
//      selectItem(0, projectTitles[0]);
//    }
  }

  public void setNavigationProjects() {
    ListView drawerList = (ListView) findViewById(R.id.left_drawer_list);
    List<Project> projects = getProjects();
    drawerList.setAdapter(new ArrayAdapter<Project>(this,
        R.layout.drawer_list_item, projects));
  }

  @Override
  protected void onStart() {
    super.onStart();
    getHelper();
  }

  @Override
  protected void onStop() {
    super.onStop();
    releaseHelper();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
//      inflater.inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  /* Called whenever we call invalidateOptionsMenu() */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    // If the nav drawer is open, hide action items related to the content view
    boolean drawerOpen = drawerLayout.isDrawerOpen(drawer);
//      menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // The action bar home/up action should open or close the drawer.
    // ActionBarDrawerToggle will take care of this.
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    // Handle action buttons
//      switch(item.getItemId()) {
//        case R.id.action_websearch:
//          // create intent to perform web search for this planet
//          Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//          intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//          // catch event that there's no activity to handle intent
//          if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//          } else {
//            Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//          }
//          return true;
//        default:
//          return super.onOptionsItemSelected(item);
//      }
    return true;
  }

  /* The click listner for ListView in the navigation drawer */
  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      selectItem(position, (Project) parent.getItemAtPosition(position));
    }
  }
// listener
  private void selectItem(int position, Project project) {
    // update the main content by replacing fragments
    Fragment fragment = new ProjectFragment();
    Bundle args = new Bundle();
    args.putString(ProjectFragment.PROJECT_TITLE, project.getTitle());
    args.putInt(ProjectFragment.PROJECT_ID, project.getId());
    fragment.setArguments(args);

    switchFragment(fragment);

    // update selected item and title, then close the drawer
    drawerList.setItemChecked(position, true);
    setTitle(title);
    drawerLayout.closeDrawer(drawer);
  }


  public void switchFragment(Fragment fragment) {
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
  }


  @Override
  public void setTitle(CharSequence title) {
    this.title = title;
    getSupportActionBar().setTitle(this.title);
  }

  /**
   * When using the ActionBarDrawerToggle, you must call it during
   * onPostCreate() and onConfigurationChanged()...
   */

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggls
    drawerToggle.onConfigurationChanged(newConfig);
  }


  private List<Project> getProjects() {
    try {
      Dao<Project, Integer> dao = getHelper().getProjectDao();
      QueryBuilder<Project, Integer> builder = dao.queryBuilder();
      builder.orderBy("CREATED", true);
      return dao.query(builder.prepare());
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

}





















