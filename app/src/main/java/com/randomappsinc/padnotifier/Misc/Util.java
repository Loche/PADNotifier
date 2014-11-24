package com.randomappsinc.padnotifier.Misc;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Alex on 10/26/2014.
 */
public class Util
{
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

    public static String readFile (String fileName)
    {
        String fileContents = "";
        try
        {
            String sCurrentLine;
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getPath()
                        + "/PADNotifier/" + fileName));
                while ((sCurrentLine = br.readLine()) != null)
                {
                    fileContents += sCurrentLine;
                }
            }
            catch (FileNotFoundException e) {}
        }
        catch (IOException e) {}
        return fileContents;
    }

    public static void createFile (String fileName, String fileContents)
    {
        Log.d("FOR NARNIA", "WRITING ALL UP IN DIS ISH");
        try
        {
            FileWriter fWriter = new FileWriter(Environment.getExternalStorageDirectory()
                    .getPath() + "/PADNotifier/" + fileName);
            fWriter.write(fileContents);
            fWriter.close();
        }
        catch (Exception e)
        {
        }
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
        // Change timezone to where the machine is running
        calendar.setTimeZone(TimeZone.getDefault());

        int localHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int localMinute = calendar.get(Calendar.MINUTE);
        return calendar.get(Calendar.HOUR)
                + (localMinute < 10 ? ":0" + localMinute : ":" + localMinute)
                + (localHourOfDay < 12 ? " am" : " pm");
    }

    public static String calendarToExactTime(Calendar calendar) {
        return "" + (calendar.get(calendar.MONTH) + 1 /* WHY IS MONTH ZERO-INDEXED */) + "-" +
                calendar.get(calendar.DAY_OF_MONTH) + " " +
                calendar.get(Calendar.HOUR_OF_DAY) +
                (calendar.get(Calendar.MINUTE) > 9 ? ":" : ":0") +
                calendar.get(Calendar.MINUTE) +
                (calendar.get(Calendar.SECOND) > 9 ? ":" : ":0") +
                calendar.get(Calendar.SECOND) + "." +
                calendar.get(Calendar.MILLISECOND);
    }
}
