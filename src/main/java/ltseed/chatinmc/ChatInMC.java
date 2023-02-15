package ltseed.chatinmc;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static ltseed.chatinmc.Config.*;

public final class ChatInMC extends JavaPlugin {

    public static Server ts;
    public static Plugin tp;
    @Override
    public void onEnable() {
        ts = getServer();
        tp = this;
        readConfig();
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        Config.saveConfig();
        // Plugin shutdown logic
    }
}
