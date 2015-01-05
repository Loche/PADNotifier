package com.randomappsinc.padbuddy.Models;

import android.util.Log;

import com.randomappsinc.padbuddy.Misc.Util;

import java.util.Calendar;

/**
 * Created by Derek on 11/5/2014.
 *
 * Represents a dungeon, because trying to parse everything through
 * strings is difficult.
 */
public class Timeslot implements Comparable<Timeslot> {
    private String imageUrl;       // URL to the PDX image for this dungeon
    private Calendar starts_at;     // Start time
    private int country;            // Country code
    private String title;           // Dungeon title
    private Character group_name;   // Group dungeon is in

    private static final String TAG = "Timeslot";

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public Timeslot(String imageUrl, Calendar starts_at, int country, String title, Character group_name) {
        this.imageUrl = imageUrl;
        this.starts_at = starts_at;
        this.country = country;
        this.title = title;

        this.group_name = group_name;
    }

    public void setStarts_at(Calendar starts_at) {
        this.starts_at = starts_at;
    }

    public Calendar getStarts_at() {
        return starts_at;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Character getGroup_name() {
        return group_name;
    }

    public void setGroup_name(Character group_name) {
        this.group_name = group_name;
    }

    @Override
    public String toString() {
        String ret;
        if (country == 1)
            ret = "JP ";
        else
            ret = "USA ";

        return ret + group_name + ", " + title + ": " + Util.calendarToExactTime(starts_at) +
                "\n" + imageUrl;
    }

    public void debugInfo() {
        Log.d(TAG, toString());
    }

    @Override
    public int compareTo(Timeslot anotherTimeslot) {
        int compareVal = starts_at.compareTo(anotherTimeslot.starts_at);

        if (compareVal == 0) {
            if (title == null) {
                compareVal = 1;
            }
            else {
                compareVal = title.compareTo(anotherTimeslot.getTitle());
            }
        }

        return compareVal;
    }
}
