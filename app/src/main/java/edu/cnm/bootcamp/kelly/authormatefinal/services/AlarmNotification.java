package edu.cnm.bootcamp.kelly.authormatefinal.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import edu.cnm.bootcamp.kelly.authormatefinal.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmNotification extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_notification);
    final ToggleButton toggle = (ToggleButton) findViewById(R.id.alarmToggle);
    toggle.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        TextView text = (TextView) findViewById(R.id.alarmText);
        Intent intent = new Intent(AlarmNotification.this, Receiver.class);
//        intent.putEx
        PendingIntent pending = PendingIntent.getBroadcast(AlarmNotification.this, 0, intent, 0);
        if (toggle.isChecked()) {
          TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
          Calendar calendar = Calendar.getInstance();
          SimpleDateFormat format = new SimpleDateFormat("HH:mm");
          calendar.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
          calendar.set(Calendar.MINUTE, picker.getCurrentMinute());
          manager = (AlarmManager) getSystemService(ALARM_SERVICE);
          manager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pending);
          text.setText(format.format(calendar.getTime()));
        } else {
          manager.cancel(pending);
          text.setText("Alarm Off");
        }
      }
    });
  }
  public void onToggleClicked(View view) {
  }

}
