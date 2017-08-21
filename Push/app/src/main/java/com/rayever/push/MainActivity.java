package com.rayever.push;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, RSSPullService.class);
        startService(intent);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mHandler, new IntentFilter("com.rayever.push_MSG"));

        final Button getTokenButton = (Button)findViewById(R.id.buttonToken);

        getTokenButton.setOnClickListener(new View.OnClickListener()
          {
                public void onClick(View v)
                {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    Log.d("Push", "Refreshed token: " + refreshedToken);
                }
          });

//        try
//        {
//            FirebaseInstanceId.getInstance().deleteInstanceId();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }

        addNotification();


//        AlertDialog.Builder alert=new AlertDialog.Builder(this);
//        alert.setMessage("Alert Box Test");
//        alert.setTitle("Error");
//        alert.setPositiveButton("OK", null);
//        alert.create().show();

//        final Calendar cld = Calendar.getInstance();
//        int time = cld.get(Calendar.HOUR_OF_DAY);
    }

    private void addNotification()
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private BroadcastReceiver mHandler = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String title = intent.getStringExtra("title");
            String msg = intent.getStringExtra("message");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }

    };

    @Override
    protected void onPause()
    {
        super.onPause();
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mHandler, new IntentFilter("com.rayever.push_MSG"));
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.create_notification_activiy, menu);
//        return true;
//    }
}
