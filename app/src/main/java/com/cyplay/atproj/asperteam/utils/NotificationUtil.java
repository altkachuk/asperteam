package com.cyplay.atproj.asperteam.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.cyplay.atproj.asperteam.R;

/**
 * Created by andre on 17-May-18.
 */

public class NotificationUtil {

    static private String CHANNEL_ID = "AsperTeam_id_01";
    static private String CHANNEL_NAME = "AsperTeam_name_01";

    public static void sendNotification(Context context, int titleRes, int textRes, Class activityClass) {
        Intent homeIntent = new Intent(context.getApplicationContext(), activityClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, homeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(context.getString(titleRes))
                .setContentText(context.getText(textRes))
                .setPriority(Notification.PRIORITY_MAX)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true);



        Notification notification = builder.build();

        int notifyID = 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notifyID, notification);
    }
}
