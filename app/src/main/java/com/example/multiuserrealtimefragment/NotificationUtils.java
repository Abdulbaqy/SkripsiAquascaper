package com.example.multiuserrealtimefragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    String id = "Aquascaper";
    String name = "Aquascaper Notification";
    String description = "Notification for Aquascaper App";
    Context context;
    NotificationManager notificationManager;
    NotificationChannel notificationChannel;

    public NotificationUtils(Context applicationContext) {
        this.context = applicationContext;
        notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(description);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public void showProjectPaid() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Project already paid!")
                .setContentText("Customer has made a payment!")
                .setOngoing(true)
                .setChannelId(id);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }

    public void showProjectDone() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Project has been done!")
                .setContentText("PT. Aquascaper has forwarded the payment!")
                .setOngoing(true)
                .setChannelId(id);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, mBuilder.build());
    }
}
