package com.randomappsinc.padnotifier.Models;

/**
 * Created by Alex on 11/23/2014.
 */
public class God
{
    private String imageUrl;
    private String godName;

    public God(String imageUrl, String godName)
    {
        this.imageUrl = imageUrl;
        this.godName = godName;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getGodName()
    {
        return godName;
    }

    public void setGodName(String godName)
    {
        this.godName = godName;
    }
}
