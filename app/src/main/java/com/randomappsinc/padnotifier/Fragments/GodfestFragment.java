package com.randomappsinc.padnotifier.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Activities.MainActivity;
import com.randomappsinc.padnotifier.Adapters.GodfestListAdapter;
import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Godfest.GodfestOverview;
import com.randomappsinc.padnotifier.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class GodfestFragment extends Fragment
{
    private static View rootView;
    public static Context context;
    private static DataFetcher dataFetcher;

    // Views
    private static ProgressBar progress;
    private static TextView godfestMessage;
    private static ListView godfestList;

    public static final String GODFEST_CACHE_FILENAME = "godfest_info";

    public static ListView getGodsList()
    {
        return godfestList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        dataFetcher = new DataFetcher(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_godfest, container, false);
        context = getActivity().getApplicationContext();
        progress = (ProgressBar) rootView.findViewById(R.id.progressBarGodfest);
        godfestMessage = (TextView) rootView.findViewById(R.id.godfestMessage);
        godfestList = (ListView) rootView.findViewById(R.id.godsList);
        MainActivity.setUpGodsListener();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progress.setVisibility(View.GONE);

        // If we have a cache that isn't outdated, render as normal. Else, re-pull the data.
        File metals_info = new File(context.getFilesDir(), GODFEST_CACHE_FILENAME);
        Calendar refreshTime = Calendar.getInstance();
        refreshTime.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        refreshTime.set(Calendar.HOUR_OF_DAY, 2); /* 2 AM PDT of the current day. TODO: Change this for Japan time later.*/
        refreshTime.set(Calendar.MINUTE, 0);

        if (Arrays.asList(context.fileList()).contains(GODFEST_CACHE_FILENAME) &&
                metals_info.lastModified() > refreshTime.getTimeInMillis())
        {
            if (GodfestOverview.getFeaturedGods().size() == 0)
            {
                dataFetcher.extractGodfestInfoFromStorage();
            }
            renderGods();
        }
        else
        {
            new getGodfestList().execute("This string isn't used.");
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private static class getGodfestList extends AsyncTask<String, Integer, Long>
    {
        @Override
        protected Long doInBackground(String... strings)
        {
            HttpGet httpget = new HttpGet("http://www.puzzledragonx.com/");
            HttpClient client = new DefaultHttpClient();
            try
            {
                HttpResponse response = client.execute(httpget);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    dataFetcher.extractPDXGodfestContent(data);
                }
            }
            catch (IOException e)
            {
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
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            godfestList.setVisibility(View.GONE);
            godfestMessage.setVisibility(View.GONE);
        }

        /**
         *
         * Invoked on the UI thread after the background computation finishes.
         * The result of the background computation is passed to this step as a parameter.
         *
         * @param aLong
         */
        @Override
        protected void onPostExecute(Long aLong)
        {
            super.onPostExecute(aLong);
            progress.setVisibility(View.GONE);
            godfestList.setVisibility(View.VISIBLE);
            godfestMessage.setVisibility(View.VISIBLE);
            renderGods();
        }
    }

    public static void refreshView()
    {
        context.deleteFile(GODFEST_CACHE_FILENAME);
        GodfestOverview.clearGodfestInfo();
        new getGodfestList().execute("This string isn't used.");
    }

    public static void renderGods()
    {
        Log.d("GodfestFragment", "Rendering Godfest...");
        TextView godfestMessage = (TextView) rootView.findViewById(R.id.godfestMessage);
        godfestMessage.setText(Html.fromHtml(GodfestOverview.getGodfestMessage()));
        ListView questionList = (ListView) rootView.findViewById(R.id.godsList);
        GodfestListAdapter GodfestListAdapter = new GodfestListAdapter(context, GodfestOverview.getFeaturedGods());
        questionList.setAdapter(GodfestListAdapter);
    }
}
