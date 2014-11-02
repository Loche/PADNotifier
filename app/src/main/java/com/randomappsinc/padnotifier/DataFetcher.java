package com.randomappsinc.padnotifier;

import com.loopj.android.http.AsyncHttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 10/19/2014.
 */

// This class does the HTML fetching and parsing for the app
public class DataFetcher {
    private static final String PDX_HOME = "http://www.puzzledragonx.com/";

    // Strings for metals
    private static final String METALS_SPLITTER = "Metal Schedule";
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

    // Given a string that is the HTML for PDX home, parse it for the relevant info
    // After that, populate the MetalSchedule and Godfest classes the app draws on to draw things
    public static void extractPDXHomeContent(String content)
    {
        // 1. SET UP METALS
        Document MetalsDoc = Jsoup.parse(content.split(METALS_SPLITTER)[1]);
        // Util.createFile("MetalsSplit.html", content.split(METALS_SPLITTER)[1]);

        // PARSE OUT TIMES
        Elements metalTimes = MetalsDoc.getElementsByClass(METAL_TIME_CLASS_NAME);
        HashMap<Character, ArrayList<String>> mappings = MetalSchedule.getGroupMappings();
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
        Element table = MetalsDoc.select("table").get(0);
        Elements rows = table.select("tr");

        // First row is the col names (A, B, C, D, E) so skip it.
        for (int j = 1; j < rows.size() - 1; j+=2)
        {
            Element row = rows.get(j);
            Elements cols = row.select("td");
            ArrayList<String> images = new ArrayList<String>();
            for (int k = 0; k < MAX_CONCURRENT_DUNGEONS; k++)
            {
                Elements img = cols.get(k).select("img");
                String imageURL = img.attr(IMAGE_URL_ATTR_NAME);
                if (!imageURL.isEmpty())
                {
                    images.add(PDX_HOME + imageURL);
                }
            }
            MetalSchedule.addURLs(images);
        }
        MetalSchedule.printImageURLs();

        // Set up metal page now that it's done
        MetalsFragment.renderMetals();

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
}
