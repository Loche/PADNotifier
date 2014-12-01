package com.randomappsinc.padnotifier.Godfest;

/**
 * Created by Alex on 11/30/2014.
 */

import com.randomappsinc.padnotifier.R;

import java.util.HashMap;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodMapper
{
    private static GodMapper instance = null;
    private static HashMap<String, Integer> nameToDrawableId = new HashMap<String, Integer>();

    private GodMapper()
    {
        setUpMapper();
    }

    public static GodMapper getGodMapper()
    {
        if (instance == null)
        {
            instance = new GodMapper();
        }
        return instance;
    }

    private static void setUpMapper()
    {
        // SPECIALS
        nameToDrawableId.put("Phantom God, Odin", R.drawable.rodin);
        nameToDrawableId.put("Loyal Deity, Guan Yu", R.drawable.red_guan);
        nameToDrawableId.put("Red Dragon Caller, Sonia", R.drawable.ronia);
        nameToDrawableId.put("Blue Dragon Caller, Sonia", R.drawable.blonia);
        nameToDrawableId.put("Odin, the War Deity", R.drawable.blodin);
        nameToDrawableId.put("Bearded Deity, Guan Yu", R.drawable.green_guan);
        nameToDrawableId.put("Sleeping Dragon, Zhuge Liang", R.drawable.green_zhuge);
        nameToDrawableId.put("Jade Dragon Caller, Sonia", R.drawable.gronia);
        nameToDrawableId.put("Odin", R.drawable.grodin);
        nameToDrawableId.put("Life Dragon, Zhuge Liang", R.drawable.light_zhuge);
        nameToDrawableId.put("Apocalypse", R.drawable.apocalypse);
        nameToDrawableId.put("Goddess of Secrets, Kali", R.drawable.lkali);
        nameToDrawableId.put("Archangel Metatron", R.drawable.lmeta);
        nameToDrawableId.put("Goddess of Power, Kali", R.drawable.dkali);
        nameToDrawableId.put("Dark Angel Metatron", R.drawable.dmeta);

        // GRECO-ROMAN
        nameToDrawableId.put("Minerva", R.drawable.minerva);
        nameToDrawableId.put("Venus", R.drawable.venus);
        nameToDrawableId.put("Hades", R.drawable.hades);
        nameToDrawableId.put("Ceres", R.drawable.ceres);
        nameToDrawableId.put("Neptune", R.drawable.neptune);

        // GRECO
        nameToDrawableId.put("Apollo", R.drawable.apollo);
        nameToDrawableId.put("Artemis", R.drawable.artemis);
        nameToDrawableId.put("Ares", R.drawable.ares);
        nameToDrawableId.put("Hermes", R.drawable.hermes);
        nameToDrawableId.put("Persephone", R.drawable.persephone);

        // CHINESE
        nameToDrawableId.put("Incarnation of Byakko, Haku", R.drawable.haku);
        nameToDrawableId.put("Incarnation of Genbu, Meimei", R.drawable.genbu);
        nameToDrawableId.put("Incarnation of Kirin, Sakuya", R.drawable.kirin);
        nameToDrawableId.put("Incarnation of Seiryuu, Karin", R.drawable.karin);
        nameToDrawableId.put("Incarnation of Suzaku, Leilan", R.drawable.leilan);
    }

    public Integer getGodDrawable(String godName)
    {
        return nameToDrawableId.get(godName);
    }
}

