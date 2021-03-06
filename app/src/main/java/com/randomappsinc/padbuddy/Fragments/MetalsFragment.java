package com.randomappsinc.padbuddy.Fragments;

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

import com.randomappsinc.padbuddy.Activities.MainActivity;
import com.randomappsinc.padbuddy.Adapters.MetalsListAdapter;
import com.randomappsinc.padbuddy.Alarms.MetalsAlarmReceiver;
import com.randomappsinc.padbuddy.Data.DataFetcher;
import com.randomappsinc.padbuddy.Metals.MetalSchedule;
import com.randomappsinc.padbuddy.Misc.PreferencesManager;
import com.randomappsinc.padbuddy.Misc.Util;
import com.randomappsinc.padbuddy.Models.Timeslot;
import com.randomappsinc.padbuddy.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MetalsFragment extends Fragment
{
    private static View rootView;
    private static ProgressBar progress;
    private static TextView metalsMessage;
    public static ListView metalsList;

    public static Context context;
    private static MetalSchedule metalSchedule;
    private static DataFetcher dataFetcher;

    private static PreferencesManager m_prefs_manager;
    private static final float METALS_MESSAGE_SIZE = 18;
    public static final String METALS_CACHE_FILENAME = "metals_info";

    // Display message for no metals information.
    private static final String NO_METALS = "It looks like we are unable to " +
            "fetch the metals information for today. If you have no internet access at the moment, try again when you get it.";
    private static final String SOME_METALS = "It looks like we are unable to " +
            "fetch all of the metals information for today. Please try again later.";

    private static final String TAG = "MetalsFragment";

    public static ListView getMetalsList()
    {
        return metalsList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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
        metalsMessage = (TextView) rootView.findViewById(R.id.metalsMessage);
        metalsList = (ListView) rootView.findViewById(R.id.metalsList);
        MainActivity.setUpMetalsListener();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        progress = (ProgressBar) getView().findViewById(R.id.progressBarMetals);
        progress.setVisibility(View.GONE);

        // If we have a cache that isn't outdated, render as normal. Else, re-pull the data.
        if (Util.cacheIsUpdated(getActivity(), METALS_CACHE_FILENAME))
        {
            if (metalSchedule.getNumKeys() == 0)
            {
                dataFetcher.extractMetalsFromStorage();
            }
            renderMetals();
            new MetalsAlarmReceiver().setAlarm(getActivity());
        }
        else
        {
            refreshView();
        }

        super.onViewCreated(view, savedInstanceState);
    }

    public static void refreshView()
    {
        context.deleteFile(METALS_CACHE_FILENAME);
        metalSchedule.clearTimeslots();
        new getMetalsList().execute("This string isn't used.");
    }

    public static void renderMetals()
    {
        MetalSchedule.getInstance();
        if (MetalSchedule.timesIsEmpty(2 /* US Country Code*/, m_prefs_manager.getGroup()))
        {
            metalsMessage = (TextView) rootView.findViewById(R.id.metalsMessage);
            metalsMessage.setText(NO_METALS);
            metalsMessage.setTextSize(METALS_MESSAGE_SIZE);

            // Clear the list, make it empty
            MetalsListAdapter metalsAdapter = new MetalsListAdapter(context, new ArrayList<Timeslot>());
            metalsList.setAdapter(metalsAdapter);
        }
        else
        {
            Log.d(TAG, "Rendering metals...");
            metalsMessage.setVisibility(View.GONE);
            metalsList = (ListView) rootView.findViewById(R.id.metalsList);
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

            // Display dungeons in order of the day.
            Collections.sort(timeslots);

            MetalsListAdapter metalsAdapter = new MetalsListAdapter(context, timeslots);
            metalsList.setAdapter(metalsAdapter);
        }

        Log.d(TAG, "Finished rendering metals.");
        MetalSchedule.printMap();
    }

    private static class getMetalsList extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... strings) {

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

        // Done before task
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress.setVisibility(View.VISIBLE);
            metalsList.setVisibility(View.GONE);
            metalsMessage.setVisibility(View.GONE);
        }

        // Done after task
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            progress.setVisibility(View.GONE);
            metalsList.setVisibility(View.VISIBLE);
            metalsMessage.setVisibility(View.VISIBLE);

            renderMetals();
        }
    }
}