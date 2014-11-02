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
    private static final String IMAGE_URL_ATTR_NAME = "data-original";
    private static final int NUM_SEPARATORS = 4;
    private static final int MAX_CONCURRENT_DUNGEONS = 2;

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
        Element table = MetalsDoc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        for (int j = 1; j < rows.size() - 1; j+=2)  // First row is the col names so skip it.
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
    }

    public static void curlPDXHome()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(PDX_HOME, new PDNHttpResponseHandler());
    }
}
