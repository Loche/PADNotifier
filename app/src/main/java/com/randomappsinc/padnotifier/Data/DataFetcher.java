package com.randomappsinc.padnotifier.Data;

import android.content.Context;

import com.randomappsinc.padnotifier.Alarms.MetalsAlarmReceiver;
import com.randomappsinc.padnotifier.Fragments.GodfestFragment;
import com.randomappsinc.padnotifier.Fragments.MetalsFragment;
import com.randomappsinc.padnotifier.Godfest.GodfestOverview;
import com.randomappsinc.padnotifier.Metals.DungeonMapper;
import com.randomappsinc.padnotifier.Metals.MetalSchedule;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.Models.God;
import com.randomappsinc.padnotifier.Models.GodfestState;
import com.randomappsinc.padnotifier.Models.Timeslot;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Alex on 10/19/2014.
 */

// This class does the HTML fetching and parsing for the app
public class DataFetcher
{
    private static final String PDX_HOME = "http://www.puzzledragonx.com/";
    private Context context;

    // Strings for metals
    private static final String METALS_SPLITTER = "Metal Schedule";
    private static final String METAL_TIME_CLASS_NAME = "metaltime";
    private static final int NUM_SEPARATORS = 4;

    /* GODFEST */
    private static final String GODFEST_SPLITTER = "a id=\"godfes\"";
    private static final String GODFEST_LIST_CLASS_NAME = "godfeslist";
    private static final String GODFEST_ONGOING = "gftimeleft";
    private static final String IMAGE_URL_ATTR_NAME = "data-original";
    private static final String GODFEST_ICON_TITLE = "title";
    private static final String GODFEST_SPACER = "en/img/spacer.gif";
    private static final String IMAGE_URL_KEY = "IMG_URL";
    private static final String GOD_NAME_KEY = "NAME";
    private static final int GODFEST_NUM_CATEGORIES = 3;
    public static final String GODFEST_ENDS_IN = "Ends in:";
    public static final String GODFEST_STARTS_IN = "Starts in:";

    // File extraction
    private static final String GODFEST_CATEGORIES_KEY = "CATEGORIES";
    private static final String FEATURED_GODS_KEY = "GODS";
    private static final String GODFEST_STATE_KEY = "STATE";
    private static final String GODFEST_TIME_LEFT_KEY = "TIME_LEFT";

    // Tag for Log/debugging
    private static final String TAG = "DataFetcher";

    private MetalSchedule metalSchedule;

    public DataFetcher(Context context)
    {
        this.context = context;
        metalSchedule = MetalSchedule.getInstance();
    }

    // Given a string that is the HTML for PDX home, parse it for metals info
    public void extractPDXMetalsContent(String content)
    {
        // 1. SET UP METALS
        Document MetalsDoc = Jsoup.parse(content.split(METALS_SPLITTER)[1]);

        DungeonMapper dungeonMapper = DungeonMapper.getDungeonMapper();

        // PARSE OUT IMAGE URLS
        Elements tables = MetalsDoc.select("table");
        ArrayList<String[]> allDungeonTimes = new ArrayList<String[]>();
        ArrayList<ArrayList<String>> allImages = new ArrayList<ArrayList<String>>();
        JSONObject externalFile = new JSONObject();
        JSONArray timeSlotList = new JSONArray();
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
                    Elements times = row.getElementsByClass(METAL_TIME_CLASS_NAME);
                    for (int i = 0; i < times.size(); i++)
                    {
                        dungeonTimes[i] = times.get(i).text();
                    }
                    allDungeonTimes.add(dungeonTimes);
                }

                // PARSING OUT IMAGE URLS
                for (int j = 1; j < rows.size(); j += 2)
                {
                    Element row = rows.get(j);
                    Elements cols = row.select("td");
                    int numImages = (cols.size() - NUM_SEPARATORS);
                    ArrayList<String> images = new ArrayList<String>();
                    int numImagesFound = 0;

                    for (int k = 0; k < cols.size(); k++)
                    {
                        Elements img = cols.get(k).select("img");
                        if (img.size() > 0)
                        {
                            String imageURL = img.get(0).attr(IMAGE_URL_ATTR_NAME);
                            images.add(PDX_HOME + imageURL);
                            numImagesFound++;
                        }
                    }
                    allImages.add(images);
                }

