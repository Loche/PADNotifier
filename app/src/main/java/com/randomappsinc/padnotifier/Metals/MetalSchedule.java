package com.randomappsinc.padnotifier.Metals;

import android.util.Log;

import com.randomappsinc.padnotifier.Models.Timeslot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Alex on 10/26/2014.
 *
 * Singleton master schedule for all metals dungeons across all countries. At the
 * moment, the only country supported is the USA.
 */
public class MetalSchedule
{
    // Map of <Country, to Map of <Group IDs, to list of Timeslots>>
    // Each country has its own IDs, which have their own lists of metal dungeons.
    private static TreeMap<Integer, HashMap<Character, ArrayList<Timeslot>>> schedule;
    private static final String TAG = "MetalSchedule";

    private MetalSchedule() {
        schedule = new TreeMap<Integer, HashMap<Character, ArrayList<Timeslot>>>();
    }

    /**
     * Initializes singleton. Code shamelessly copied from Wikipedia's entry on
     * Singleton pattern.
     *
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private static final MetalSchedule INSTANCE = new MetalSchedule();
    }

    public static MetalSchedule getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // Adds a timeslot to the schedule.
    public static void addTimeslot(Timeslot timeslot) {
        // If our master schedule doesn't have a map for a country yet, add it.
        if (!schedule.containsKey(timeslot.getCountry()))
            schedule.put(timeslot.getCountry(), new HashMap<Character, ArrayList<Timeslot>>());

        // Get the country's map of groups to dungeons.
        HashMap<Character, ArrayList<Timeslot>> countryDungeons = schedule.get(timeslot.getCountry());

        // If the country doesn't have a map for a group yet, add it. Note that sometimes, these
        // groups are based on starter element instead of third digit.
        // TODO: Make login page choose starter element.
        if (!countryDungeons.containsKey(timeslot.getGroup_name())) {
            countryDungeons.put(timeslot.getGroup_name(), new ArrayList<Timeslot>());
        }

        // Add the timeslot to group's list of dungeons.
        countryDungeons.get(timeslot.getGroup_name()).add(timeslot);
    }

    // Get a country's group's list of dungeons.
    public static ArrayList<Timeslot> getTimeslots(int country, Character group) {
        return schedule.get(country).get(group);
    }

    // Checks if the country times are updated.
    public static boolean timesIsEmpty(int country, Character group) {
        return !schedule.containsKey(country) ||                // no country
                !schedule.get(country).containsKey(group) ||    // no group
                schedule.get(country).get(group).isEmpty();     // group exists, but is empty
    }

    public static void reset() {
        schedule = new TreeMap<Integer, HashMap<Character, ArrayList<Timeslot>>>();
        // TODO: When alarms get implemented, remember to reset them here.
    }

    public static void printMap() {
        Log.d(TAG, "Printing Metals Schedule:");
        for (int country : schedule.keySet()) { // for each country in the schedule
            for (Character group : schedule.get(country).keySet()) { // for each group in the country
                for (Timeslot dungeon : schedule.get(country).get(group)) { // for each dungeon in the group
                    Log.d(TAG, dungeon.toString());
                }
            }
        }
    }
}
