package ltseed.chatinmc.Talker;

import lombok.Getter;
import lombok.Setter;
import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.Talker.ChatGPT.ChatGPTBuilder;
import ltseed.chatinmc.Talker.DialogFlow.DialogFlowBuilder;
import ltseed.chatinmc.Utils.FileProcess;
import ltseed.chatinmc.Utils.TimeConverter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static ltseed.chatinmc.ChatInMC.*;

/**
 * This class represents a Chatter who is capable of communicating with players in Minecraft.
 * It contains methods for saving, loading, and deleting the chatter's configuration data.
 * It also contains a method for generating a description of the chatter's properties.
 *
 * @author ltseed
 * @version 1.0
 */
@Getter
@Setter
public class Chatter {

    /**
     * 聊天机器人的名字。
     */
    private String name;

    /**
     * 聊天机器人的描述信息。
     */
    private String description;

    /**
     * 聊天机器人的 UUID。
     */
    private UUID uuid;

    /**
     * 聊天机器人的对话距离。
     */
    private double talk_distance = 10;

    /**
     * 聊天机器人对话的超时时间，单位为毫秒。
     */
    private long dialogTime = 60 * 1000L;

    /**
     * 聊天机器人的消息生成器。
     */
    private MessageBuilder core;

    /**
     * 聊天机器人的实体对象。
     */
    private Entity te = null;

    /**
     * Constructs a new Chatter object.
     */
    //新建实例时使用
    public Chatter() {
    }

    /**
     * Constructs a new Chatter object by loading the chatter's configuration data from a file.
     *
     * @param chatter the file containing the chatter's configuration data
     * @throws IOException                   if an I/O error occurs while reading the file
     * @throws InvalidConfigurationException if the file contains invalid configuration data
     * @throws InvalidChatterException       if the file contains invalid chatter data
     */
    //读取文件时使用
    public Chatter(File chatter) throws IOException, InvalidConfigurationException, InvalidChatterException {
        long dialogTime1;
        YamlConfiguration yml_file = new YamlConfiguration();
        yml_file.load(chatter);
        try {
            uuid = UUID.fromString(Objects.requireNonNull(yml_file.getString("uuid")));
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_UUID);
        }
        try {
            name = yml_file.getString("name", "NF");
            description = yml_file.getString("description", "NF");
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_NAME_OR_DESCRIPTION);
        }
        try {
            talk_distance = yml_file.getDouble("talk_distance");
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_TALK_DISTANCE);
        }
        try {
            dialogTime1 = yml_file.getLong("DialogTime", TimeConverter.getTime("1时"));
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_DIALOG_TIME);
        }
        try {
            String p = yml_file.getString("modelOrProjectId", "text-davinci-003");
            String s = yml_file.getString("type", "ChatGPT");
            core = getCore(dialogTime1, s, p);
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_CORE_TYPE);
        }
        dialogTime = dialogTime1;

        if (core instanceof ChatGPTBuilder) {
            ((ChatGPTBuilder) core).readFile(yml_file);
        }
    }

    /**
     * 获取消息构建器实例，根据传入的模型类型和项目ID进行创建。
     *
     * @param dialogTime1 消息对话开始时间
     * @param model       模型类型，支持 "ChatGPT" 和 "DialogFlow" 两种类型，以及以 "ChatGPT_" 开头的自定义模型类型
     * @param projectId   项目ID，仅在使用 "DialogFlow" 模型时需要传入
     * @return 返回对应的消息构建器实例
     * @throws InvalidChatterException 当模型类型不合法时抛出异常
     */
    public static MessageBuilder getCore(Long dialogTime1, String model, String projectId) throws InvalidChatterException {
        switch (model) {
            case "ChatGPT":
                return ChatGPTBuilder.getDefault();
            case "DialogFlow":
                return new DialogFlowBuilder(projectId, dialogTime1);
            default:
                if (model.contains("ChatGPT")) {
                    ChatGPTBuilder aDefault = ChatGPTBuilder.getDefault();
                    aDefault.setModel(model.replace("ChatGPT_", ""));
                    return aDefault;
                }
                throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_CORE_TYPE);
        }
    }

    /**
     * Returns the entity associated with the chatter.
     *
     * @return the entity associated with the chatter
     */
    @Nullable
    public Entity getEntity() {
        if (te == null) {
            Bukkit.getScheduler().runTaskLater(tp, () -> te = ts.getEntity(uuid), 5);
        }
        return te;
    }

    /**
     * Returns a map of the chatter's properties.
     *
     * @return a map of the chatter's properties
     */
    public Map<String, Object> describe() {
        Map<String, Object> descriptionMap = new HashMap<>();
        descriptionMap.put("name", this.name);
        descriptionMap.put("description", this.description);
        descriptionMap.put("uuid", this.uuid.toString());
        descriptionMap.put("talk_distance", this.talk_distance);
        descriptionMap.put("dialogTime", this.dialogTime);
        return descriptionMap;
    }

    /**
     * Saves the chatter's configuration data to a file.
     *
     * @param Folder the folder in which to save the file
     * @throws IOException if an I/O error occurs while saving the file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveToFile(File Folder) throws IOException {
        ChatInMC.debug.debugB("ltseed.chatinmc.Talker.Chatter#saveToFile called");
        File saving = new File(Folder, String.valueOf(uuid));
        saving.createNewFile();
        YamlConfiguration yml_file = new YamlConfiguration();
        yml_file.set("uuid", String.valueOf(uuid));
        yml_file.set("talk_distance", talk_distance);
        yml_file.set("name", name);
        yml_file.set("description", description);
        if (core instanceof ChatGPTBuilder) {
            yml_file.set("type", "ChatGPT");
            yml_file.set("modelOrProjectId", "ChatGPT_" + models);
            ((ChatGPTBuilder) core).writeFile(yml_file);
        } else {
            yml_file.set("type", "DialogFlow");
            yml_file.set("modelOrProjectId", ((DialogFlowBuilder) core).getProjectId());
        }
        yml_file.set("DialogTime", dialogTime);
        yml_file.save(saving);
    }

    /**
     * 删除当前实例所对应的 chatter 文件。
     */
    public void delete() {
        FileProcess.deleteChatterFile(uuid);
    }
}
