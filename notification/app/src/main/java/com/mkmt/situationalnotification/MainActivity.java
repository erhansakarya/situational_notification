package com.mkmt.situationalnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button notifyButton;
    private NotificationCompat.Builder notificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifyButton = findViewById(R.id.buttonNotify);

        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifySituational();
            }
        });
    }

    public void notifySituational() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channelId";
            String channelName = "channelName";
            String channelDesc = "channelDesc";
            int channelPriority = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);

            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(channelId, channelName, channelPriority);
                notificationChannel.setDescription(channelDesc);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            notificationBuilder = new NotificationCompat.Builder(this, channelId);

            notificationBuilder.setContentTitle("title");
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_dashboard_24);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);
        } else {

            notificationBuilder = new NotificationCompat.Builder(this);

            notificationBuilder.setContentTitle("title");
            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_dashboard_24);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
            notificationBuilder.setContentIntent(pendingIntent);
        }

        notificationManager.notify(1, notificationBuilder.build());
    }
}