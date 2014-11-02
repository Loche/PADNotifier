package com.randomappsinc.padnotifier;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
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
}
