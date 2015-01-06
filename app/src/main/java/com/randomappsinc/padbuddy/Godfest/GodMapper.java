package com.randomappsinc.padbuddy.Godfest;

/**
 * Created by Alex on 11/30/2014.
 */

import com.randomappsinc.padbuddy.Models.MonsterAttributes;
import com.randomappsinc.padbuddy.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodMapper
{
    private static GodMapper instance = null;
    private static HashMap<String, Integer> nameToDrawableId = new HashMap<String, Integer>();
    private static HashMap<String, MonsterAttributes> nameToAttributes = new HashMap<String, MonsterAttributes>();
    private static ArrayList<String> friendFinderMonsterList = new ArrayList<String>();

    private GodMapper()
    {
        setUpMapper();
        setUpFriendFinderMonsterList();
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
        nameToDrawableId.put("Sun Wukong", R.drawable.sun_wukong);
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

        // NORN GIRLS
        nameToDrawableId.put("The Norn Urd", R.drawable.urd);
        nameToDrawableId.put("The Norn Verdandi", R.drawable.verdandi);
        nameToDrawableId.put("The Norn Skuld", R.drawable.skuld);

        // FRIEND FINDER CREATURES
        nameToAttributes.put("Ancient Dragon Knight, Zeal", new MonsterAttributes(9, 5, 3, R.drawable.adk_zeal));
        nameToAttributes.put("World Tree Sprite, Alraune", new MonsterAttributes(99, 11, 3, R.drawable.alraune_green));
        nameToAttributes.put("Dancing Light, Amaterasu Ohkami", new MonsterAttributes(99, 11, 4, R.drawable.ama_light));
        nameToAttributes.put("Deathly Hell Deity Jackal, Anubis", new MonsterAttributes(99, 11, 4, R.drawable.anubis_final));
        nameToAttributes.put("Heavenly Herald, Archangel", new MonsterAttributes(99, 6, 3, R.drawable.archangel_blue));
        nameToAttributes.put("Guardian of the Sacred City, Athena", new MonsterAttributes(99, 9, 6, R.drawable.athena_final));
        nameToAttributes.put("Moonlit Feline Goddess, Bastet", new MonsterAttributes(99, 8, 4, R.drawable.bastet_dark));
        nameToAttributes.put("Feline Deity of Harmony, Bastet", new MonsterAttributes(99, 8, 4, R.drawable.bastet_light));
        nameToAttributes.put("BAO Batman+Batarang", new MonsterAttributes(50, 4, 0, R.drawable.batman_4));
        nameToAttributes.put("BAO Batman+BW Stealth", new MonsterAttributes(99, 4, 7, R.drawable.batman_blue_final));
        nameToAttributes.put("Crazed King of Purgatory, Beelzebub", new MonsterAttributes(99, 5, 8, R.drawable.beelz_final));
        nameToAttributes.put("Endless Blue Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.drawable.blonia_7));
        nameToAttributes.put("Keeper of Paradise, Metatron", new MonsterAttributes(99, 6, 6, R.drawable.btron));
        nameToAttributes.put("Awoken Ceres", new MonsterAttributes(99, 3, 8, R.drawable.ceres_final));
        nameToAttributes.put("Destroying Goddess of Power, Kali", new MonsterAttributes(99, 6, 6, R.drawable.dkali_6));
        nameToAttributes.put("Arbiter of Judgement, Metatron", new MonsterAttributes(99, 4, 7, R.drawable.dmeta_final));
        nameToAttributes.put("Divine Flowers, Da Qiao & Xiao Qiao", new MonsterAttributes(99, 6, 4, R.drawable.dqxq_6));
        nameToAttributes.put("Demon Slaying Goddess, Durga", new MonsterAttributes(99, 6, 4, R.drawable.durga_6));
        nameToAttributes.put("Hell-Creating Archdemon, Lucifer", new MonsterAttributes(99, 16, 4, R.drawable.fa_lucifer_dark));
        nameToAttributes.put("Goemon, the Thief", new MonsterAttributes(99, 15, 3, R.drawable.goemon_7));
        nameToAttributes.put("Shining Lance Wielder, Odin", new MonsterAttributes(99, 6, 8, R.drawable.grodin_final));
        nameToAttributes.put("Eternal Jade Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.drawable.gronia_7));
        nameToAttributes.put("Genius Sleeping Dragon, Zhuge Liang", new MonsterAttributes(99, 5, 5, R.drawable.gzl_6));
        nameToAttributes.put("Banishing Claw Byakko, Haku", new MonsterAttributes(99, 6, 7, R.drawable.haku_final));
        nameToAttributes.put("Sacred Life Goddess, Hathor", new MonsterAttributes(99, 6, 4, R.drawable.hathor_6));
        nameToAttributes.put("Blazing Deity Falcon, Horus", new MonsterAttributes(99, 11, 4, R.drawable.horus_final));
        nameToAttributes.put("Eternal Twin Stars, Idunn&Idunna", new MonsterAttributes(99, 9, 4, R.drawable.i_and_i_healer));
        nameToAttributes.put("Shining Sea Deity, Isis", new MonsterAttributes(99, 6, 4, R.drawable.isis_light));
        nameToAttributes.put("Kirin of the Sacred Gleam, Sakuya", new MonsterAttributes(99, 6, 7, R.drawable.kirin_final));
        nameToAttributes.put("Divine Brave General, Krishna", new MonsterAttributes(99, 6, 4, R.drawable.krishna_6));
        nameToAttributes.put("Devoted Miko Goddess, Kushinadahime", new MonsterAttributes(99, 4, 7, R.drawable.kush_final));
        nameToAttributes.put("Shining Goddess of Secrets, Kali", new MonsterAttributes(99, 6, 5, R.drawable.lkali_6));
        nameToAttributes.put("Keeper of the Sacred Texts, Metatron", new MonsterAttributes(99, 6, 6, R.drawable.lmeta_final));
        nameToAttributes.put("Divine Flying General, Lu Bu", new MonsterAttributes(99, 6, 4, R.drawable.lubu_6));
        nameToAttributes.put("Divine Law Goddess, Valkyrie Rose", new MonsterAttributes(99, 7, 5, R.drawable.lvalk_final));
        nameToAttributes.put("Celestial Life Dragon, Zhuge Liang", new MonsterAttributes(99, 6, 6, R.drawable.lzl_7));
        nameToAttributes.put("Sea God's Songstress, Siren", new MonsterAttributes(99, 7, 3, R.drawable.mermaid_final));
        nameToAttributes.put("Awoken Minerva", new MonsterAttributes(99, 3, 8, R.drawable.minerva_final));
        nameToAttributes.put("Goddess of the Dead, Nephthys", new MonsterAttributes(99, 6, 4, R.drawable.nephthys_6));
        nameToAttributes.put("Awoken Neptune", new MonsterAttributes(99, 3, 8, R.drawable.neptune_final));
        nameToAttributes.put("Roaming National Founder, Okuninushi", new MonsterAttributes(99, 4, 4, R.drawable.oku_final));
        nameToAttributes.put("God of Dark Riches, Osiris", new MonsterAttributes(99, 6, 4, R.drawable.osiris_6));
        nameToAttributes.put("Goddess of the Bleak Night, Pandora", new MonsterAttributes(99, 6, 4, R.drawable.pandora_6));
        nameToAttributes.put("Pure Light Sun Deity, Ra", new MonsterAttributes(99, 8, 4, R.drawable.ra_light));
        nameToAttributes.put("BAO Robin", new MonsterAttributes(99, 4, 0, R.drawable.robin_4));
        nameToAttributes.put("Awoken Phantom God, Odin", new MonsterAttributes(99, 6, 6, R.drawable.rodin_7));
        nameToAttributes.put("Marvelous Red Dragon Caller, Sonia", new MonsterAttributes(99, 6, 6, R.drawable.ronia_final));
        nameToAttributes.put("Holy Night Kirin Princess, Sakuya", new MonsterAttributes(99, 6, 7, R.drawable.santa_kirin));
        nameToAttributes.put("King of Hell, Satan", new MonsterAttributes(99, 16, 3, R.drawable.satan_final));
        nameToAttributes.put("Demolishing Creator, Shiva", new MonsterAttributes(99, 6, 4, R.drawable.shiva_final));
        nameToAttributes.put("Rebel Seraph Lucifer", new MonsterAttributes(99, 16, 4, R.drawable.sod_lucifer_final));
        nameToAttributes.put("Sylph", new MonsterAttributes(35, 6, 0, R.drawable.sylph_4));
        nameToAttributes.put("Divine Wardens, Umisachi&Yamasachi", new MonsterAttributes(99, 4, 7, R.drawable.u_and_y_final));
        nameToAttributes.put("Awoken Zeus Olympios", new MonsterAttributes(99, 16, 3, R.drawable.zeus_light));
        nameToAttributes.put("BAO Robin+E. Stick", new MonsterAttributes(99, 4, 3, R.drawable.robin_5));
        nameToAttributes.put("Dark Sun Deity, Ra", new MonsterAttributes(99, 8, 4, R.drawable.ra_dark));
        nameToAttributes.put("Gleaming Kouryu Emperor, Fagan", new MonsterAttributes(99, 6, 8, R.drawable.fagan_light));
        nameToAttributes.put("Dark Kouryu Emperor, Fagan", new MonsterAttributes(99, 6, 8, R.drawable.fagan_dark));
        nameToAttributes.put("Seraph of Dawn Lucifer", new MonsterAttributes(99, 16, 4, R.drawable.sod_lucifer));
        nameToAttributes.put("Dark Liege, Vampire Duke", new MonsterAttributes(99, 7, 3, R.drawable.vamp_dark));
        nameToAttributes.put("Jester Dragon, Drawn Joker", new MonsterAttributes(99, 7, 3, R.drawable.drawn_joker_final));
        nameToAttributes.put("Asuka&Eva Unit-02", new MonsterAttributes(50, 11, 0, R.drawable.asuka_eva_4));
        nameToAttributes.put("Awoken Dancing Queen Hera-Ur", new MonsterAttributes(99, 5, 6, R.drawable.hera_ur_final));
        nameToAttributes.put("Awoken Hinokagutsuchi", new MonsterAttributes(99, 5, 8, R.drawable.kagutsuchi_final));
        nameToAttributes.put("Crimson Lotus Mistress, Echidna", new MonsterAttributes(99, 6, 3, R.drawable.echidna_final));
        nameToAttributes.put("Unyielding Samurai Dragon King, Zaerog", new MonsterAttributes(99, 5, 4, R.drawable.zaerog_samurai));
        nameToAttributes.put("TAMADRApurin", new MonsterAttributes(99, 5, 4, R.drawable.tamadrapurin));
        nameToAttributes.put("Norn of the Past, Urd", new MonsterAttributes(99, 6, 5, R.drawable.urd_6));
        nameToAttributes.put("Norn of the Present, Verdandi", new MonsterAttributes(99, 6, 5, R.drawable.verdandi_6));
        nameToAttributes.put("Norn of the Future, Skuld", new MonsterAttributes(99, 6, 6, R.drawable.skuld_7));
        nameToAttributes.put("Gods of Hunt, Umisachi&Yamasachi", new MonsterAttributes(99, 4, 4, R.drawable.u_and_y_6));
        nameToAttributes.put("Awoken Odin", new MonsterAttributes(99, 6, 5, R.drawable.grodin_6));
        nameToAttributes.put("Nocturne Chanter, Tsukuyomi", new MonsterAttributes(99, 6, 4, R.drawable.yomi_dark));
        nameToAttributes.put("Hand of the Dark God, Metatron", new MonsterAttributes(99, 6, 6, R.drawable.dmeta_7));
        nameToAttributes.put("Warrior Rose, Graceful Valkyrie", new MonsterAttributes(99, 7, 3, R.drawable.valk_7));
        nameToAttributes.put("Scholarly God, Ganesha", new MonsterAttributes(99, 6, 4, R.drawable.ganesha_6));
        nameToAttributes.put("Goddess of Rice Fields, Kushinada", new MonsterAttributes(99, 4, 4, R.drawable.kush_6));
        nameToAttributes.put("Voice of God, Metatron", new MonsterAttributes(99, 6, 5, R.drawable.lmeta_6));
        nameToAttributes.put("Extant Red Dragon Caller, Sonia", new MonsterAttributes(99, 6, 5, R.drawable.ronia_6));
        nameToAttributes.put("Kirin of the Aurora, Sakuya", new MonsterAttributes(99, 6, 4, R.drawable.kirin_6));
    }

    private static void setUpFriendFinderMonsterList()
    {
        for (String key : nameToAttributes.keySet())
        {
            friendFinderMonsterList.add(key);
        }
    }

    public ArrayList<String> getFriendFinderMonsterList()
    {
        return (ArrayList<String>) friendFinderMonsterList.clone();
    }

    public Integer getGodDrawable(String godName)
    {
        return nameToDrawableId.get(godName);
    }
}

