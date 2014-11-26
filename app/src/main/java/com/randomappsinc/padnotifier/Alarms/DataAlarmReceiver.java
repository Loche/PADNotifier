package com.randomappsinc.padnotifier.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.randomappsinc.padnotifier.Activities.MainActivity;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;

import java.util.Calendar;
import java.util.Random;

// TODO: ALL OF THIS.
// The alarms part works just fine, as far as turning on the app and setting an alarm and having
// it go off immediately goes... The call to the async method, however, dies horribly. Logcat is a
// graveyard of blue RuntimeExceptions.
public class DataAlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    private static final String TAG = "DataAlarmReceiver";

    public DataAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        PreferencesManager prefsManager = new PreferencesManager(context);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent service = new Intent(context, DataSchedulingService.class);

        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, service);
    }

    /**
     * Sets a repeating alarm that runs once a day between 12:30 AM and 1:30 AM. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DataAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to some time between 12:30 and 1:30 am.
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 30);

        Random rand = new Random();
        int jitter = rand.nextInt(1000 * 60 * 60 + 1); // an hour in milliseconds
        calendar.setTimeInMillis(calendar.getTimeInMillis() + jitter);

        boolean DEBUG = false;
        if (DEBUG) {
            calendar.setTimeInMillis(System.currentTimeMillis() + 30000);
        }

        // Set the alarm to fire between 12:30 and 1:30 AM, according to the device's
        // clock, and to repeat once a day.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

        // Enable {@code MetalsBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, MetalsBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    // NOTE: This isn't actually called anywhere right now, but maybe we can add "only pull data
    // manually" to the settings page later or something.
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, MetalsBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}