package com.randomappsinc.padnotifier.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Adapters.MetalsListAdapter;
import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Metals.MetalSchedule;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.Models.Timeslot;
import com.randomappsinc.padnotifier.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class MetalsFragment extends Fragment
{
    private static View rootView;
    public static Context context;
    private ProgressBar progress;
    private TextView metalMessage;
    private ListView metalsList;
    private MetalSchedule metalSchedule;
    private DataFetcher dataFetcher;

    private static PreferencesManager m_prefs_manager;
    private static final float METALS_MESSAGE_SIZE = 18;
    public static final String METALS_CACHE_FILENAME = "metals_info";

    // Display message for no metals information.
    private static final String NO_METALS = "It looks like we are unable to " +
            "fetch the metals information for today. Please try again later.";
    private static final String SOME_METALS = "It looks like we are unable to " +
            "fetch all of the metals information for today. Please try again later.";

    private static final String TAG = "MetalsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        m_prefs_manager = new PreferencesManager(context);
        metalSchedule = MetalSchedule.getInstance();
        dataFetcher = new DataFetcher(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_metals, container, false);
        progress = (ProgressBar) rootView.findViewById(R.id.progressBarMetals);
        metalMessage = (TextView) rootView.findViewById(R.id.metalsMessage);
        metalsList = (ListView) rootView.findViewById(R.id.metalsList);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progress = (ProgressBar) getView().findViewById(R.id.progressBarMetals);
        progress.setVisibility(View.GONE);

        // If we have a cache that isn't outdated, render as normal. Else, re-pull the data.
        File metals_info = new File(context.getFilesDir(), METALS_CACHE_FILENAME);
        Calendar refreshTime = Calendar.getInstance();
        refreshTime.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        refreshTime.set(Calendar.HOUR_OF_DAY, 2); /* 2 AM PDT of the current day. TODO: Change this for Japan time later.*/
        refreshTime.set(Calendar.MINUTE, 0);

        if (Arrays.asList(context.fileList()).contains(METALS_CACHE_FILENAME) &&
                metals_info.lastModified() > refreshTime.getTimeInMillis())
        {
            if (metalSchedule.getNumKeys() == 0)
            {
                dataFetcher.extractMetalsFromStorage();
            }
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
        if (MetalSchedule.timesIsEmpty(2 /* US Country Code*/, m_prefs_manager.getGroup()))
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

            ArrayList<Timeslot> timeslots = masterSchedule.getTimeslots(2 /* US Country Code */,
                    m_prefs_manager.getGroup());

            // If any of the times are "--", display schedule-is-incomplete message
            for (Timeslot ts : timeslots) {
                if (ts.getStarts_at() == null) {
                    TextView metalsMessage = (TextView) rootView.findViewById(R.id.metalsMessage);
                    metalsMessage.setText(SOME_METALS);
                    metalsMessage.setTextSize(METALS_MESSAGE_SIZE);
                    break;
                }
            }

            MetalsListAdapter metalsAdapter = new MetalsListAdapter(context, timeslots);
            metalsList.setAdapter(metalsAdapter);
        }

        Log.d(TAG, "Finished rendering metals.");
        MetalSchedule.printMap();
    }

    private class getMetalsList extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... strings) {
            // Parameter not used. Return value not used, either.

            HttpGet httpget = new HttpGet("http://www.puzzledragonx.com/");
            HttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(httpget);

                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    dataFetcher.extractPDXMetalsContent(data);
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

            progress.setVisibility(View.VISIBLE);
            metalsList.setVisibility(View.GONE);
            metalMessage.setVisibility(View.GONE);
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

            progress.setVisibility(View.GONE);
            metalsList.setVisibility(View.VISIBLE);
            metalMessage.setVisibility(View.VISIBLE);

            renderMetals();
        }
    }
}