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
    private static String godfestState;
    private static long godfestSecondsLeft;

    public static String GODFEST_BEFORE = "BEFORE";
    public static String GODFEST_STARTED = "STARTED";
    public static String GODFEST_OVER = "OVER";
    public static String GODFEST_NONE = "NONE";
    private static final String NO_GODFEST_MESSAGE = "Currently, there isn't even a godfest on the radar. Check back next time!";
    private static final String GODFEST_FETCH_FAIL = "It looks like we are unable to fetch the godfest information. "
            + "If you have no internet access at the moment, try again when you get it.";

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

    public static void setGodfestState(String state)
    {
        godfestState = state;
    }

    public static long getGodfestSecondsLeft()
    {
        return godfestSecondsLeft;
    }

    public static String getGodfestState()
    {
        return godfestState;
    }

    public static void setGodfestTimeLeft(long secondsLeft)
    {
        godfestSecondsLeft = secondsLeft;
    }

    public static void clearGodfestInfo()
    {
        featuredGods.clear();
        godfestGroups.clear();
        godfestState = null;
    }

    public static String getGodfestMessage ()
    {
        if (godfestState == null)
        {
            return GODFEST_FETCH_FAIL;
        }
        if (godfestState.equals(GODFEST_NONE))
        {
            return NO_GODFEST_MESSAGE;
        }
        String message;
        if (godfestState.equals(GODFEST_BEFORE))
        {
            message = "The Godfest has yet to begin, and it will feature the ";
        }
        else if (godfestState.equals(GODFEST_STARTED))
        {
            message = "The Godfest is LIVE, and it features the ";
        }
        else
        {
            message = "The Godfest is over, and it featured the ";
        }
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
