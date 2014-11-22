package com.randomappsinc.padnotifier.Metals;

import com.randomappsinc.padnotifier.Models.DungeonInfo;
import com.randomappsinc.padnotifier.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex on 11/1/2014.
 */
public class DungeonMapper
{
    private static DungeonMapper instance = null;
    private static HashMap<String, DungeonInfo> imageURLtoDungeonInfo = new HashMap<String, DungeonInfo>();
    private static HashMap<String, Integer> nameToDrawableId = new HashMap<String, Integer>();
    private static List<String> dungeonNamesList = new ArrayList<String>();

    private DungeonMapper()
    {
        setUpMapper();
        setUpDungeonNamesList();
    }

    public static DungeonMapper getDungeonMapper()
    {
        if (instance == null)
        {
            instance = new DungeonMapper();
        }
        return instance;
    }

    private static void setUpMapper()
    {
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/254.png",
                new DungeonInfo("Dungeon of Ruby Dragons", R.drawable.hunt_ruby_dragons));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/257.png",
                new DungeonInfo("Dungeon of Sapphire Dragons", R.drawable.hunt_sapphire_dragons));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/260.png",
                new DungeonInfo("Dungeon of Emerald Dragons", R.drawable.hunt_emerald_dragons));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/181.png",
                new DungeonInfo("Dungeon of Gold Dragons", R.drawable.hunt_gold_dragons));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/178.png",
                new DungeonInfo("Alert! Metal Dragons!", R.drawable.hunt_metal_dragons));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/617.png",
                new DungeonInfo("Super Ruby Dragons Descended", R.drawable.revenge_of_rubies));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/6 R.drawable.revenge_of_sapphires18.png",
                new DungeonInfo("Super Sapphire Dragons Descended", R.drawable.revenge_of_sapphires));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/619.png",
                new DungeonInfo("Super Emerald Dragons Descended", R.drawable.revenge_of_emeralds));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/309.png",
                new DungeonInfo("Super Gold Dragons Descended", R.drawable.revenge_of_golds));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png",
                new DungeonInfo("Super Metal Dragons Descended", R.drawable.super_metal));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1002.png",
                new DungeonInfo("Metal/Gold Dungeon", R.drawable.metal_gold_outbreak));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png",
                new DungeonInfo("King Carnival", R.drawable.super_metal));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/153.png",
                new DungeonInfo("Alert! Dragon Plant Infestation!", R.drawable.dragon_plant_infestation));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/603.png",
                new DungeonInfo("Pengdra Village", R.drawable.pengdra_village));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/321.png",
                new DungeonInfo("Together at Last! Evo Rush!!", R.drawable.evo_rush));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/265.png",
                new DungeonInfo("Ruins of the Star Vault", R.drawable.starry_view_lane));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1189.png",
                new DungeonInfo("Hera-Beorc Descended!", R.drawable.hera_beorc_descended));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/650.png",
                new DungeonInfo("Zeus-Dios Descended!", R.drawable.zeus_dios_descended));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/599.png",
                new DungeonInfo("Hera-Ur Descended!", R.drawable.hera_ur_descended));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1252.png",
                new DungeonInfo("Zeus Vulcan Descended!", R.drawable.zeus_vulcan_descended));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/597.png",
                new DungeonInfo("Hera-Is Descended!", R.drawable.hera_is_descended));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1532.png",
                new DungeonInfo("Zeus Mercury Descended!", R.drawable.zeus_mercury_descended));

        nameToDrawableId.put("Dungeon of Ruby Dragons", R.drawable.hunt_ruby_dragons);
        nameToDrawableId.put("Dungeon of Sapphire Dragons", R.drawable.hunt_sapphire_dragons);
        nameToDrawableId.put("Dungeon of Emerald Dragons", R.drawable.hunt_emerald_dragons);
        nameToDrawableId.put("Dungeon of Gold Dragons", R.drawable.hunt_gold_dragons);
        nameToDrawableId.put("Alert! Metal Dragons!", R.drawable.hunt_metal_dragons);
        nameToDrawableId.put("Super Ruby Dragons Descended", R.drawable.revenge_of_rubies);
        nameToDrawableId.put("Super Sapphire Dragons Descended", R.drawable.revenge_of_sapphires);
        nameToDrawableId.put("Super Emerald Dragons Descended", R.drawable.revenge_of_emeralds);
        nameToDrawableId.put("Super Gold Dragons Descended", R.drawable.revenge_of_golds);
        nameToDrawableId.put("Super Metal Dragons Descended", R.drawable.super_metal);
        nameToDrawableId.put("Metal/Gold Dungeon", R.drawable.metal_gold_outbreak);
        nameToDrawableId.put("King Carnival", R.drawable.super_metal);
        nameToDrawableId.put("Alert! Dragon Plant Infestation!", R.drawable.dragon_plant_infestation);
        nameToDrawableId.put("Pengdra Village", R.drawable.pengdra_village);
        nameToDrawableId.put("Together at Last! Evo Rush!!", R.drawable.evo_rush);
        nameToDrawableId.put("Ruins of the Star Vault", R.drawable.starry_view_lane);
        nameToDrawableId.put("Hera-Beorc Descended!", R.drawable.hera_beorc_descended);
        nameToDrawableId.put("Zeus-Dios Descended!", R.drawable.zeus_dios_descended);
        nameToDrawableId.put("Hera-Ur Descended!", R.drawable.hera_ur_descended);
        nameToDrawableId.put("Zeus Vulcan Descended!", R.drawable.zeus_vulcan_descended);
        nameToDrawableId.put("Hera-Is Descended!", R.drawable.hera_is_descended);
        nameToDrawableId.put("Zeus Mercury Descended!", R.drawable.zeus_mercury_descended);
    }

    private static void setUpDungeonNamesList()
    {
        for (String key : imageURLtoDungeonInfo.keySet())
        {
            dungeonNamesList.add(imageURLtoDungeonInfo.get(key).getDungeonTitle());
        }
        Collections.sort(dungeonNamesList);
    }

    public DungeonInfo getDungeonInfo(String imageURL)
    {
        return imageURLtoDungeonInfo.get(imageURL);
    }

    public List<String> getDungeonNamesList()
    {
        return dungeonNamesList;
    }

    public int getDrawableIdFromName (String dungeonName)
    {
        return nameToDrawableId.get(dungeonName);
    }
}
