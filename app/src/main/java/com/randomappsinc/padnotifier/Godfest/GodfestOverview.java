package com.randomappsinc.padnotifier.Godfest;

import com.randomappsinc.padnotifier.Models.God;

import java.util.ArrayList;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodfestOverview
{
    private static ArrayList<God> featuredGods = new ArrayList<God>();
    private static ArrayList<String> godfestGroups = new ArrayList<String>();

    private static final String NO_GODFEST_MESSAGE = "Currently, there is no godfest. Check back next time!";

    public static ArrayList<God> getFeaturedGods()
    {
        return featuredGods;
    }

    public static void addGodfestGroup(String godfestGroup)
    {
        godfestGroups.add(godfestGroup);
    }

    public static void addGod(God featuredGod)
    {
        featuredGods.add(featuredGod);
    }

    public static void clearGodfestInfo()
    {
        featuredGods.clear();
        godfestGroups.clear();
    }

    public static String getGodfestMessage ()
    {
        if (featuredGods.isEmpty())
        {
            return NO_GODFEST_MESSAGE;
        }
        String message = "The Godfest is LIVE, and it features the ";
        if (godfestGroups.isEmpty()) {
            message += "<b>Player's Choice</b> voted gods!";
        }
        else {
            for (int i = 0; i < godfestGroups.size(); i++) {
                if (i == godfestGroups.size() - 1) {
                    message += "and <b>" + godfestGroups.get(i) + "</b>";
                } else {
                    message += "<b>" + godfestGroups.get(i) + "</b>, ";
                }
            }
            message += " series!";
        }
        return message;
    }
}
