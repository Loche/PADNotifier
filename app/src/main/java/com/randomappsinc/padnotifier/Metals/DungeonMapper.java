package com.randomappsinc.padnotifier.Metals;

import com.randomappsinc.padnotifier.R;

import java.util.HashMap;

/**
 * Created by Alex on 11/1/2014.
 */
public class DungeonMapper
{
    private static DungeonMapper instance = null;
    private static HashMap<String, String> imageURLtoDungeonName = new HashMap<String, String>();
    private static HashMap<String, Integer> imageURLtoDrawableID = new HashMap<String, Integer>();

    private static HashMap<String, String> dungeonNameToImageURL = new HashMap<String, String>();

    private DungeonMapper()
    {
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/254.png", "Dungeon of Ruby Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/257.png", "Dungeon of Sapphire Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/260.png", "Dungeon of Emerald Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/181.png", "Dungeon of Gold Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/178.png", "Alert! Metal Dragons!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/617.png", "Super Ruby Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/618.png", "Super Sapphire Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/619.png", "Super Emerald Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/309.png", "Super Gold Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", "Super Metal Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/1002.png", "Metal/Gold Dungeon");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", "King Carnival");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/153.png", "Alert! Dragon Plant Infestation!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/603.png", "Pengdra Village");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/321.png", "Together at Last! Evo Rush!!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/265.png", "Ruins of the Star Vault");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/1189.png", "Hera-Beorc Descended!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/650.png", "Zeus-Dios Descended!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/599.png", "Hera-Ur Descended!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/1252.png", "Zeus Vulcan Descended!");

        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/254.png", R.drawable.hunt_ruby_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/257.png", R.drawable.hunt_sapphire_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/260.png", R.drawable.hunt_emerald_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/181.png", R.drawable.hunt_gold_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/178.png", R.drawable.hunt_metal_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/617.png", R.drawable.revenge_of_rubies);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/618.png", R.drawable.revenge_of_sapphires);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/619.png", R.drawable.revenge_of_emeralds);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/309.png", R.drawable.revenge_of_golds);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", R.drawable.super_metal);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/1002.png", R.drawable.metal_gold_outbreak);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", R.drawable.super_metal);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/153.png", R.drawable.dragon_plant_infestation);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/603.png", R.drawable.pengdra_village);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/321.png", R.drawable.evo_rush);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/265.png", R.drawable.starry_view_lane);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/1189.png", R.drawable.hera_beorc_descended);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/650.png", R.drawable.zeus_dios_descended);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/599.png", R.drawable.hera_ur_descended);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/1252.png", R.drawable.zeus_vulcan_descended);

        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/254.png", "Dungeon of Ruby Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/257.png", "Dungeon of Sapphire Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/260.png", "Dungeon of Emerald Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/181.png", "Dungeon of Gold Dragons");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/178.png", "Alert! Metal Dragons!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/617.png", "Super Ruby Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/618.png", "Super Sapphire Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/619.png", "Super Emerald Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/309.png", "Super Gold Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", "Super Metal Dragons Descended");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/1002.png", "Metal/Gold Dungeon");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", "King Carnival");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/153.png", "Alert! Dragon Plant Infestation!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/603.png", "Pengdra Village");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/321.png", "Together at Last! Evo Rush!!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/265.png", "Ruins of the Star Vault");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/1189.png", "Hera-Beorc Descended!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/650.png", "Zeus-Dios Descended!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/599.png", "Hera-Ur Descended!");
        imageURLtoDungeonName.put("http://www.puzzledragonx.com/en/img/thumbnail/1252.png", "Zeus Vulcan Descended!");

        dungeonNameToImageURL.put("Dungeon of Ruby Dragons", "http://www.puzzledragonx.com/en/img/thumbnail/254.png");
        dungeonNameToImageURL.put("Dungeon of Sapphire Dragons", "http://www.puzzledragonx.com/en/img/thumbnail/257.png");
        dungeonNameToImageURL.put("Dungeon of Emerald Dragons", "http://www.puzzledragonx.com/en/img/thumbnail/260.png");
        dungeonNameToImageURL.put("Dungeon of Gold Dragons", "http://www.puzzledragonx.com/en/img/thumbnail/181.png");
        dungeonNameToImageURL.put("Alert! Metal Dragons!", "http://www.puzzledragonx.com/en/img/thumbnail/178.png");
        dungeonNameToImageURL.put("Super Ruby Dragons Descended", "http://www.puzzledragonx.com/en/img/thumbnail/617.png");
        dungeonNameToImageURL.put("Super Sapphire Dragons Descended", "http://www.puzzledragonx.com/en/img/thumbnail/618.png");
        dungeonNameToImageURL.put("Super Emerald Dragons Descended", "http://www.puzzledragonx.com/en/img/thumbnail/619.png");
        dungeonNameToImageURL.put("Super Gold Dragons Descended", "http://www.puzzledragonx.com/en/img/thumbnail/309.png");
        dungeonNameToImageURL.put("Super Metal Dragons Descended", "http://www.puzzledragonx.com/en/img/thumbnail/261.png");
        dungeonNameToImageURL.put("Metal/Gold Dungeon", "http://www.puzzledragonx.com/en/img/thumbnail/1002.png");
        dungeonNameToImageURL.put("King Carnival", "http://www.puzzledragonx.com/en/img/thumbnail/261.png");
        dungeonNameToImageURL.put("Alert! Dragon Plant Infestation!", "http://www.puzzledragonx.com/en/img/thumbnail/153.png");
        dungeonNameToImageURL.put("Pengdra Village", "http://www.puzzledragonx.com/en/img/thumbnail/603.png");
        dungeonNameToImageURL.put("Together at Last! Evo Rush!!", "http://www.puzzledragonx.com/en/img/thumbnail/321.png");
        dungeonNameToImageURL.put("Ruins of the Star Vault", "http://www.puzzledragonx.com/en/img/thumbnail/265.png");
        dungeonNameToImageURL.put("Hera-Beorc Descended!", "http://www.puzzledragonx.com/en/img/thumbnail/1189.png");
        dungeonNameToImageURL.put("Zeus-Dios Descended!", "http://www.puzzledragonx.com/en/img/thumbnail/650.png");
        dungeonNameToImageURL.put("Hera-Ur Descended!", "http://www.puzzledragonx.com/en/img/thumbnail/599.png");
        dungeonNameToImageURL.put("Zeus Vulcan Descended!", "http://www.puzzledragonx.com/en/img/thumbnail/1252.png");
    }

    public static DungeonMapper getDungeonMapper()
    {
        if (instance == null)
        {
            instance = new DungeonMapper();
        }
        return instance;
    }

    public static String getDungeonName (String imageURL)
    {
        return imageURLtoDungeonName.get(imageURL);
    }

    public static String getImageURL (String dungeonName) {
        return dungeonNameToImageURL.get(dungeonName);
    }

    public int getDrawableResourceID (String dungeonName)
    {
        return imageURLtoDrawableID.get(dungeonNameToImageURL.get(dungeonName));
    }
}
