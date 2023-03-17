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

import static ltseed.chatinmc.Utils.Config.*;
import static ltseed.chatinmc.Utils.FileProcess.*;

public final class ChatInMC extends JavaPlugin {

    public static Server ts;
    public static Plugin tp;

    public static List<String> models;
    public static Map<UUID, Chatter> chatters;

    public static Debug debug;
    @Override
    public void onEnable() {
        //获取静态实例
        ts = getServer();
        tp = this;

        //读取config
        readConfig();
        debug.loadDebug(s -> tp.getLogger().info(s));

        //检测运行环境
        try {
            CmdTest.test();
            if(!CloudSDKInstaller.isCloudSdkInstalled())
                CloudSDKInstaller.install();
            if(!DialogFlowInstaller.isDialogFlowInstalled())
                DialogFlowInstaller.install();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //读取data
        checkFolders(tp);
        models = readModels();
        chatters = readChatters();
        ts.getPluginManager().registerEvents(new ChatterListener(),this);

        //注册所有GUI
        enableAllViews();

        //注册指令处理类
        Objects.requireNonNull(ts.getPluginCommand("cim")).setExecutor(new Commands());

        debug.info("已经成功加载！");
    }


    private void enableAllViews() {
        Reflections reflections = new Reflections("ltseed.chatinmc");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(EnabledView.class);

        for (Class<?> clazz : annotated) {
            try {
                SimpleGUI simpleGUI = (SimpleGUI) clazz.getDeclaredConstructor().newInstance();
                simpleGUI.enableView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        Config.saveConfig();
        saveChatters(chatters.values());
        saveModels(models);
    }
}
