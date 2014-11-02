package com.randomappsinc.padnotifier;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodfestOverview
{
    private static ArrayList<String> imageURLs = new ArrayList<String>();
    private static ArrayList<String> godfestGroups = new ArrayList<String>();
    private static ArrayList<String> godNames = new ArrayList<String>();

    private static final String NO_GODFEST_MESSAGE = "Currently, there is no godfest. Check back next time!";

    public static ArrayList<String> getImageURLs ()
    {
        return imageURLs;
    }
    public static ArrayList<String> getGodNames ()
    {
        return godNames;
    }

    public static void addGodfestGroup (String godfestGroup)
    {
        godfestGroups.add(godfestGroup);
    }

    public static void addImageURL (String imageURL)
    {
        imageURLs.add(imageURL);
    }

    public static void addGodName (String godName)
    {
        godNames.add(godName);
    }

    public static String getGodfestMessage ()
    {
        if (imageURLs.isEmpty())
        {
            return NO_GODFEST_MESSAGE;
        }
        String message = "The Godfest is LIVE, and it features the ";
        for (int i = 0; i < godfestGroups.size(); i++)
        {
            if (i == godfestGroups.size() - 1)
            {
                message += "and <b>" +  godfestGroups.get(i) + "</b>";
            }
            else
            {
                message += "<b>" + godfestGroups.get(i) + "</b>, ";
            }
        }
        message += " series!";
        return message;
    }

    public static void printInfo()
    {
        for (int i = 0; i < imageURLs.size(); i++)
        {
            Log.d("FOR NARNIA", imageURLs.get(i));
        }
        for (int i = 0; i < godfestGroups.size(); i++)
        {
            Log.d("FOR NARNIA", godfestGroups.get(i));
        }
    }
}
