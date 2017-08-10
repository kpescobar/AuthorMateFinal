package edu.cnm.bootcamp.kelly.authormatefinal.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
/**
 * Created by kelly on 8/8/17.
 */

public class ProjectFragment extends Fragment implements OnClickListener {



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View layout = null;
    // Inflate the layout for this fragment
    layout = inflater.inflate(R.layout.fragment_project, container, false);

    RadioButton onButton = (RadioButton) layout.findViewById(R.id.radioButtonOn);
    onButton.setOnClickListener(this);

    RadioButton offButton = (RadioButton) layout.findViewById(R.id.radioButtonOff);
    offButton.setOnClickListener(this);

    return layout;
  }

  @Override
  public void onClick(View view) {

    // Check which radio button was clicked
    switch(view.getId()) {
      case R.id.radioButtonOn:
        getActivity().findViewById(R.id.timePicker).setVisibility(View.VISIBLE);
        break;
      case R.id.radioButtonOff:
        getActivity().findViewById(R.id.timePicker).setVisibility(View.INVISIBLE);
          break;
    }
  }


}
