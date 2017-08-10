package edu.cnm.bootcamp.kelly.authormatefinal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.bootcamp.kelly.authormatefinal.R;

/**
 * Created by kelly on 8/9/17.
 */

public class NewProjectFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_newproject, container, false);
  }

}
