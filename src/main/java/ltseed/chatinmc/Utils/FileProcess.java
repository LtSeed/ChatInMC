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

/**
 This class provides static methods to read and save data files in the plugin data folder,
 including chatters and models.
 @author ltseed
 @version 1.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileProcess {

    private static File dataFolder;
    private static File chattersFolder;

    /**
     * 检查数据文件夹和对话者文件夹是否存在，如果不存在则创建。
     *
     * @param tp 插件实例
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void checkFolders(Plugin tp){
        dataFolder = tp.getDataFolder();
        dataFolder.mkdirs();
        chattersFolder = new File(dataFolder,"chatters");
        chattersFolder.mkdirs();
    }

    /**
     * 从models.yml文件中读取可用的模型列表，并返回包含这些模型名称的字符串列表。
     * 如果文件不存在，则创建一个新的models.yml文件。
     *
     * @return 包含可用模型名称的字符串列表
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static List<String> readModels() {
        List<String> rl = new ArrayList<>();
        rl.add("ChatGPT");
        rl.add("DialogFlow");
        File models = new File(dataFolder, "models");
        try {
            models.createNewFile();
        } catch (IOException e) {
            ChatInMC.debug.err("Error when reading file of models!");
            ChatInMC.debug.debugA(e.getLocalizedMessage());
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(models);
        List<?> list = yml.getList("models");
        if (list != null) {
            list.forEach(o -> rl.add(o.toString()));
        }
        return rl;
    }

    /**
     * 从对话者文件夹中读取所有已保存的对话者文件，并返回一个包含所有对话者的Map。
     * 如果对话者文件夹不存在或为空，则返回一个空的Map。
     *
     * @return 包含所有对话者的Map
     */
    public static Map<UUID, Chatter> readChatters(){
        Map<UUID, Chatter> map = new HashMap<>();
        if(chattersFolder.listFiles() == null) return map;
        for (File file : Objects.requireNonNull(chattersFolder.listFiles())) {
            Chatter value = null;
            try {
                value = new Chatter(file);
            } catch (IOException | InvalidConfigurationException | InvalidChatterException e) {
                ChatInMC.debug.warn("Error when reading file of entities: " + e.getMessage());
            }
            map.put(UUID.fromString(file.getName()), value);
        }
        return map;
    }

    /**
     * 将所有给定的对话者实例保存到磁盘上的对话者文件夹中。
     * 如果该文件夹不存在，则会创建它。
     *
     * @param chatters 要保存的对话者实例的集合
     */
    public static void saveChatters(Collection<Chatter> chatters){
        for (Chatter chatter : chatters) {
            try {
                chatter.saveToFile(chattersFolder);
            } catch (IOException e) {
                ChatInMC.debug.err("Error when saving file of entities, it may cause problem" +
                        "please check file:"+ chatter.getUuid() +":" + e.getMessage());
            }
        }
    }

    /**
     * 将给定的模型名称列表保存到磁盘上的models.yml文件中。
     * 如果该文件不存在，则会创建它。
     *
     * @param models 要保存的模型名称列表
     */
    public static void saveModels(List<String> models){
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("models",models);
        try {
            yml.save(new File(dataFolder,"models.yml"));
        } catch (IOException e) {
            ChatInMC.debug.err("Error when saving file of entities," +
                    "please check file:models.yml: " + e.getMessage());
        }
    }

    /**
     * 删除具有给定UUID的对话者的文件。
     * 如果该文件不存在，则什么都不会发生。
     *
     * @param uuid 要删除文件的对话者的UUID
     */
    public static void deleteChatterFile(UUID uuid) {
        File file = new File(chattersFolder, uuid.toString());
        if(file.exists()) file.delete();
    }
}
