package edu.cnm.bootcamp.kelly.authormatefinal.fragments;

import static android.R.attr.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import edu.cnm.bootcamp.kelly.authormatefinal.activities.NavigationActivity;

/**
 * Created by kelly on 8/9/17.
 */

public class HomeFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_home, container, false);

    FloatingActionButton howToUseButton = (FloatingActionButton) rootView
        .findViewById(R.id.howToUseButton);
    howToUseButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment fragment = new HowToUseFragment();
        ((NavigationActivity) getActivity()).switchFragment(fragment);
      }
    });

    Button createButton = (Button) rootView.findViewById(R.id.createButton);
    createButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment fragment = new NewProjectFragment();
        ((NavigationActivity) getActivity()).switchFragment(fragment);
      }
    });
    return rootView;
  }
//  public void switchFragment(Fragment fragment) {
//    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
//    fragmentManager.beginTransaction().replace(R.id.fragment_home, fragment).commit();
//  }

//    Button databaseButton = (Button) findViewById(R.id.databaseButton);
//    databaseButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent testIntent = new Intent(NavigationActivity.this, AndroidDatabaseManager.class);
//        startActivity(testIntent);
//      }
//    });

}

