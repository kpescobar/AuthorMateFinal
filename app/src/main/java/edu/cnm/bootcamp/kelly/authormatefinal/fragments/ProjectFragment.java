package edu.cnm.bootcamp.kelly.authormatefinal.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import edu.cnm.bootcamp.kelly.authormatefinal.activities.NavigationActivity;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.Project;
import edu.cnm.bootcamp.kelly.authormatefinal.entities.ProjectUpdate;
import edu.cnm.bootcamp.kelly.authormatefinal.helpers.OrmHelper;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kelly on 8/8/17.
 */

public class ProjectFragment extends Fragment implements OnClickListener {

  public static final String PROJECT_TITLE = ProjectFragment.class.getName() + "PROJECT_TITLE";
  public static final String PROJECT_ID = ProjectFragment.class.getName() + "PROJECT_ID";
  private Project currentProject;

  protected void timeSpinner(View view) {
    Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
    String[] times = getResources().getStringArray(R.array.time);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, times);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    final View layout = inflater.inflate(R.layout.fragment_project, container, false);

    Bundle args = getArguments();
    final int projectId = args.getInt(PROJECT_ID);

    timeSpinner(layout);

    final EditText editTextUpdate = (EditText) layout.findViewById(R.id.editTextUpdate);
    editTextUpdate.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        Button updateButton = (Button) layout.findViewById(R.id.updateButton);
        if (editTextUpdate.getText().toString().trim().length() == 0) {
          updateButton.setEnabled(false);
        } else {
          updateButton.setEnabled(true);
        }
      }
    });

    try {
      Dao<Project, Integer> projectDao = ((NavigationActivity) getActivity()).getHelper()
          .getProjectDao();
      currentProject = projectDao.queryForId(projectId);

      TextView projectText = (TextView) layout.findViewById(R.id.projectText);
      projectText.setText(currentProject.getTitle().toString());

      TextView wordCountText = (TextView) layout.findViewById(R.id.wordCountTextView);
      wordCountText.setText(Integer.toString(currentProject.getGoal()));

      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

      TextView deadlineText = (TextView) layout.findViewById(R.id.deadlineTextView);
      deadlineText.setText(format.format(currentProject.getTarget()));

      updateStats(layout);

//      Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
//      currentProject.setTime

    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    RadioButton onButton = (RadioButton) layout.findViewById(R.id.radioButtonOn);
    onButton.setOnClickListener(this);

    RadioButton offButton = (RadioButton) layout.findViewById(R.id.radioButtonOff);
    offButton.setOnClickListener(this);
//Added all of this...
    Button updateButton = (Button) layout.findViewById(R.id.updateButton);
    updateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Dao<ProjectUpdate, Integer> dao = ((NavigationActivity) getActivity()).getHelper().getProjectUpdateDao();
          ProjectUpdate update = new ProjectUpdate();

          EditText editTextUpdate = (EditText) layout.findViewById(R.id.editTextUpdate);
          update.setCount(Integer.parseInt(editTextUpdate.getText().toString()));

          editTextUpdate.setText(""); //added

          update.setProject(currentProject);

          dao.create(update);

          updateStats(layout);

        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    });

    return layout;
  }

  private void updateStats(View layout) {

    TextView wordsWrittenText = (TextView) layout.findViewById(R.id.wordsWrittenText);
    int wordsWritten = getWordsWritten();
    wordsWrittenText.setText(Integer.toString(wordsWritten));

    TextView wordsRemainingText = (TextView) layout.findViewById(R.id.wordsRemainingText);
    int wordsRemaining = currentProject.getGoal() - wordsWritten;
    wordsRemainingText.setText(Integer.toString(Math.max(0, wordsRemaining)));

    TextView advisedDailyText = (TextView) layout.findViewById(R.id.advisedDailyText);
    int daysRemaining = daysRemaining();
    if (wordsRemaining <= 0) {
      //TODO print yay!
    } else if (daysRemaining <= 0) {
      //TODO can't meet
    } else {
      int wordsPerDay = Math.round(wordsRemaining / (float) daysRemaining);
      advisedDailyText.setText(Integer.toString(wordsPerDay));
    }


  }

  private int daysRemaining() {
    Date target = currentProject.getTarget();
    Date today = new Date();
    if (target.after(today)) {
      long timeRemaining = target.getTime()- today.getTime();
      return (int) Math.ceil(timeRemaining/1000/60/60/24.0);
    } else {
      return 0;
    }
  }

  private int getWordsWritten() {
    try {
      Dao<ProjectUpdate, Integer> updateDao = ((NavigationActivity) getActivity()).getHelper().getProjectUpdateDao();
      QueryBuilder<ProjectUpdate, Integer> updateBuilder = updateDao.queryBuilder();

      Dao<Project, Integer> projectDao = ((NavigationActivity) getActivity()).getHelper().getProjectDao();
      QueryBuilder<Project, Integer> projectBuilder = projectDao.queryBuilder();

      projectBuilder.where().eq("PROJECT_ID", currentProject.getId());

      updateBuilder.join(projectBuilder);

      updateBuilder.selectRaw("SUM(COUNT)");

      GenericRawResults<String[]> results = updateDao.queryRaw(updateBuilder.prepareStatementString());

      String[] values = results.getFirstResult();

      int count = (values == null || values.length == 0 || values[0] == null) ? 0 : Integer.parseInt(values[0]);

      return count;


    } catch (SQLException ex) {
      ex.printStackTrace();
      return 0;
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  @Override
  public void onClick(View view) {
    switch(view.getId()) {
      case R.id.radioButtonOn:
        getActivity().findViewById(R.id.spinner).setVisibility(View.VISIBLE);
        break;
      case R.id.radioButtonOff:
        getActivity().findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
          break;
//      case R.id.updateButton:
    }
  }



}































