package edu.cnm.bootcamp.kelly.authormatefinal.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import edu.cnm.bootcamp.kelly.authormatefinal.helpers.OrmHelper;
import java.util.Locale;

/**
 * Created by kelly on 8/8/17.
 */

public class NavigationActivity extends AppCompatActivity {

  private DrawerLayout drawerLayout;
  private ListView drawerList;
  private ActionBarDrawerToggle drawerToggle;

  private CharSequence drawerTitle;
  private CharSequence title;
  private String[] projectTitles;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_navigation);

    OrmHelper ormHelper = OpenHelperManager.getHelper(this, OrmHelper.class);
    ormHelper.getWritableDatabase().close();

    title = drawerTitle = getTitle();
    projectTitles = getResources().getStringArray(R.array.project_array);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerList = (ListView) findViewById(R.id.left_drawer);

    // set up the drawer's list view with items and click listener
    drawerList.setAdapter(new ArrayAdapter<String>(this,
        R.layout.drawer_list_item, projectTitles));
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

    if (savedInstanceState == null) {
      selectItem(0, projectTitles[0]);
    }
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
    boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
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
      selectItem(position, projectTitles[position]);
    }
  }

  private void selectItem(int position, String title) {
    // update the main content by replacing fragments
    Fragment fragment = new ProjectFragment();
    Bundle args = new Bundle();
    args.putString(ProjectFragment.PROJECT_TITLE, title);
    fragment.setArguments(args);

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    // update selected item and title, then close the drawer
    drawerList.setItemChecked(position, true);
    setTitle(title);
    drawerLayout.closeDrawer(drawerList);
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

  /**
   * Fragment that appears in the "content_frame", shows a planet
   */
  public static class ProjectFragment extends Fragment {

    public static final String PROJECT_TITLE = "project_title";

    public ProjectFragment() {
      // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_project, container, false);
      String title = getArguments().getString(PROJECT_TITLE);

//        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
//            "drawable", getActivity().getPackageName());
//        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
      getActivity().setTitle(title);
      return rootView;
    }
  }
}

