package com.randomappsinc.padnotifier;

import android.os.Environment;

import java.io.FileWriter;

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

    public static void createFile (String fileName, String fileContents)
    {
        System.out.println("WRITING ALL UP IN DIS ISH");
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
