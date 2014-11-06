package com.randomappsinc.padnotifier;

import java.util.Calendar;

/**
 * Created by Derek on 11/5/2014.
 *
 * Represents a dungeon, because trying to parse everything through
 * strings is difficult.
 */
public class Timeslot implements Comparable<Timeslot>{
    String name;    // Dungeon name
    Calendar calendar;

    // hour input is in 24-hour format; 9 pm should be input as 21
    public Timeslot(String name, int hour, int minute) {
        this.name = name;
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHour(int hour) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public void setMinute(int minute) {
        calendar.set(Calendar.MINUTE, minute);
    }

    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    @Override
    public int compareTo(Timeslot anotherTimeslot) {
        // Assume both Timeslots are on the same day.
        return calendar.compareTo(anotherTimeslot.calendar);
    }
}
