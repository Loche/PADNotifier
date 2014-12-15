package com.randomappsinc.padnotifier.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Activities.MainActivity;
import com.randomappsinc.padnotifier.Adapters.GodfestListAdapter;
import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Godfest.GodfestOverview;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GodfestFragment extends Fragment
{
    private static View rootView;
    public static Context context;
    private static DataFetcher dataFetcher;

    // Countdown timer
    private static CountDownTimer countDownTimer;
    public static void startTimer() { countDownTimer.start(); }
    public static void killCountDownTimer()
    {
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }
    public static void setCountdownTimer(CountDownTimer passedTimer) { countDownTimer = passedTimer; }

    // Views
    private static ProgressBar progress;
    private static TextView godfestMessage;
    private static ListView godfestList;
    private static TextView godfestCountdown;

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
        godfestCountdown = (TextView) rootView.findViewById(R.id.godfest_countdown);
        godfestList = (ListView) rootView.findViewById(R.id.godsList);
        MainActivity.setUpGodsListener();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progress.setVisibility(View.GONE);
        if (Util.cacheIsUpdated(getActivity(), GODFEST_CACHE_FILENAME))
        {
            GodfestOverview.clearGodfestInfo();
            dataFetcher.extractGodfestInfoFromStorage();
            renderGods();
        }
        else
        {
            refreshView();
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

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            godfestList.setVisibility(View.GONE);
            godfestMessage.setVisibility(View.GONE);
            godfestCountdown.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Long aLong)
        {
            super.onPostExecute(aLong);
            progress.setVisibility(View.GONE);
            godfestList.setVisibility(View.VISIBLE);
            godfestMessage.setVisibility(View.VISIBLE);
            godfestCountdown.setVisibility(View.VISIBLE);
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
        TextView godfestMessage = (TextView) rootView.findViewById(R.id.godfestMessage);
        godfestMessage.setText(Html.fromHtml(GodfestOverview.getGodfestMessage()));

        if (GodfestOverview.getGodfestState().equals(GodfestOverview.GODFEST_BEFORE) ||
            GodfestOverview.getGodfestState().equals(GodfestOverview.GODFEST_STARTED))
        {
            countDownTimer = Util.giveTimer(GodfestOverview.getGodfestSecondsLeft()*1000, godfestCountdown, godfestMessage);
            countDownTimer.start();
        }
        else
        {
            if (godfestCountdown != null && godfestCountdown.getParent() != null)
            {
                // Nuke the countdown
                ((LinearLayout) godfestCountdown.getParent()).removeView(godfestCountdown);
            }
        }

        ListView godsList = (ListView) rootView.findViewById(R.id.godsList);
        GodfestListAdapter GodfestListAdapter = new GodfestListAdapter(context, GodfestOverview.getFeaturedGods());
        godsList.setAdapter(GodfestListAdapter);
    }
}
