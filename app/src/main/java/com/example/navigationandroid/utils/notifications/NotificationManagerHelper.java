package com.example.navigationandroid.utils.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.navigationandroid.R;

import java.util.Random;

public class NotificationManagerHelper {

    private final static int NOTIFICATION_ID = NotificationManagerHelper.class.hashCode();

    private static NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sendNotificationEvent(Context context, NotificationEvent notificationEvent) {
        Intent intent = new Intent();
        sendNotificationEvent(context, intent, notificationEvent);
    }

    /**
     * This function is used to create notification
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void sendNotificationEvent(Context context, Intent intent, NotificationEvent notificationEvent) {
        String CHANNEL_ID = context.getResources().getString(R.string.app_name);// The id of the channel.
        CharSequence name = context.getResources().getString(R.string.app_name);// The user-visible name of the channel.

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.notificacion_sound);
        //Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notificacion_sound);

        context.grantUriPermission("com.android.systemui", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);

            AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build();
            mChannel.setSound(uri, attributes);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.white))
                .setSmallIcon(getNotificationIcon())
                .setContentTitle(notificationEvent.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationEvent.getSubject()))
                .setContentText(notificationEvent.getBody())
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                //.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                .setChannelId(CHANNEL_ID);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setSound(uri);
        }

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_ALL;
        //notification.sound = uri;
        //notification.defaults |= Notification.DEFAULT_SOUND;
        int NOTIFICATION_ID = new Random().nextInt(1000 - 100) + 100;

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private static int getNotificationIcon() {
        boolean whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    public static void clearAll() {
        if (notificationManager != null)
            notificationManager.cancelAll();
    }
}
