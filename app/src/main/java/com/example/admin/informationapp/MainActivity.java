package com.example.admin.informationapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Time;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    EditText editText;
//    int hour, minute;

    private DatePickerDialog.OnDateSetListener settingDate;
    private TimePickerDialog.OnTimeSetListener settingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textbox);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);

        Button bt = (Button) findViewById(R.id.settime);
        bt.setOnClickListener(this);

        settingTime=new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker v,int hour,int minute){
                sendIntent(hour,minute);
                finish();
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textbox:
                inputtext();
                break;
            case R.id.button1:
                sendNotification();
                break;
            case R.id.settime:
//                settime();
                setting();
                break;
        }
    }

    public void setting(){
        Calendar calendar=Calendar.getInstance();
        TimePickerDialog td=
                new TimePickerDialog(MainActivity.this,settingTime,
                        calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)
                        ,true);
        td.show();
    }

/*    public void settime() {
        Calendar cl = Calendar.getInstance();
        hour = cl.get(Calendar.HOUR_OF_DAY);
        minute = cl.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(
                this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Log.d("test", String.format("%02:%02d", hour, minute));
            }
        }, hour, minute, true);
        dialog.show();
        }
*/

    public void sendIntent(int hour, int minute) {
        Calendar triggerTime = Calendar.getInstance();
        triggerTime.set(Calendar.HOUR,hour);
        triggerTime.set(Calendar.MINUTE,minute);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("text",textView.getText());
        PendingIntent sender =
                PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), sender);
    }

    private void inputtext() {
        String text = editText.getText().toString();
        // 取得したテキストを TextView に張り付ける
        textView.setText(text);
    }

    private void sendNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        Notification n = new Notification.Builder(this)
                .setSmallIcon(R.drawable.common_ic_googleplayservices)
                .setTicker("Test")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("MEMO")
                .setContentText(textView.getText())
                .setContentIntent(pi)
                .build();

        nm.notify(1, n);
        finish();
    }



 /*   class Setteing implements View.OnClickListener{
        @Override
        public void onClick(View v){
            CompleteSetting setT=new CompleteSetting();

        }
    }

    class CompleteSetting implements android.content.DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface s, int t){
            sendIntent(hour,minute);
        }
    }*/
}
