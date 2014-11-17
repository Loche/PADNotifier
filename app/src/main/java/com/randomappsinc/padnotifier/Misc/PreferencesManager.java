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

    private static final String PREFS_KEY = "com.randomappsinc.padnotifier";

    private static final String THIRD_DIGIT_KEY = "com.randomappsinc.padnotifier.ThirdDigit";
    private static final String GROUP_KEY = "com.randomappsinc.padnotifier.group";
    private static final String STARTER_COLOR_KEY = "com.randomappsinc.padnotifier.starterColor";
    private static final String MUTE_SETTING_KEY = "com.randomappsinc.padnotifier.muteSetting";

    public PreferencesManager(Context context)
    {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    public boolean getMuteSetting()
    {
        return prefs.getBoolean(MUTE_SETTING_KEY, false);
    }

    public void setMuteSetting(boolean muteSetting)
    {
        prefs.edit().putBoolean(MUTE_SETTING_KEY, muteSetting).apply();
    }

    public String getThirdDigit()
    {
        return prefs.getString(THIRD_DIGIT_KEY, "0");
    }

    public void setThirdDigit(String thirdDigit)
    {
        prefs.edit().putString(THIRD_DIGIT_KEY, thirdDigit).apply();
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
