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

        // JAPANESE 2.0
        nameToDrawableId.put("Umisachi&Yamasachi", R.drawable.u_and_y);
        nameToDrawableId.put("Izanagi", R.drawable.izanagi);
        nameToDrawableId.put("Kushinadahime", R.drawable.kush);
        nameToDrawableId.put("Okuninushi", R.drawable.oku);
        nameToDrawableId.put("Ame no Uzume", R.drawable.ame_no_uzume);

        // ARCHDEMON
        nameToDrawableId.put("Fallen Angel Lucifer", R.drawable.fa_luci);
        nameToDrawableId.put("Amon", R.drawable.amon);
        nameToDrawableId.put("Astaroth", R.drawable.astaroth);
        nameToDrawableId.put("Baal", R.drawable.baal);
        nameToDrawableId.put("Belial", R.drawable.belial);

        // 3 KINGDOMS
        nameToDrawableId.put("Sun Quan", R.drawable.sun_quan);
        nameToDrawableId.put("Lu Bu", R.drawable.lu_bu);
        nameToDrawableId.put("Da Qiao & Xiao Qiao", R.drawable.dqxq);
        nameToDrawableId.put("Cao Cao", R.drawable.cao_cao);
        nameToDrawableId.put("Liu Bei", R.drawable.liu_bei);

        // ARCHANGEL
        nameToDrawableId.put("Archangel Raphael", R.drawable.raphael);
        nameToDrawableId.put("Archangel Lucifer", R.drawable.lucifer);
        nameToDrawableId.put("Archangel Gabriel", R.drawable.gabriel);
        nameToDrawableId.put("Archangel Uriel", R.drawable.uriel);
        nameToDrawableId.put("Archangel Michael", R.drawable.michael);

        // JAPANESE
        nameToDrawableId.put("Yomi", R.drawable.yomi);
        nameToDrawableId.put("Kagutsuchi", R.drawable.kagutsuchi);
        nameToDrawableId.put("Amaterasu", R.drawable.amaterasu);
        nameToDrawableId.put("Viper Orochi", R.drawable.orochi);
        nameToDrawableId.put("Susano", R.drawable.susano);

        // INDIAN
        nameToDrawableId.put("Shiva", R.drawable.shiva);
        nameToDrawableId.put("Lakshmi", R.drawable.lakshmi);
        nameToDrawableId.put("Vritra", R.drawable.vritra);
        nameToDrawableId.put("Parvati", R.drawable.parvati);
        nameToDrawableId.put("Indra", R.drawable.indra);

        // EGYPTIAN
        nameToDrawableId.put("Anubis", R.drawable.anubis);
        nameToDrawableId.put("Bastet", R.drawable.bastet);
        nameToDrawableId.put("Horus", R.drawable.horus);
        nameToDrawableId.put("Isis", R.drawable.isis);
        nameToDrawableId.put("Ra", R.drawable.ra);

        // HEROES
        nameToDrawableId.put("Pandora", R.drawable.pandora);
        nameToDrawableId.put("Andromeda", R.drawable.andromeda);
        nameToDrawableId.put("Perseus", R.drawable.perseus);
        nameToDrawableId.put("Sun Wukong ", R.drawable.sun_wukong);
        nameToDrawableId.put("Yamato Takeru", R.drawable.yamato_takeru);

        // NORSE
        nameToDrawableId.put("Freyja", R.drawable.freyja);
        nameToDrawableId.put("Freyr", R.drawable.freyr);
        nameToDrawableId.put("Thor", R.drawable.thor);
        nameToDrawableId.put("Loki", R.drawable.loki);
        nameToDrawableId.put("Idunn&Idunna", R.drawable.idunn_and_idunna);

        // INDIAN 2.0
        nameToDrawableId.put("Sarasvati", R.drawable.sarasvati);
        nameToDrawableId.put("Vishnu", R.drawable.vishnu);
        nameToDrawableId.put("Krishna", R.drawable.krishna);
        nameToDrawableId.put("Ganesha", R.drawable.ganesha);
        nameToDrawableId.put("Durga", R.drawable.durga);

        // EGYPTIAN 2.0
        nameToDrawableId.put("Set", R.drawable.set);
        nameToDrawableId.put("Osiris", R.drawable.osiris);
        nameToDrawableId.put("Hathor", R.drawable.hathor);
        nameToDrawableId.put("Nephthys", R.drawable.nephthys);
        nameToDrawableId.put("Nut", R.drawable.nut);
    }

    public Integer getGodDrawable(String godName)
    {
        return nameToDrawableId.get(godName);
    }
}

