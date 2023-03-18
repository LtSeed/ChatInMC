package ltseed.chatinmc.Utils;

import ltseed.chatinmc.ChatInMC;
import org.bukkit.configuration.file.FileConfiguration;

import static ltseed.chatinmc.ChatInMC.tp;

public class Config {

    public static String chatGPT_key;
    public static String dialogFlowToken;
    //public static Long dialogTime;

    public static String debug;

    public static void readConfig(){
        FileConfiguration fileConfiguration = tp.getConfig();
        chatGPT_key = fileConfiguration.getString("chatGPT_key");
        dialogFlowToken = fileConfiguration.getString("DialogFlowToken");
        //translation = fileConfiguration.getBoolean("translation",false);
        debug = fileConfiguration.getString("debug","N");
        //dialogTime = fileConfiguration.getLong("DialogTime",60*1000L);
        ChatInMC.debug = Debug.valueOf(debug);
    }

    public static void saveConfig(){
        FileConfiguration fileConfiguration = tp.getConfig();
        fileConfiguration.set("chatGPT_key",chatGPT_key);
        fileConfiguration.set("DialogFlowToken", dialogFlowToken);
        //fileConfiguration.set("translation",translation);
        fileConfiguration.set("debug",debug);
        //fileConfiguration.set("DialogTime",dialogTime);
        tp.saveConfig();
    }

}
