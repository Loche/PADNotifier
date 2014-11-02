package com.randomappsinc.padnotifier;

import java.util.HashMap;

/**
 * Created by Alex on 11/1/2014.
 */
public class DungeonMapper
{
    private static DungeonMapper instance = null;
    private static HashMap<String, String> imageURLtoDungeonName = new HashMap<String, String>();
    private static HashMap<String, Integer> imageURLtoDrawableID = new HashMap<String, Integer>();

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

        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/254.png", R.drawable.hunt_ruby_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/257.png", R.drawable.hunt_sapphire_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/260.png", R.drawable.hunt_emerald_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/181.png", R.drawable.hunt_gold_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/178.png", R.drawable.hunt_metal_dragons);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/617.png", R.drawable.revenge_of_rubies);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/618.png", R.drawable.revenge_of_sapphires);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/619.png", R.drawable.revenge_of_emeralds);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/309.png", R.drawable.revenge_of_golds);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", R.drawable.revenge_of_metals);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/1002.png", R.drawable.metal_gold_outbreak);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/261.png", R.drawable.king_carnival);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/153.png", R.drawable.dragon_plant_infestation);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/603.png", R.drawable.pengdra_village);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/321.png", R.drawable.evo_rush);
        imageURLtoDrawableID.put("http://www.puzzledragonx.com/en/img/thumbnail/265.png", R.drawable.starry_view_lane);
    }

    public static DungeonMapper getDungeonMapper()
    {
        if (instance == null)
        {
            instance = new DungeonMapper();
        }
        return instance;
    }

    public String getDungeonName (String imageURL)
    {
        return imageURLtoDungeonName.get(imageURL);
    }

    public int getDrawableResourceID (String imageURL)
    {
        return imageURLtoDrawableID.get(imageURL);
    }
}
