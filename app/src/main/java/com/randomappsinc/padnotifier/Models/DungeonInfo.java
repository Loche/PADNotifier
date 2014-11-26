package com.randomappsinc.padnotifier.Models;

/**
 * Created by Alex on 11/22/2014.
 */
public class DungeonInfo
{
    private String dungeonTitle;
    private Integer drawableId;

    public DungeonInfo(String dungeonTitle, Integer drawableId)
    {
        this.dungeonTitle = dungeonTitle;
        this.drawableId = drawableId;
    }

    public String getDungeonTitle()
    {
        return dungeonTitle;
    }

    public void setDungeonTitle(String dungeonTitle)
    {
        this.dungeonTitle = dungeonTitle;
    }

    public int getDrawableId()
    {
        return drawableId;
    }

    public void setDrawableId(int drawableId)
    {
        this.drawableId = drawableId;
    }
}
