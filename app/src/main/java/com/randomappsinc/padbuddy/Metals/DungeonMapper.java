package com.randomappsinc.padbuddy.Metals;

import com.randomappsinc.padbuddy.Models.DungeonInfo;
import com.randomappsinc.padbuddy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Alex on 11/1/2014.
 */
public class DungeonMapper
{
    private static DungeonMapper instance = null;
    private static HashMap<String, DungeonInfo> imageURLtoDungeonInfo = new HashMap<String, DungeonInfo>();
    private static HashMap<String, Integer> nameToDrawableId = new HashMap<String, Integer>();
    private static ArrayList<String> dungeonNamesList = new ArrayList<String>();

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
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/618.png",
                new DungeonInfo("Super Sapphire Dragons Descended", R.drawable.revenge_of_sapphires));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/619.png",
                new DungeonInfo("Super Emerald Dragons Descended", R.drawable.revenge_of_emeralds));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/309.png",
                new DungeonInfo("Super Gold Dragons Descended", R.drawable.revenge_of_golds));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png",
                new DungeonInfo("Super Metal Dragons Descended", R.drawable.super_metal));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1002.png",
                new DungeonInfo("Metal/Gold Dungeon", R.drawable.metal_gold_outbreak));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/kc.png",
                new DungeonInfo("King Carnival", R.drawable.king_carnival));
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
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1061.png",
                new DungeonInfo("Dragon Zombie", R.drawable.dragon_zombie));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/744.png",
                new DungeonInfo("Draggie!", R.drawable.draggie));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1223.png",
                new DungeonInfo("Gaia Descended!", R.drawable.gaia));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1307.png",
                new DungeonInfo("Wadatsumi Descended!", R.drawable.wadatsumi));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1701.png",
                new DungeonInfo("TAMADRA Retreat", R.drawable.chibidra));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/565.png",
                new DungeonInfo("Golden Mound", R.drawable.goemon));
        imageURLtoDungeonInfo.put("http://www.puzzledragonx.com/en/img/thumbnail/1525.png",
                new DungeonInfo("Kanetsugu!", R.drawable.kanetsugu));

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
        nameToDrawableId.put("Dragon Zombie", R.drawable.dragon_zombie);
        nameToDrawableId.put("Draggie!", R.drawable.draggie);
        nameToDrawableId.put("Gaia Descended!", R.drawable.gaia);
        nameToDrawableId.put("Wadatsumi Descended!", R.drawable.wadatsumi);
        nameToDrawableId.put("TAMADRA Retreat", R.drawable.chibidra);
        nameToDrawableId.put("Golden Mound", R.drawable.goemon);
        nameToDrawableId.put("Kanetsugu!", R.drawable.kanetsugu);
    }

    private static void setUpDungeonNamesList()
    {
        for (String key : nameToDrawableId.keySet())
        {
            dungeonNamesList.add(key);
        }
        Collections.sort(dungeonNamesList);
    }

    public DungeonInfo getDungeonInfo(String imageURL)
    {
        // Log.d("DungeonMapper", imageURL);
        if (imageURLtoDungeonInfo.get(imageURL) == null)
        {
            // Log.d("DungeonMapper", imageURL + " not found.");
            return new DungeonInfo(null, null);
        }
        // Log.d("DungeonMapper", imageURLtoDungeonInfo.get(imageURL).getDungeonTitle());
        return imageURLtoDungeonInfo.get(imageURL);
    }

    public ArrayList<String> getDungeonNamesList()
    {
        return (ArrayList<String>) dungeonNamesList.clone();
    }

    public int getDrawableIdFromName(String dungeonName)
    {
        return nameToDrawableId.get(dungeonName);
    }
}
