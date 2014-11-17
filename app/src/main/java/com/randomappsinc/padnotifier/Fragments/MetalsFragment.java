package com.randomappsinc.padnotifier.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Adapters.MetalsListAdapter;
import com.randomappsinc.padnotifier.Activities.MainActivity;
import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Metals.MetalSchedule;
import com.randomappsinc.padnotifier.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MetalsFragment extends Fragment
{
    private static View rootView;
    public static Context context;
    private ProgressBar mProgress;
    private TextView mMetalMessage;
    private ListView mMetalsList;
//    private int mProgressStatus = 0;

    // Display font size.
    private static final float METALS_MESSAGE_SIZE = 18;

    // Display message for no metals information.
    private static final String NO_METALS = "It looks like we are unable to " +
            "fetch the metals information for today. Please try again later.";

    private static final String TAG = "MetalsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_metals, container, false);
        context = getActivity().getApplicationContext();
        return rootView;
    }

    // TODO: Put up a progress spinny thing while the app is pulling data.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mProgress = (ProgressBar) getView().findViewById(R.id.progressBarMetals);
        mProgress.setVisibility(View.GONE);

        System.out.println("Files:");
        for (String filename : context.fileList()) {
            System.out.println(filename);
        }

        // XXX: Doesn't check if the cache is outdated. A cache will be considered outdated
        //      if it's older than 1 AM (PDT) of the current day.

        // If we have a cache
            File metals_info = new File(context.getFilesDir(), MainActivity.METALS_CACHE_FILENAME);
            Calendar refreshTime = Calendar.getInstance(TimeZone.getTimeZone("America/Los Angeles"));
            refreshTime.set(Calendar.HOUR_OF_DAY, 2); /* 2 AM PDT of the current day. TODO: Change this for Japan time.*/

        if (Arrays.asList(context.fileList()).contains(MainActivity.METALS_CACHE_FILENAME) &&
                metals_info.lastModified() > refreshTime.getTimeInMillis()) {
            renderMetals();
        }
        else {
            new getMetalsList().execute("This string isn't used.");
        }

        super.onViewCreated(view, savedInstanceState);
    }

    public static void renderMetals()
    {
        MetalSchedule.getInstance();
        if (MetalSchedule.timesIsEmpty(2 /* US Country Code*/, MainActivity.getGroup()))
        {
            TextView metalsMessage = (TextView) rootView.findViewById(R.id.metalsMessage);
            metalsMessage.setText(NO_METALS);
            metalsMessage.setTextSize(METALS_MESSAGE_SIZE);
        }
        else
        {
            Log.d(TAG, "Rendering metals...");
            ListView metalsList = (ListView) rootView.findViewById(R.id.metalsList);
            MetalSchedule masterSchedule = MetalSchedule.getInstance();
            MetalsListAdapter metalsAdapter = new MetalsListAdapter(context,
                    masterSchedule.getTimeslots(2 /* US Country Code */, MainActivity.getGroup()));
            metalsList.setAdapter(metalsAdapter);
        }
    }

    private class getMetalsList extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... strings) {
            // Parameter not used. Return value not used, either.

            HttpGet httppost = new HttpGet("http://www.padherder.com/api/events/");
            HttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(httppost);

                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    DataFetcher.extractPadherderAPIJSON(data);

                    // TODO: Make this a function or something.
                    FileOutputStream fos;
                    try {
                        // Open a writer to the cache file.
                        fos = MainActivity.mContext.openFileOutput(MainActivity.METALS_CACHE_FILENAME, Context.MODE_PRIVATE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.wtf(TAG, "Couldn't cache the JSON because file not found."
                                + " Isn't Context.MODE_PRIVATE supposed to take care of that?");

                        return new Long(-1);
                    }

                    try {
                        // Write the contents of the data pull to this file.
                        fos.write(data.getBytes());
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

            } catch (IOException e) {
                e.printStackTrace();
                return new Long(-1);
            }
            return null;
        }

        /**
         * This step is normally used to setup the task, for instance
         * by showing a progress bar in the user interface.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = (ProgressBar) getView().findViewById(R.id.progressBarMetals);
            mMetalsList = (ListView) rootView.findViewById(R.id.metalsList);
            mMetalMessage = (TextView) rootView.findViewById(R.id.metalsMessage);

            mProgress.setVisibility(View.VISIBLE);
            mMetalsList.setVisibility(View.GONE);
            mMetalMessage.setVisibility(View.GONE);
        }

        /**
         *
         * Invoked on the UI thread after the background computation finishes.
         * The result of the background computation is passed to this step as a parameter.
         *
         * @param aLong
         */
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            mProgress.setVisibility(View.GONE);
            mMetalsList.setVisibility(View.VISIBLE);
            mMetalMessage.setVisibility(View.VISIBLE);

            renderMetals();
        }
    }
}