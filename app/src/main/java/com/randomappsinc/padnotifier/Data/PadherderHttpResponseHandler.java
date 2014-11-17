package com.randomappsinc.padnotifier.Data;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.randomappsinc.padnotifier.Activities.MainActivity;
import com.randomappsinc.padnotifier.Metals.MetalSchedule;

import org.apache.http.Header;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Alex on 10/23/2014.
 */
public class PadherderHttpResponseHandler extends AsyncHttpResponseHandler
{
    private static final String METALS_CACHE_FILENAME = "metals_info";
    private static final String TAG = "PDNHttpResponseHandler";

    @Override
    public void onStart()
    {
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response)
    {
        // Now that we have the data from the JSON, put it into Timeslots so we can use it.
        String content = new String(response);
        DataFetcher.extractPadherderAPIJSON(content);

        // If there are no American dungeons open, then it's treated like a failure. Another
        // request will need to be sent later.
        MetalSchedule schedule = MetalSchedule.getInstance();
        if (schedule.isEmpty(2 /* US Country Code */ )) {
            Log.d(TAG, "No info, try again later");
            // TODO: Try again later.
        }
        // Else, save the data for today. This is our cache, and we can extract it without
        // needing to pull data again for the day.
        else {
            String[] files = MainActivity.context.fileList();
            if (Arrays.asList(files).contains(METALS_CACHE_FILENAME)) {
                // Clear the cached info
                MainActivity.context.deleteFile(METALS_CACHE_FILENAME);
            }

            // TODO: Make this a function or something.
            FileOutputStream fos;
            try {
                // Open a writer to the cache file.
                fos = MainActivity.context.openFileOutput(METALS_CACHE_FILENAME, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.wtf(TAG, "Couldn't cache the JSON because file not found."
                        + " Isn't Context.MODE_PRIVATE supposed to take care of that?");
                return;
            }

            try {
                // Write the contents of the data pull to this file.
                fos.write(response);
                fos.flush();
            } catch (IOException e) {
                Log.e(TAG, "Failed to write to metals cache file.");
                e.printStackTrace();
            } finally {
                try {
                    // Try to close the file. Also, oh my god are you serious this
                    // is the standard for closing files in Java apparently
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // ignore ... any significant errors should already have been
                    // reported via an IOException from the final flush.
                    // Shamelessly copied from StackOverflow.
                }
            }
        }

        // TODO: Make the alarms stuff work.
        // MetalsAlarmReceiver alarm = new MetalsAlarmReceiver();
        // alarm.cancelAlarm(MainActivity.mContext);
        // alarm.setAlarm(MainActivity.mContext);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
    {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
        // TODO: Try to pull the data again in an hour or so.
    }

    @Override
    public void onRetry(int retryNo)
    {
        // called request is tried for the retryNo time
        String retryMsg = "Retry #" + retryNo + " to grab Padherder event info.";
        Log.d(TAG, retryMsg);
    }
}
