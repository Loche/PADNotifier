package com.randomappsinc.padnotifier.Alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be enabled (android:enabled="true") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class MetalsBootReceiver extends BroadcastReceiver {
    MetalsAlarmReceiver metalsAlarm = new MetalsAlarmReceiver();
    DataAlarmReceiver dataAlarm = new DataAlarmReceiver();

    public MetalsBootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // If we need to re-pull data, then do that
            // pullDataAndReloadCacheOrSomething();

            // metalsAlarm.setAlarm(context);
            dataAlarm.setAlarm(context);
        }
    }
}
//END_INCLUDE(autostart)
