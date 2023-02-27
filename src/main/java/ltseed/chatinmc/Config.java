package ltseed.chatinmc;

import org.bukkit.configuration.file.FileConfiguration;

import static ltseed.chatinmc.ChatInMC.tp;

public class Config {

    public static String chatGPT_key;
    public static String translation_key;

    public static boolean translation;
    public static String debug;

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
        fileConfiguration.set("translation",translation);
        fileConfiguration.set("debug",debug);
        tp.saveConfig();
    }
}
