package ltseed.chatinmc;

import ltseed.Exception.InvalidChatterException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileProcess {

    private static File dataFolder;
    private static File chattersFolder;
    private static File modelsFolder;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void checkFolders(Plugin tp){
        dataFolder = tp.getDataFolder();
        dataFolder.mkdirs();
        chattersFolder = new File(dataFolder,"chatters");
        modelsFolder = new File(dataFolder,"models");
        chattersFolder.mkdirs();
        modelsFolder.mkdirs();
    }
    public static Map<String, Model> readModels(){
        Map<String, Model> map = new HashMap<>();
        if(modelsFolder.listFiles() == null) return map;
        for (File file : Objects.requireNonNull(modelsFolder.listFiles())) {
            map.put(file.getName(),new Model());
        }
        return map;
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
                        "请检查文件（"+chatter.uuid+"）: " + e.getMessage());
            }
        }
    }
    public static void saveModels(Collection<Model> models){
        for (Model model : models) {
            try {
                model.saveToFile(modelsFolder);
            } catch (IOException e) {
                ChatInMC.debug.err("在保存模型数据时发生了错误，这可能导致重大的问题，" +
                        "请检查文件（"+model.getName()+"）: " + e.getMessage());
            }
        }
    }
}
