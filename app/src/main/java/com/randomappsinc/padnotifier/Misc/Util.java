package com.randomappsinc.padnotifier.Misc;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Alex on 10/26/2014.
 */
public class Util
{
    public static String readFileFromInternalStorage(String filePath, Context context)
    {
        try
        {
            FileInputStream fin = context.openFileInput(filePath);
            int c;
            StringBuilder temp = new StringBuilder();
            while( (c = fin.read()) != -1)
            {
                temp.append(Character.toString((char)c));
            }
            fin.close();
            return temp.toString();
        }
        catch (FileNotFoundException e)
        {
            Log.d("UTIL", "File not found for: " + filePath);
        }
        catch (IOException e2)
        {
            Log.d("UTIL", "Some BS IO stuff happened reading from internal storage.");
        }
        return "";
    }

    public static ArrayList<String> getSearchResults (ArrayList<String> candidates, String criteria)
    {
        ArrayList<String> searchResults = new ArrayList<String>();
        criteria = criteria.toLowerCase();
        ArrayList<String> candidatesLowerCase = (ArrayList<String>) candidates.clone();
        for (int i = 0; i < candidatesLowerCase.size(); i++)
        {
            candidatesLowerCase.set(i, candidatesLowerCase.get(i).toLowerCase());
        }

        for (int i = 0; i < candidatesLowerCase.size(); i++)
        {
            if (candidatesLowerCase.get(i).contains(criteria))
            {
                searchResults.add(candidates.get(i));
            }
        }
        return searchResults;
    }

    public static Character intToGroup(int index)
    {
        int mapping = index % 5;
        switch (mapping)
        {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            default:
                return 'F';
        }
    }

    public static String digitToGroup(int digit)
    {
        digit %= 5;
        return("" + (char) (digit + (int) 'A'));
    }

    public static String starterColorToChar(String starterColor)
    {
        if (starterColor.equals("Fire"))
        {
            return "1";
        }
        if (starterColor.equals("Water"))
        {
            return "2";
        }
        if (starterColor.equals("Grass"))
        {
            return "3";
        }
        return "1";
    }

    // Times are either HH am/pm OR HH:MM am/pm in PDT
    // This function converts the given time from PDT to the user's time zone
    public static String convertTime(String time)
    {
        // Create a calendar object and set its time based on the PDT time zone
        Calendar localTime = new GregorianCalendar(TimeZone.getTimeZone("America/Los Angeles"));

        if (time.split(":").length == 2)
        {
            localTime.set(Calendar.HOUR, Integer.valueOf(time.split(":")[0]));
            localTime.set(Calendar.MINUTE, Integer.valueOf((time.split(":")[1]).split(" ")[0]));
        }
        else
        {
            localTime.set(Calendar.HOUR, Integer.valueOf(time.split(" ")[0]));
            localTime.set(Calendar.MINUTE, 0);
        }
        localTime.set(Calendar.SECOND, 0);

        // Create an instance user's time
        Calendar userCal = new GregorianCalendar(TimeZone.getDefault());
        userCal.setTimeInMillis(localTime.getTimeInMillis());

        // Get the foreign time
        int hour = userCal.get(Calendar.HOUR);
        int minutes = userCal.get(Calendar.MINUTE);
        boolean am = userCal.get(Calendar.AM_PM) == Calendar.AM;

        String convertedTime = String.valueOf(hour);

        if (minutes != 0)
        {
            convertedTime += ":" + String.valueOf(minutes);
        }

        if (am)
        {
            convertedTime += " am";
        }
        else
        {
            convertedTime += " pm";
        }

        Log.d("FOR NARNIA", time + " -> " + convertedTime);
        return convertedTime;
    }

    // Times are formatted in the form #?#(:##)? [ap]m. Calendar is in PDT timezone.
    public static Calendar timeToCalendar (String time) {
        int hour;
        int minute;
        Calendar calendar;

        int colonIndex = time.indexOf(':');
        if (colonIndex != -1) {
            hour = Integer.parseInt(time.substring(0, colonIndex));
            minute = Integer.parseInt(time.substring(colonIndex+1, colonIndex+3));
        }
        else {
            hour = Integer.parseInt(time.substring(0,2).trim());
            minute = 0;
        }

        // PM check
        if (time.contains("pm")) {
            hour += 12;
            hour %= 24;
        }

        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Los Angeles"));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar;
    }

    // Goddammit. Takes in a ISO_8601 time formatted string.
    public static Calendar utcToCalendar(String utcTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Cut off the Z at the end of the input UTC time
        Date date = sdf.parse(utcTime.substring(0, utcTime.length() - 1));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String calendarToLocalTime(Calendar calendar) {
        String localTime = "";

        // Change timezone to where the machine is running
        calendar.setTimeZone(TimeZone.getDefault());

        int localHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int localMinute = calendar.get(Calendar.MINUTE);
        return calendar.get(Calendar.HOUR)
                + (localMinute < 10 ? ":0" + localMinute : ":" + localMinute)
                + (localHourOfDay < 12 ? " am" : " pm");
    }
    public static void writeToInternalStorage(String filePath,Context context, String fileContent){
        FileOutputStream fos = null;
        try {
            // Open a writer to the cache file.
            fos = context.openFileOutput(filePath, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Write the contents of the data pull to this file.
            fos.write(fileContent.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

