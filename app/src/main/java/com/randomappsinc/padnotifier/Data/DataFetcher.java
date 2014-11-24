package com.randomappsinc.padnotifier.Data;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.randomappsinc.padnotifier.Fragments.GodfestFragment;
import com.randomappsinc.padnotifier.Fragments.MetalsFragment;
import com.randomappsinc.padnotifier.Godfest.GodfestOverview;
import com.randomappsinc.padnotifier.Metals.DungeonMapper;
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
import java.util.ArrayList;
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
        // 1. SET UP METALS
        Document MetalsDoc = Jsoup.parse(content.split(METALS_SPLITTER)[1]);
        // Util.createFile("MetalsSplit.html", content.split(metalsSplitter)[1]);

        DungeonMapper dungeonMapper = DungeonMapper.getDungeonMapper();

        // PARSE OUT IMAGE URLS
        Elements tables = MetalsDoc.select("table");
        ArrayList<String[]> allDungeonTimes = new ArrayList<String[]>();
        ArrayList<String[]> allImages = new ArrayList<String[]>();
        // Go through all tables. Parse out data from first legit one
        for (Element table : tables)
        {
            String style = table.attr("style");
            if (!style.contains("none"))
            {
                Elements rows = table.select("tr");
                // PARSING OUT TIMES
                for (int j = 2; j < rows.size(); j += 2)
                {
                    Element row = rows.get(j);
                    String[] dungeonTimes = new String[5];
                    Elements times = row.getElementsByClass("metaltime");
                    for (int i=0; i < times.size(); i++)
                    {
                        dungeonTimes[i] = times.get(i).text();
                    }
                    allDungeonTimes.add(dungeonTimes);

                    // MetalSchedule.addURLs(images);
                }
                // PARSING OUT IMAGE URLS
                for (int j = 1; j < rows.size(); j += 2)
                {
                    Element row = rows.get(j);
                    Elements cols = row.select("td");
                    int numImages = (cols.size() - NUM_SEPARATORS);
                    String[] images = new String[numImages];
                    int numImagesFound = 0;
                    for (int k = 0; k < cols.size(); k++)
                    {

                        Elements img = cols.get(k).select("img");
                        if (img.size()>0) {
                            String imageURL = img.get(0).attr(IMAGE_URL_ATTR_NAME);
                            images[numImagesFound] = PDX_HOME + imageURL;
                            numImagesFound++;
                        }
                    }
                    allImages.add(images);
                }

                // How many "waves" of dungeons there are today. Up to 6, as of now
                for (int level = 0; level < allDungeonTimes.size(); level++)
                {
                    // Number of dungeons per timeslot. Could be 2 due to simultaneous hunt/supers
                    int numImagesInLevel = (allImages.get(level).length)/5;
                    // For each of the 5 times
                    for (int group = 0; group < 5; group++)
                    {
                        // For each of the images in a slot (could be hunt/supers as aforementioned)
                        for (int image = 0; image < numImagesInLevel; image++)
                        {
                            Timeslot dungeon = new Timeslot(allImages.get(level)[group+ image],
                                Util.timeToCalendar(allDungeonTimes.get(level)[group]),
                                2 /*USA*/,
                                dungeonMapper.getDungeonInfo(allImages.get(level)[group+ image]).getDungeonTitle(),
                                Util.intToGroup(group));
                            MetalSchedule.addTimeslot(dungeon);
                        }
                    }
                }
                break;
            }

        }



       // MetalSchedule.printImageURLs();


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
