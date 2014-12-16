package com.randomappsinc.padnotifier.Misc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.Fragments.GodfestFragment;
import com.randomappsinc.padnotifier.Godfest.GodfestOverview;
import com.randomappsinc.padnotifier.Models.GodfestState;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Alex on 10/26/2014.
 */
public class Util
{
    // Default length of godfest in seconds (2 days)
    public static long GODFEST_DEFAULT_LENGTH = 172800;

    public static boolean haveInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork)
        {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
               activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                return true;
            }
        }
        return false;
    }

    public static CountDownTimer giveTimer (long timeInMillis, final TextView countdown, final TextView message)
    {
        // Create timer
        return new CountDownTimer(timeInMillis, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                long secondsUntilDone = millisUntilFinished / 1000;
                long daysLeft = secondsUntilDone / 60 / 60 / 24;
                long hoursLeft = secondsUntilDone / 60 / 60 % 24;
                long minutesLeft = secondsUntilDone / 60 % 60;
                long secondsLeft = secondsUntilDone % 60;
                String days = "days";
                String hours = "hours";
                String seconds = "seconds";
                String minutes = "minutes";
                if (daysLeft == 1) { days = "day"; }
                if (hoursLeft == 1) { hours = "hour"; }
                if (secondsLeft == 1) { seconds = "second"; }
                if (minutesLeft == 1) { minutes = "minute"; }
                String numbers =  " <b>" + daysLeft + "</b> " + days + " <b>" + hoursLeft + "</b> " + hours
                        + " <b>" + minutesLeft + "</b> " + minutes + " <b>" + secondsLeft + "</b> " + seconds;
                if (GodfestOverview.getGodfestState().equals(GodfestOverview.GODFEST_BEFORE))
                {
                    countdown.setText(Html.fromHtml(DataFetcher.GODFEST_STARTS_IN + numbers));
                }
                if (GodfestOverview.getGodfestState().equals(GodfestOverview.GODFEST_STARTED))
                {
                    countdown.setText(Html.fromHtml(DataFetcher.GODFEST_ENDS_IN + numbers));
                }
            }

            public void onFinish()
            {
                if (GodfestOverview.getGodfestState().equals(GodfestOverview.GODFEST_BEFORE))
                {
                    // Change the godfest message and create another timer
                    GodfestOverview.setGodfestState(GodfestOverview.GODFEST_STARTED);
                    message.setText(Html.fromHtml(GodfestOverview.getGodfestMessage()));
                    GodfestFragment.killCountDownTimer();
                    GodfestFragment.setCountdownTimer(giveTimer(Util.GODFEST_DEFAULT_LENGTH * 1000, countdown, message));
                    GodfestFragment.startTimer();
                }
                if (GodfestOverview.getGodfestState().equals(GodfestOverview.GODFEST_STARTED))
                {
                    // Change the message, kill the timer
                    GodfestOverview.setGodfestState(GodfestOverview.GODFEST_OVER);
                    message.setText(Html.fromHtml(GodfestOverview.getGodfestMessage()));
                    ((LinearLayout) countdown.getParent()).removeView(countdown);
                }
            }
        };
    }

    public static GodfestState giveGodfestState(String state, long timeLeft, long cacheUnixTime)
    {
        GodfestState godfestState = new GodfestState();
        long currentUnixTime = (System.currentTimeMillis() / 1000L);
        if (state.equals(GodfestOverview.GODFEST_BEFORE))
        {
            // Not enough time has elapsed for godfest start
            if (cacheUnixTime + timeLeft > currentUnixTime)
            {
                godfestState.setState(GodfestOverview.GODFEST_BEFORE);
                godfestState.setTimeLeft(timeLeft - (currentUnixTime - cacheUnixTime));
            }
            else
            {
                godfestState.setState(GodfestOverview.GODFEST_STARTED);
                godfestState.setTimeLeft(GODFEST_DEFAULT_LENGTH - ((currentUnixTime - cacheUnixTime) - timeLeft));
            }
        }
        if (state.equals(GodfestOverview.GODFEST_STARTED))
        {
            // Not enough time has elapsed for godfest to end
            if (cacheUnixTime + timeLeft > currentUnixTime)
            {
                godfestState.setState(GodfestOverview.GODFEST_STARTED);
                godfestState.setTimeLeft(timeLeft - (currentUnixTime - cacheUnixTime));
            }
            else
            {
                godfestState.setState(GodfestOverview.GODFEST_OVER);
                godfestState.setTimeLeft(0);
            }
        }
        return godfestState;
    }

    public static String cleanGodName(String godName)
    {
        String[] pieces = godName.split(" ");
        StringBuilder godNameCleaned = new StringBuilder();
        for (int i = 1; i < pieces.length; i++)
        {
            godNameCleaned.append(pieces[i]);
            if (i != pieces.length - 1)
            {
                godNameCleaned.append("_");
            }
        }
        return godNameCleaned.toString();
    }

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
        // Sometimes PDX likes making time be "--".
        if (time.equals("--"))
            return null;

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
        if (time.contains("pm") && hour != 12) {
            hour += 12;
            hour %= 24;
        }

        calendar = Calendar.getInstance();
        TimeZone laTimeZone = TimeZone.getTimeZone("America/Los_Angeles");
        calendar.setTimeZone(laTimeZone);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    // Takes in a ISO_8601 time formatted string and turns it into a Calendar object.
    // Note that ISO_8601 times end with the character 'Z', like "2014-11-20T13:05:55Z".
    // This Z is ignored by the parser, but it is required for parsing.
    public static Calendar utcToCalendar(String utcTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Cut off the Z at the end of the input UTC time
        Date date = sdf.parse(utcTime.substring(0, utcTime.length() - 1));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // Given a calendar, convert to a string in the form of "HH:mm [am|pm]"
    public static String calendarToLocalTime(Calendar calendar) {
        if (calendar == null)
            return "--";

        String localTimeString;

        // Change timezone to where the machine is running
        TimeZone laTime = calendar.getTimeZone(); // Save away calendar's old time zone. This
                                                  // SHOULD be Los Angeles time.
        calendar.setTimeZone(TimeZone.getDefault());

        int localHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int localMinute = calendar.get(Calendar.MINUTE);
        localTimeString = (calendar.get(Calendar.HOUR) == 0 ? 12 : calendar.get(Calendar.HOUR))
                + (localMinute < 10 ? ":0" + localMinute : ":" + localMinute)
                + (localHourOfDay < 12 ? " am" : " pm");

        // Set the timezone back to Pacific time
        calendar.setTimeZone(laTime);
        return localTimeString;
    }

    public static void writeToInternalStorage(String filePath, Context context, String fileContent) {
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

    // Print out the time of a Calendar in human-readable format. Includes date, time, and timezone.
    public static String calendarToExactTime(Calendar calendar) {
        if (calendar == null)
            return "--";

        return "" + (calendar.get(calendar.MONTH) + 1 /* WHY IS MONTH ZERO-INDEXED */) + "-" +
                calendar.get(calendar.DAY_OF_MONTH) +
                (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? " " : " 0") +
                calendar.get(Calendar.HOUR_OF_DAY) +
                (calendar.get(Calendar.MINUTE) > 9 ? ":" : ":0") +
                calendar.get(Calendar.MINUTE) +
                (calendar.get(Calendar.SECOND) > 9 ? ":" : ":0") +
                calendar.get(Calendar.SECOND) + "." +
                String.format("%03d", calendar.get(Calendar.MILLISECOND)) + " " +
                calendar.getTimeZone().getDisplayName();
    }

    public static boolean cacheIsUpdated(Context context, String fileName) {
        File cacheFile = new File(context.getFilesDir(), fileName);
        Calendar refreshTime = Calendar.getInstance();

        refreshTime.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        refreshTime.set(Calendar.DAY_OF_MONTH, refreshTime.get(Calendar.DATE));
        refreshTime.set(Calendar.HOUR_OF_DAY, 0); /* TODO: Change this for Japan time later.*/
        refreshTime.set(Calendar.MINUTE, 0);
        refreshTime.set(Calendar.SECOND, 0);
        refreshTime.set(Calendar.MILLISECOND, 0);

        return (Arrays.asList(context.fileList()).contains(fileName) &&
                cacheFile.lastModified() > refreshTime.getTimeInMillis());
    }
}

