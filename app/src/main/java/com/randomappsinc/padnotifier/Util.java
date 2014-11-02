package com.randomappsinc.padnotifier;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
