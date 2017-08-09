package edu.cnm.bootcamp.kelly.authormatefinal.fragments;

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

/**
 * Created by kelly on 8/9/17.
 */

public class HomeFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_home, container, false);


    FloatingActionButton howToUseButton = (FloatingActionButton) rootView.findViewById(R.id.howToUseButton);
    howToUseButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    Button createButton = (Button) rootView.findViewById(R.id.createButton);
    createButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

//    Button databaseButton = (Button) findViewById(R.id.databaseButton);
//    databaseButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent testIntent = new Intent(NavigationActivity.this, AndroidDatabaseManager.class);
//        startActivity(testIntent);
//      }
//    });
    return rootView;
  }

}
