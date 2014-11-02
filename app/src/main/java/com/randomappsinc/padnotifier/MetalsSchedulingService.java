package com.randomappsinc.padnotifier;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MetalsSchedulingService extends IntentService {

    /* **********************************************************
     * SHAMELESSLY CnP'D FROM THE ANDROID EXAMPLE DOCUMENTATION
     * **********************************************************/

    public static final String TAG = "MetalsSchedulingService";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 5552;
    // The string the app searches for in the Google home page content. If the app finds
    // the string, it indicates the presence of a doodle.
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;


    public MetalsSchedulingService() {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)

        // Create the notification and push it.
        alertMetalDragons();

        // Release the wake lock provided by the BroadcastReceiver.
        MetalsAlarmReceiver.completeWakefulIntent(intent);

        // END_INCLUDE(service_onhandle)
    }

    // Post a notification when a metals dungeon comes up.
    private void alertMetalDragons() {
        Log.d(TAG, "Alert! Metal Dragons!");

        // Notification to say that the app has opened. Take this out before pushing to app store.
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon_177)
                        .setContentTitle("PAD Notifier")
                        .setContentText("A metals dungeon is open!");
        int buzztime = 100;
        int pause = 5;
        long[] pattern = {0, buzztime, buzztime+pause, 2*buzztime+pause, 2*buzztime+2*pause, 3*buzztime+2*pause};
        mBuilder.setVibrate(pattern);
        mBuilder.setAutoCancel(true);

        // Set the Notification's Click Behavior (i.e., open PAD)
        Intent resultIntent = getPackageManager().getLaunchIntentForPackage("jp.gungho.padEN");
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = (int) System.currentTimeMillis();
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
