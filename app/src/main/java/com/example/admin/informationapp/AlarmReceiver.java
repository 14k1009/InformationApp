package com.example.admin.informationapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;


public class AlarmReceiver extends BroadcastReceiver {

    TextView textView;

    @Override
    public void onReceive(Context context, Intent intent){


        Toast.makeText(context,"ALARM onReceive",Toast.LENGTH_SHORT).show();

        int bid =intent.getIntExtra("intentID",0);

        Intent it=new Intent(context,MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(context,0,it,0);


        Notification n=new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("時間です")
                .setContentTitle("MEMO")
                .setContentText(intent.getCharSequenceExtra("text")).setVibrate(new long[]{0,200,100})
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build();


        NotificationManager nm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,n);
    }
}
