package ltseed.chatinmc.Utils;

import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.Talker.Chatter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileProcess {

    private static File dataFolder;
    private static File chattersFolder;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void checkFolders(Plugin tp){
        dataFolder = tp.getDataFolder();
        dataFolder.mkdirs();
        chattersFolder = new File(dataFolder,"chatters");
        chattersFolder.mkdirs();
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static List<String> readModels() {
        List<String> rl = new ArrayList<>();
        File models = new File(dataFolder, "models");
        try {
            models.createNewFile();
        } catch (IOException e) {
            ChatInMC.debug.err("在读取models文件时发生错误！");
            ChatInMC.debug.debugA(e.getLocalizedMessage());
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(models);
        List<?> list = yml.getList("models");
        if (list != null) {
            list.forEach(o -> rl.add(o.toString()));
        }
        return rl;
    }

    public static Map<UUID, Chatter> readChatters(){
        Map<UUID, Chatter> map = new HashMap<>();
        if(chattersFolder.listFiles() == null) return map;
        for (File file : Objects.requireNonNull(chattersFolder.listFiles())) {
            Chatter value = null;
            try {
                value = new Chatter(file);
            } catch (IOException | InvalidConfigurationException | InvalidChatterException e) {
                ChatInMC.debug.warn("在读取实体数据时发生了错误: " + e.getMessage());
            }
            map.put(UUID.fromString(file.getName()), value);
        }
        return map;
    }
    public static void saveChatters(Collection<Chatter> chatters){
        for (Chatter chatter : chatters) {
            try {
                chatter.saveToFile(chattersFolder);
            } catch (IOException e) {
                ChatInMC.debug.err("在保存实体数据时发生了错误，这可能导致重大的问题，" +
                        "请检查文件（"+ chatter.getUuid() +"）: " + e.getMessage());
            }
        }
    }
    public static void saveModels(List<String> models){
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("models",models);
        try {
            yml.save(new File(dataFolder,"models.yml"));
        } catch (IOException e) {
            ChatInMC.debug.err("在保存模型数据时发生了错误，" +
                    "请检查文件（models.yml）: " + e.getMessage());
        }
    }
}