                // How many "waves" (rows) of dungeons there are today. Up to 6, as of now
                for (int level = 0; level < allDungeonTimes.size(); level++)
                {
                    // Number of dungeons per timeslot. Could be 2 due to simultaneous hunt/supers
                    int numImagesInLevel = (allImages.get(level).size()) / 5;
                    // For each of the 5 times
                    for (int group = 0; group < 5; group++)
                    {
                        // For each of the images in a slot (could be hunt/supers as aforementioned)
                        for (int image = 0; image < numImagesInLevel; image++)
                        {
                            Timeslot dungeon = new Timeslot(allImages.get(level).get(group + image),
                                    Util.timeToCalendar(allDungeonTimes.get(level)[group]),
                                    2 /*USA*/,
                                    dungeonMapper.getDungeonInfo(allImages.get(level).get(group + image)).getDungeonTitle(),
                                    Util.intToGroup(group));
                            metalSchedule.addTimeslot(dungeon);
                            JSONObject timeSlotJSON = new JSONObject();
                            timeSlotJSON.put("IMG_URL", allImages.get(level).get(group + image));
                            timeSlotJSON.put("TIME", allDungeonTimes.get(level)[group]);
                            timeSlotJSON.put("COUNTRY", String.valueOf(2));
                            timeSlotJSON.put("DUNGEON_TITLE", dungeonMapper.getDungeonInfo(allImages.get(level).get(group + image)).getDungeonTitle());
                            timeSlotJSON.put("GROUP", String.valueOf(Util.intToGroup(group)));
                            timeSlotList.add(timeSlotJSON);
                        }
                    }
                }
                externalFile.put("TIME_SLOT_LIST", timeSlotList);
                Util.writeToInternalStorage(MetalsFragment.METALS_CACHE_FILENAME, context, externalFile.toJSONString());
                break;
            }
        }

        new MetalsAlarmReceiver().setAlarm(context);
    }

    public void extractPDXGodfestContent(String content)
    {
        JSONObject externalFile = new JSONObject();
        JSONArray categoryList = new JSONArray();
        JSONArray godsList = new JSONArray();

        // Check to see if Godfest is even on PDX's radar
        String[] godfestPieces = content.split(GODFEST_SPLITTER);
        if (godfestPieces.length >= 2)
        {
            Document fullDoc = Jsoup.parse(content);

            // 2.1 PARSE OUT CATEGORIES
            boolean isOver = false;
            Element godfest = fullDoc.getElementById("event");
            Elements infoRows = godfest.select("tr");

            int i;
            for (i = 1; i < infoRows.size(); i++)
            {
                Elements list = infoRows.get(i).getElementsByClass(GODFEST_LIST_CLASS_NAME);
                if (!list.isEmpty())
                {
                    Elements godCategories = list.get(0).getElementsByTag("a");
                    for (int j = 0; j < GODFEST_NUM_CATEGORIES && j < godCategories.size(); j++)
                    {
                        Element currElement = godCategories.get(j);

                        // Make sure the link goes to a monster book series.
                        // This is to prevent REM simulator from becoming a category
                        if (currElement.attr("href").contains("monsterbook"))
                        {
                            String currElementText = currElement.text();
                            String godfestGroup = currElementText.replaceAll(" God", "");
                            GodfestOverview.addGodfestGroup(godfestGroup);
                            categoryList.add(godfestGroup);
                        }
                    }
                }

                // 2.1.1 EXTRACT STATE AND TIME LEFT OF THE GODFEST
                Elements alive = infoRows.get(i).getElementsByClass(GODFEST_ONGOING);
                if (!alive.isEmpty())
                {
                    Elements spans = alive.select("span");
                    for (Element span: spans)
                    {
                        if (span.text().equals(GODFEST_STARTS_IN))
                        {
                            GodfestOverview.setGodfestState(GodfestOverview.GODFEST_BEFORE);
                            externalFile.put(GODFEST_STATE_KEY, GodfestOverview.GODFEST_BEFORE);
                            isOver = false;
                        }
                        if (span.text().equals(GODFEST_ENDS_IN))
                        {
                            GodfestOverview.setGodfestState(GodfestOverview.GODFEST_STARTED);
                            externalFile.put(GODFEST_STATE_KEY, GodfestOverview.GODFEST_STARTED);
                            isOver = false;
                        }
                        String spanClass = span.attr("class");
                        if (spanClass != null)
                        {
                            if (spanClass.contains("until"))
                            {
                                GodfestOverview.setGodfestTimeLeft(Integer.parseInt(spanClass.replace("until", "")));
                                externalFile.put(GODFEST_TIME_LEFT_KEY, spanClass.replace("until", ""));
                            }
                        }
                    }
                }
            }

            if (isOver)
            {
                GodfestOverview.setGodfestState(GodfestOverview.GODFEST_OVER);
                externalFile.put(GODFEST_STATE_KEY, GodfestOverview.GODFEST_OVER);
                externalFile.put(GODFEST_TIME_LEFT_KEY, 0);
            }

            // 2.2 PARSE OUT GODS INFO
            Document godIconsDocument = Jsoup.parse(content.split(GODFEST_LIST_CLASS_NAME)[1]);
            Element godIcons = godIconsDocument.getElementById("event");
            Elements icons = godIcons.getElementsByTag("img");
            for (i = 0; i < icons.size(); i++)
            {
                String iconURL = icons.get(i).attr(IMAGE_URL_ATTR_NAME);
                if (!iconURL.equals(GODFEST_SPACER))
                {
                    GodfestOverview.addGod(new God(PDX_HOME + iconURL, icons.get(i).attr(GODFEST_ICON_TITLE)));
                    JSONObject god = new JSONObject();
                    god.put(IMAGE_URL_KEY, PDX_HOME + iconURL);
                    god.put(GOD_NAME_KEY, icons.get(i).attr(GODFEST_ICON_TITLE));
                    godsList.add(god);
                }
            }
            externalFile.put(GODFEST_CATEGORIES_KEY, categoryList);
            externalFile.put(FEATURED_GODS_KEY, godsList);
        }
        else
        {
            GodfestOverview.setGodfestState(GodfestOverview.GODFEST_NONE);
            externalFile.put(GODFEST_STATE_KEY, GodfestOverview.GODFEST_NONE);
            externalFile.put(GODFEST_TIME_LEFT_KEY, 0);
        }
        Util.writeToInternalStorage(GodfestFragment.GODFEST_CACHE_FILENAME, context, externalFile.toJSONString());
    }

    public void extractMetalsFromStorage()
    {
        String JSONdata = Util.readFileFromInternalStorage(MetalsFragment.METALS_CACHE_FILENAME, context);
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(JSONdata);
            JSONArray TIME_SLOT_LIST = (JSONArray) jsonObject.get("TIME_SLOT_LIST");
            Iterator i = TIME_SLOT_LIST.iterator();
            while (i.hasNext())
            {
                JSONObject innerObject = (JSONObject) i.next();
                String TIME = (String) innerObject.get("TIME");
                String GROUP = (String) innerObject.get("GROUP");
                String IMG_URL = (String) innerObject.get("IMG_URL");
                String DUNGEON_TITLE = (String) innerObject.get("DUNGEON_TITLE");
                String COUNTRY = (String) innerObject.get("COUNTRY");
                Timeslot dungeon = new Timeslot(IMG_URL, Util.timeToCalendar(TIME), Integer.parseInt(COUNTRY), DUNGEON_TITLE, GROUP.toCharArray()[0]);
                metalSchedule.addTimeslot(dungeon);
            }
        }
        catch (ParseException E){}
    }

    public void extractGodfestInfoFromStorage()
    {
        String JSONdata = Util.readFileFromInternalStorage(GodfestFragment.GODFEST_CACHE_FILENAME, context);
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(JSONdata);
            // EXTRACT STATE
            String state = jsonObject.get(GODFEST_STATE_KEY).toString();
            // Bail early if there's nothing on the radar
            if (state.equals(GodfestOverview.GODFEST_NONE))
            {
                GodfestOverview.setGodfestState(state);
                return;
            }
            Long timeLeft = Long.valueOf(jsonObject.get(GODFEST_TIME_LEFT_KEY).toString());
            File godfest_info = new File(context.getFilesDir(), GodfestFragment.GODFEST_CACHE_FILENAME);
            Long cacheUnixTime = (godfest_info.lastModified() / 1000L);
            GodfestState godfestState = Util.giveGodfestState(state, timeLeft, cacheUnixTime);
            GodfestOverview.setGodfestTimeLeft(godfestState.getTimeLeft());
            GodfestOverview.setGodfestState(godfestState.getState());

            // EXTRACT GROUPS
            JSONArray categoriesList = (JSONArray) jsonObject.get(GODFEST_CATEGORIES_KEY);
            Iterator categoriesIterator = categoriesList.iterator();
            while (categoriesIterator.hasNext())
            {
                GodfestOverview.addGodfestGroup(categoriesIterator.next().toString());
            }

            JSONArray godsList = (JSONArray) jsonObject.get(FEATURED_GODS_KEY);
            Iterator godsIterator = godsList.iterator();

            // EXTRACT GODS
            while (godsIterator.hasNext())
            {
                JSONObject innerObject = (JSONObject) godsIterator.next();
                String imageUrl = (String) innerObject.get(IMAGE_URL_KEY);
                String godName = (String) innerObject.get(GOD_NAME_KEY);
                God featuredGod = new God(imageUrl, godName);
                GodfestOverview.addGod(featuredGod);
            }
        }
        catch (ParseException E){}
    }

    public boolean syncFetchData() {
        HttpGet httpget = new HttpGet("http://www.puzzledragonx.com/");
        HttpClient client = new DefaultHttpClient();

        try {
            HttpResponse response = client.execute(httpget);

            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                extractPDXMetalsContent(data);
                extractPDXGodfestContent(data);

                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
