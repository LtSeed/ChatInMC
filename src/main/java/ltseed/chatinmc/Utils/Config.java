package ltseed.chatinmc.Utils;

import ltseed.chatinmc.ChatInMC;
import org.bukkit.configuration.file.FileConfiguration;

import static ltseed.chatinmc.ChatInMC.tp;

/**
 * The Config class is responsible for reading and saving the configuration data
 * for the ChatInMC plugin. The configuration data includes the ChatGPT API key,
 * DialogFlow authentication token, and debug mode flag.
 *
 * @author ltseed
 * @version 1.0
 */
public class Config {

    /**
     * The ChatGPT API key used to authenticate requests to the ChatGPT API.
     */
    public static String chatGPT_key;

    /**
     * The DialogFlow authentication token used to authenticate requests to the DialogFlow API.
     */
    public static String dialogFlowToken;

    /**
     * The debug mode flag. If set to "Y", additional debug information will be output to the console.
     */
    public static String debug;

    /**
     * Reads the configuration data from the plugin's config.yml file and assigns
     * the values to the corresponding fields in the Config class.
     */
    public static void readConfig() {
        FileConfiguration fileConfiguration = tp.getConfig();
        chatGPT_key = fileConfiguration.getString("chatGPT_key");
        dialogFlowToken = fileConfiguration.getString("DialogFlowToken");
        debug = fileConfiguration.getString("debug", "N");
        ChatInMC.debug = Debug.valueOf(debug);
    }

    /**
     * Saves the current configuration data to the plugin's config.yml file.
     */
    public static void saveConfig() {
        FileConfiguration fileConfiguration = tp.getConfig();
        fileConfiguration.set("chatGPT_key", chatGPT_key);
        fileConfiguration.set("DialogFlowToken", dialogFlowToken);
        fileConfiguration.set("debug", debug);
        tp.saveConfig();
    }

}
