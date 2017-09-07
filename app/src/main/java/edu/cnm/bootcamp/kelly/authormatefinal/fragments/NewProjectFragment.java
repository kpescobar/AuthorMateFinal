package edu.cnm.bootcamp.kelly.authormatefinal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.j256.ormlite.dao.Dao;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import edu.cnm.bootcamp.kelly.authormatefinal.activities.NavigationActivity;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.Project;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.ProjectUpdate;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by kelly on 8/9/17.
 */

public class NewProjectFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View rootView = inflater.inflate(R.layout.fragment_newproject, container, false);

    Button createButton = (Button) rootView.findViewById(R.id.createButton);
    createButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //TODO shouldn't be enabled if entries are not correct
        try {
          Dao<Project, Integer> dao = ((NavigationActivity) getActivity()).getHelper().getProjectDao();
          Project project = new Project();

          EditText editTitle = (EditText) rootView.findViewById(R.id.editTitle);
          project.setTitle(editTitle.getText().toString());

          EditText editWordCount = (EditText) rootView.findViewById(R.id.editWordCount);
          project.setGoal(Integer.parseInt(editWordCount.getText().toString()));

          SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

          EditText editDeadline = (EditText) rootView.findViewById(R.id.editDeadline);
          project.setTarget(format.parse(editDeadline.getText().toString()));


          dao.create(project);
        } catch (SQLException ex) {
          ex.printStackTrace();
        } catch (ParseException ex) {
          ex.printStackTrace();

        }
        Fragment fragment = new HomeFragment();
        ((NavigationActivity) getActivity()).setNavigationProjects();
        ((NavigationActivity) getActivity()).switchFragment(fragment);
      }
    });

    return rootView;
  }

}
