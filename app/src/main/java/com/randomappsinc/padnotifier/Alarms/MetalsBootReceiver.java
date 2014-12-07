package com.randomappsinc.padnotifier.Alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Misc.Util;

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
        DataFetcher dataFetcher = new DataFetcher(context);

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            if (!Util.cacheIsUpdated(context)) {
                dataFetcher.syncFetchData();
            }
            else {
                dataFetcher.extractMetalsFromStorage();
                dataFetcher.extractGodfestInfoFromStorage();
            }

            metalsAlarm.setAlarm(context);
            dataAlarm.setAlarm(context);
        }
    }
}
//END_INCLUDE(autostart)
