package ltseed.chatinmc;

import ltseed.chatinmc.PlayerInteraction.GUI.EnabledView;
import ltseed.chatinmc.PlayerInteraction.GUI.SimpleGUI;
import ltseed.chatinmc.Talker.Chatter;
import ltseed.chatinmc.Talker.ChatterListener;
import ltseed.chatinmc.Talker.DialogFlow.CloudSDKInstaller;
import ltseed.chatinmc.Talker.DialogFlow.DialogFlowInstaller;
import ltseed.chatinmc.Utils.CmdTest;
import ltseed.chatinmc.Utils.Config;
import ltseed.chatinmc.Utils.Debug;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.*;

import static ltseed.chatinmc.Talker.ChatGPT.ChatGPTModelUtils.getAvailableModels;
import static ltseed.chatinmc.Utils.Config.*;
import static ltseed.chatinmc.Utils.FileProcess.*;

/**
 * The main class of the ChatInMC plugin. This class extends JavaPlugin and handles plugin initialization and deactivation.
 * <p>
 * This plugin provides an AI chatbot feature to Minecraft, allowing players to chat with an AI powered by ChatGPT.
 * </p>
 * <p>
 * This plugin also provides several GUIs for the chatbot, and allows players to interact with the chatbot through commands.
 * </p>
 * <p>
 * This class initializes the plugin and registers all necessary events, commands and GUIs.
 * </p>
 *
 * @author ltseed
 * @version 1.0
 * @since 2023-03-19
 */
public final class ChatInMC extends JavaPlugin {

    /** The Bukkit server instance */
    public static Server ts;

    /** The ChatInMC plugin instance */
    public static Plugin tp;

    /** A list of available models for the ChatGPT AI */
    public static List<String> models;

    /** A map of all active chatters */
    public static Map<UUID, Chatter> chatters;

    /** The debug utility for the plugin */
    public static Debug debug;

    /**
     * Called when the plugin is being enabled.
     * Initializes the plugin and registers all necessary events, commands and GUIs.
     */
    @Override
    public void onEnable() {
        //获取静态实例
        ts = getServer();
        tp = this;

        //读取config
        readConfig();
        debug.loadDebug(s -> tp.getLogger().info(s));
        debug.info("CIM start to load");

        //检测运行环境
        new Thread(() -> {
            try {
                CmdTest.test();
                if (!CloudSDKInstaller.isCloudSdkInstalled())
                    CloudSDKInstaller.install();
                if (!DialogFlowInstaller.isDialogFlowInstalled())
                    DialogFlowInstaller.install();
            } catch (Exception e) {
                debug.err("fail to install google! You may not able to use DialogFlow!");
            }
        }).start();

        //读取data
        checkFolders(tp);
        models = readModels();
        try {
            models.addAll(getAvailableModels(chatGPT_key));
        } catch (Exception e) {
            debug.err("Unable to get models, you can only use GPT_PROXY");
            models.add("ChatGPT_PROXY");
        }
        chatters = readChatters();
        debug.info("Read " + chatters.size() + "Chatters");
        ts.getPluginManager().registerEvents(new ChatterListener(),this);

        //注册所有GUI
        enableAllViews();

        //注册指令处理类
        Objects.requireNonNull(ts.getPluginCommand("cim")).setExecutor(new Commands());
        Objects.requireNonNull(ts.getPluginCommand("cim")).setTabCompleter(new Commands());

        debug.info("Load Success!");
    }

    /**
     * Enables all GUIs annotated with {@link ltseed.chatinmc.PlayerInteraction.GUI.EnabledView}.
     */
    private void enableAllViews() {
        Reflections reflections = new Reflections("ltseed.chatinmc");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(EnabledView.class);

        for (Class<?> clazz : annotated) {
            try {
                SimpleGUI simpleGUI = (SimpleGUI) clazz.getDeclaredConstructor().newInstance();
                simpleGUI.enableView();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Called when the plugin is being disabled.
     * save data.
     */
    @Override
    public void onDisable() {
        Config.saveConfig();
        saveChatters(chatters.values());
        saveModels(models);
    }
}
