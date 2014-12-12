package com.randomappsinc.padnotifier.Alarms;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.randomappsinc.padnotifier.Activities.MainActivity;
import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

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
public class DataSchedulingService extends IntentService {
    public DataSchedulingService() {
        super("DataSchedulingService");
    }

    public static final String TAG = "DataSchedulingService";
    public static final String PDX_URL = "http://www.puzzledragonx.com";
    // public static final String PADH_URL = "https://www.padherder.com/api/events/";

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO: Change this back to pulling PDX once the PDX parsing code is finished.

        String urlString = PDX_URL;
        String result = "";

        // Try to connect to the PDX homepage and download content.
        try {
            result = loadFromNetwork(urlString);
        } catch (IOException e) {
            Log.i(TAG, "Alarm-based data fetching failed to connect.");

            // TODO: Set alarm to try curling again here.

            DataAlarmReceiver.completeWakefulIntent(intent);
            return;
        }

        DataFetcher df = new DataFetcher(this);
        df.extractPDXMetalsContent(result);
        df.extractPDXGodfestContent(result);

        MetalsAlarmReceiver metalsAlarmReceiver = new MetalsAlarmReceiver();
        metalsAlarmReceiver.setAlarm(this);

        // DataFetcher.extractPadherderAPIJSON(result);
        Log.i(TAG, "Alarm-based data fetching pulled successfully.");

        // Release the wake lock provided by the BroadcastReceiver.
        DataAlarmReceiver.completeWakefulIntent(intent);
    }

    //
    // The methods below this line fetch content from the specified URL and return the
    // content as a string.
    //
    // Shamelessly stolen from the Android examples documentation.
    //
    /** Given a URL string, initiate a fetch operation. */
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    /**
     * Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from www.google.com.
     * @return String version of InputStream.
     * @throws IOException
     */
    private String readIt(InputStream stream) throws IOException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for(String line = reader.readLine(); line != null; line = reader.readLine())
            builder.append(line);
        reader.close();
        return builder.toString();
    }
}
