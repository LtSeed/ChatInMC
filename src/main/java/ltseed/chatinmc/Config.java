package ltseed.chatinmc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bukkit.configuration.file.FileConfiguration;

import static ltseed.chatinmc.ChatInMC.tp;

public class Config {

    public static String chatGPT_key;
    public static String translation_key;

    public static boolean translation;
    public static String debug;

    public static JSONArray chatting_entity;
    public static JSONObject entity_information;

    public static void readConfig(){
        FileConfiguration fileConfiguration = tp.getConfig();
        chatGPT_key = fileConfiguration.getString("chatGPT_key");
        translation_key = fileConfiguration.getString("translation_key");
        translation = fileConfiguration.getBoolean("translation",false);
        debug = fileConfiguration.getString("debug","N");
        ChatInMC.debug = Debug.valueOf(debug);
    }

    public static void saveConfig(){
        FileConfiguration fileConfiguration = tp.getConfig();
        fileConfiguration.set("chatGPT_key",chatGPT_key);
        fileConfiguration.set("translation_key",translation_key);
        fileConfiguration.set("debug",debug);
        tp.saveConfig();
    }
}
