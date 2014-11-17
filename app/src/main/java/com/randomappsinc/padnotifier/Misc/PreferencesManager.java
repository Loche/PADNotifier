package com.randomappsinc.padnotifier.Misc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alex on 11/16/2014.
 */
public class PreferencesManager
{
    Context context;
    SharedPreferences prefs;

    private static final String GROUP_KEY = "com.randomappsinc.padnotifier.group";
    private static final String STARTER_COLOR_KEY = "com.randomappsinc.padnotifier.starterColor";
    private static final String PREFS_KEY = "com.randomappsinc.padnotifier";

    public PreferencesManager(Context context)
    {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    public Character getGroup()
    {
        String group = prefs.getString(GROUP_KEY, "");
        if (!group.isEmpty())
        {
            return group.charAt(0);
        }
        return null;
    }

    public void setGroup(String group)
    {
        prefs.edit().putString(GROUP_KEY, group).apply();
    }

    public Character getStarterColor()
    {
        String starterColor = prefs.getString(STARTER_COLOR_KEY, "");
        if (!starterColor.isEmpty())
        {
            return starterColor.charAt(0);
        }
        return null;
    }

    public void setStarterColor(String starterColor)
    {
        prefs.edit().putString(STARTER_COLOR_KEY, starterColor).apply();
    }
}
