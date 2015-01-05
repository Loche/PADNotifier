package com.randomappsinc.padbuddy.Models;

/**
 * Created by Alex on 1/4/2015.
 */
public class MonsterAttributes
{
    private int maxLevel;
    private int maxSkill;
    private int numAwakenings;
    private int drawableID;

    public MonsterAttributes(int maxLevel, int maxSkill, int numAwakenings, int drawableID)
    {
        this.maxLevel = maxLevel;
        this.maxSkill = maxSkill;
        this.numAwakenings = numAwakenings;
        this.drawableID = drawableID;

    }

    public int getMaxLevel()
    {
        return maxLevel;
    }

    public int getMaxSkill()
    {
        return maxSkill;
    }

    public int getNumAwakenings()
    {
        return numAwakenings;
    }

    public int getDrawableID()
    {
        return drawableID;
    }
}
