package com.randomappsinc.padnotifier.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.randomappsinc.padnotifier.Metals.MetalSchedule;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.Models.Timeslot;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Derek on 10/26/2014.
 */
public class MetalsAlarmReceiver extends WakefulBroadcastReceiver {
    private AlarmManager alarmMgr;
    private LinkedList<PendingIntent> alarmIntents;
    private static final String TAG = "MetalsAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // BEGIN_INCLUDE(alarm_onreceive)
        Intent service = new Intent(context, MetalsSchedulingService.class);

        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, service);
        // END_INCLUDE(alarm_onreceive)
    }

    // BEGIN_INCLUDE(set_alarm)
    /**
     * Sets a repeating alarm that runs once a day at approximately 8:30 a.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     * @param context
     */
    public void setAlarm(Context context) {
        PreferencesManager m_prefs_manager = new PreferencesManager(context);

        Log.d(TAG, "setAlarm(Context) for metals is called");

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmIntents = new LinkedList<PendingIntent>();

        // If there are no times for today, return empty timeslot list.
        ArrayList<Timeslot> timeslots;
        if (MetalSchedule.timesIsEmpty(2 /* USA Country Code */, m_prefs_manager.getGroup())) {
            timeslots = new ArrayList<Timeslot>();
        }
        else {
            timeslots = MetalSchedule.getTimeslots(2 /* USA Country Code */, m_prefs_manager.getGroup());
        }

        // Make an alarm for each timeslot
        int request_id = 0;
        for (int i = 0; i < timeslots.size(); ++i) {
            // Add the pending intent for each timeslot
            alarmIntents.add(PendingIntent.getBroadcast(context, request_id,
                    new Intent(context, MetalsAlarmReceiver.class), 0));
            // set the alarm
            alarmMgr.set(AlarmManager.RTC_WAKEUP, timeslots.get(i).getStarts_at().getTimeInMillis(), alarmIntents.get(i));

            Log.d(TAG, "Alarm: " + timeslots.get(i).getTitle() + " " +
                    Util.calendarToExactTime(timeslots.get(i).getStarts_at()));
        }

        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, MetalsBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            for (PendingIntent ai : alarmIntents) {
                alarmMgr.cancel(ai);
            }
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, MetalsBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)
}
