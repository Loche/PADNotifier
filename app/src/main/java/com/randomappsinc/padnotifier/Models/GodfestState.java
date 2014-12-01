package com.randomappsinc.padnotifier.Models;

/**
 * Created by Alex on 11/30/2014.
 */
public class GodfestState
{
    private String state;
    private long timeLeft;

    public GodfestState() {}

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public long getTimeLeft()
    {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft)
    {
        this.timeLeft = timeLeft;
    }
}
