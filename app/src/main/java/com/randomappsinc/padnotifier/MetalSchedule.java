package com.randomappsinc.padnotifier;

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
    private static ArrayList<String[]> imageURLs = new ArrayList<String[]>();

    public static void addURLs (String[] URLs)
    {
        imageURLs.add(URLs);
    }

    public static HashMap<Character, ArrayList<String>> getGroupMappings ()
    {
        return groupMappings;
    }

    public static void printImageURLs()
    {
        for (int i = 0; i < imageURLs.size(); i++)
        {
            String message = "Level " + (i+1) + " image(s): ";
            for (int j = 0; j < imageURLs.get(i).length; j++)
            {
                message += imageURLs.get(i)[j];
                if (j != imageURLs.get(i).length - 1)
                {
                    message += ", ";
                }
            }
            System.out.println(message);
        }
    }

    public static void printMap()
    {
        Iterator it = groupMappings.entrySet().iterator();
        while (it.hasNext()) {
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
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
