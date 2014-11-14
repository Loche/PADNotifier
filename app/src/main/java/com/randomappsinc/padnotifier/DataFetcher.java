package com.randomappsinc.padnotifier;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Alex on 10/19/2014.
 */

// This class does the HTML fetching and parsing for the app
public class DataFetcher {
    private static final String PDX_HOME = "http://www.puzzledragonx.com/";
    private static final String PADHERDER_API_EVENTS = "https://www.padherder.com/api/events/";

    // Strings for metals
    private static final String METALS_SPLITTER = "Metal Schedule";
    private static final String END_METALS_SPLITTER = "Schedule is subject to change";
    private static final String METAL_TIME_CLASS_NAME = "metaltime";
    private static final int MAX_CONCURRENT_DUNGEONS = 2;

    // Strings for godfest
    private static final String GODFEST_SPLITTER = "a id=\"godfes\"";
    private static final String GODFEST_LIST_CLASS_NAME = "godfeslist";
    private static final String GODFEST_ONGOING = "gftimeleft";
    private static final String IMAGE_URL_ATTR_NAME = "data-original";
    private static final String GODFEST_ICON_TITLE = "title";
    private static final String GODFEST_SPACER = "en/img/spacer.gif";
    private static final int GODFEST_NUM_CATEGORIES = 3;

    // Tag for Log/debugging
    private static final String TAG = "DataFetcher";


    // Given a string that is the HTML for PDX home, parse it for the relevant info
    // After that, populate the MetalSchedule and Godfest classes the app draws on to draw things
    public static void extractPadherderAPIJSON(String content) {
        Log.d(TAG, "extract home");
        // Parse out the JSON from the Padherder API.
        JSONArray events;
        try {
            events = new JSONArray(content);
        } catch (JSONException e) {
            // If it fails, bail. Nothing more we can do here but display an error or something.
            Log.e(TAG, "Failed to parse JSON.");
            Log.e(TAG, content);
            e.printStackTrace();
            return;
        }

        // Each object in the JSON array is a dungeon. Convert to them into Timeslots, and add them
        // to the schedule.
        JSONObject dungeon;
        String starts_at;
        int country;
        String title;
        Character group;

        for (int i = 0; i < events.length(); ++i) {
            try {
                dungeon = events.getJSONObject(i);
                starts_at = dungeon.getString("starts_at");
                country = dungeon.getInt("country");
                title = dungeon.getString("title");
                group = dungeon.getString("group_name").charAt(0);
            } catch (JSONException e) {
                // The API JSON was improperly formatted. Bail.
                e.printStackTrace();
                MetalSchedule.reset();
                return;
            }

            try {
                System.out.println("ONE");
                Calendar startCalendar = Util.utcToCalendar(starts_at);
                System.out.println("TWO");
                MetalSchedule masterSchedule = MetalSchedule.getInstance();
                masterSchedule.addTimeslot(new Timeslot(startCalendar, country, title, group));
                System.out.println("THREE");
            } catch (ParseException e) {
                // The API's dungeon timestamp was improperly formatted. Bail.
                e.printStackTrace();
                String failStr = "WTF: Failed to add Timeslot for " + title + ".";
                Log.wtf(TAG, failStr);
                failStr = "WTF: " + content;
                Log.wtf(TAG, failStr);
                return;
            }
        }

        // Set up metal page now that it's done
        MetalsFragment.renderMetals();

        Log.d(TAG, "Done rendering metals");
    }



    // Given a string that is the HTML for PDX home, parse it for the relevant info
    // After that, populate the MetalSchedule and Godfest classes the app draws on to draw things
    public static void extractPDXHomeContent(String content)
    {
        // 2. SET UP GODFEST
        // Check to see if Godfest is even on PDX's radar
        String[] godfestPieces = content.split(GODFEST_SPLITTER);
        if (godfestPieces.length >= 2)
        {
            Document fullDoc = Jsoup.parse(content);
            // Util.createFile("GodfestSplit.html", content.split(GODFEST_SPLITTER)[1]);

            // 2.1 PARSE OUT CATEGORIES
            boolean isOngoing = false;
            Element godfest = fullDoc.getElementById("event");
            Elements infoRows = godfest.select("tr");

            int i;
            for (i = 1; i < infoRows.size(); i++)
            {
                Elements list = infoRows.get(i).getElementsByClass(GODFEST_LIST_CLASS_NAME);
                if (!list.isEmpty())
                {
                    Elements godCategories = list.get(0).getElementsByTag("a");
                    for (int j = 0; j < GODFEST_NUM_CATEGORIES; j++)
                    {
                        GodfestOverview.addGodfestGroup(godCategories.get(j).text().replaceAll(" God", ""));
                    }
                }

                // Check to see that godfest is even currently happening
                Elements alive = infoRows.get(i).getElementsByClass(GODFEST_ONGOING);
                if (!alive.isEmpty())
                {
                    isOngoing = true;
                }
            }

            if (isOngoing)
            {
                // 2.2 PARSE OUT IMAGE URLS
                Document godIconsDocument = Jsoup.parse(content.split(GODFEST_LIST_CLASS_NAME)[1]);
                Element godIcons = godIconsDocument.getElementById("event");
                Elements icons = godIcons.getElementsByTag("img");
                for (i = 0; i < icons.size(); i++)
                {
                    String iconURL = icons.get(i).attr(IMAGE_URL_ATTR_NAME);
                    if (!iconURL.equals(GODFEST_SPACER))
                    {
                        GodfestOverview.addImageURL(PDX_HOME + iconURL);
                        GodfestOverview.addGodName(icons.get(i).attr(GODFEST_ICON_TITLE));
                    }
                }
            }
        }
        GodfestFragment.renderGods();
    }

    public static void curlPDXHome()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(PDX_HOME, new PDNHttpResponseHandler());
    }

    public static void pullEventInfo() {
        Log.d("DataFetcher", "pullEventInfo() is called");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(PADHERDER_API_EVENTS, new PadherderHttpResponseHandler());
    }
}
