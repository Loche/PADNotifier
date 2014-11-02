package com.randomappsinc.padnotifier;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Alex on 10/26/2014.
 */
public class MetalSchedule
{
    private static HashMap<Character, ArrayList<String>> groupMappings =
            new HashMap<Character, ArrayList<String>>();
    private static ArrayList<ArrayList<String>> imageURLs = new ArrayList<ArrayList<String>>();

    public static void addURLs(ArrayList<String> URLs)
    {
        imageURLs.add(URLs);
    }

    public static HashMap<Character, ArrayList<String>> getGroupMappings()
    {
        return groupMappings;
    }

    // Return a "flattened" list of all the imageURLs (no levels)
    public static ArrayList<String> getImageURLs()
    {
        ArrayList flattenedList = new ArrayList<String>();
        for (int i = 0; i < imageURLs.size(); i++)
        {
            for (int j = 0; j < imageURLs.get(i).size(); j++)
            {
                flattenedList.add(imageURLs.get(i).get(j));
            }
        }
        return flattenedList;
    }

    // Return 1 to 1 time mapping to match the list returned in the getImageURLs function
    public static ArrayList<String> getTimes(Character group)
    {
        ArrayList<String> times = groupMappings.get(group);
        ArrayList<String> flattenedTimes = new ArrayList<String>();
        for (int i = 0; i < imageURLs.size(); i++)
        {
            for (int j = 0; j < imageURLs.get(i).size(); j++)
            {
                flattenedTimes.add(times.get(i));
            }
        }
        return flattenedTimes;
    }

    public static void printImageURLs()
    {
        for (int i = 0; i < imageURLs.size(); i++)
        {
            String message = "Level " + (i+1) + " image(s): ";
            for (int j = 0; j < imageURLs.get(i).size(); j++)
            {
                message += imageURLs.get(i).get(j);
                if (j != imageURLs.get(i).size() - 1)
                {
                    message += ", ";
                }
            }
            Log.d("FOR NARNIA", message);
        }
    }

    public static void printMap()
    {
        Iterator it = groupMappings.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pairs = (Map.Entry)it.next();
            String message = pairs.getKey() + ": ";
            ArrayList<String> times = (ArrayList<String>) pairs.getValue();
            for (int i = 0; i < times.size(); i++)
            {
                message += times.get(i);
                if (i != (times.size() - 1))
                {
                    message += ", ";
                }
            }
            System.out.println(message);
        }
    }
}
