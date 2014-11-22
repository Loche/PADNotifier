package com.randomappsinc.padnotifier.Data;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.randomappsinc.padnotifier.Fragments.GodfestFragment;
import com.randomappsinc.padnotifier.Fragments.MetalsFragment;
import com.randomappsinc.padnotifier.Godfest.GodfestOverview;
import com.randomappsinc.padnotifier.Metals.MetalSchedule;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.Models.Timeslot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.Calendar;

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
    private static final int NUM_SEPARATORS = 4;

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


    // Given a string that is the URL for the Padherder API, parse it for the relevant info
    // After that, populate the MetalSchedule so the app can draw metals stuff
    // TODO: Make this fail gracefully.
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

        // Each object in the JSON array is a dungeon.
        // Convert to them into Timeslots, and add them to the schedule.
        JSONObject dungeon;
        String starts_at;
        int country;
        String title;
        Character group;

        MetalSchedule masterSchedule = MetalSchedule.getInstance();
        masterSchedule.reset();

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
                masterSchedule.reset();
                return;
            }

            try {
                Calendar startCalendar = Util.utcToCalendar(starts_at);
                masterSchedule.addTimeslot(new Timeslot("http://www.puzzledragonx.com/en/img/thumbnail/265", startCalendar, country, title, group));
            } catch (ParseException e) {
                // The API's dungeon timestamp was improperly formatted. Bail.
                e.printStackTrace();
                String failStr = "WTF: Failed to add Timeslot for " + title + ".";
                Log.wtf(TAG, failStr);
                failStr = "WTF: " + content;
                Log.wtf(TAG, failStr);

                masterSchedule.reset();
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
        /*
        // 1. SET UP METALS
        Document MetalsDoc = Jsoup.parse(content.split(METALS_SPLITTER)[1]);
        // Util.createFile("MetalsSplit.html", content.split(metalsSplitter)[1]);

        // PARSE OUT TIMES
        Elements metalTimes = MetalsDoc.getElementsByClass(METAL_TIME_CLASS_NAME);
        int i = 0;
        for (Element metalTime : metalTimes)
        {
            if (mappings.get(Util.intToGroup(i)) == null)
            {
                mappings.put(Util.intToGroup(i), new ArrayList<String>());
            }
            mappings.get(Util.intToGroup(i)).add(metalTime.text());
            i++;
        }
        MetalSchedule.printMap();

        // PARSE OUT IMAGE URLS
        Element table = MetalsDoc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        for (int j = 1; j < rows.size(); j+=2) { //first row is the col names so skip it.
            Element row = rows.get(j);
            Elements cols = row.select("td");
            int numImages = (cols.size() - NUM_SEPARATORS)/5;
            String[] images = new String[numImages];
            for (int k = 0; k < numImages; k++)
            {
                Elements img = cols.get(k).select("img");
                String imageURL = img.attr(IMAGE_URL_ATTR_NAME);
                images[k] = PDX_HOME + imageURL;
            }
            MetalSchedule.addURLs(images);
        }
        MetalSchedule.printImageURLs(); */

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
