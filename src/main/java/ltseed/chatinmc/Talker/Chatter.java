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
import java.util.*;

import static ltseed.chatinmc.ChatInMC.*;

@Getter
@Setter
public class Chatter {

    private String name;
    private String description;
    private UUID uuid;
    private double talk_distance = 10;
    private long dialogTime = 60*1000L;
    private MessageBuilder core;
    private Entity te = null;
    @Nullable
    public Entity getEntity(){
        if(te == null) {
            Bukkit.getScheduler().runTaskLater(tp, () -> te = ts.getEntity(uuid), 5);
        }
        return te;
    }

    //新建实例时使用
    public Chatter() {
    }

    public Map<String, Object> describe() {
        Map<String, Object> descriptionMap = new HashMap<>();
        descriptionMap.put("name", this.name);
        descriptionMap.put("description", this.description);
        descriptionMap.put("uuid", this.uuid.toString());
        descriptionMap.put("talk_distance", this.talk_distance);
        descriptionMap.put("dialogTime", this.dialogTime);
        return descriptionMap;
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveToFile(File Folder) throws IOException {
        ChatInMC.debug.debugB("ltseed.chatinmc.Talker.Chatter#saveToFile called");
        File saving = new File(Folder, String.valueOf(uuid));
        saving.createNewFile();
        YamlConfiguration yml_file = new YamlConfiguration();
        yml_file.set("uuid",String.valueOf(uuid));
        yml_file.set("talk_distance",talk_distance);
        yml_file.set("name",name);
        yml_file.set("description",description);
        if(core instanceof ChatGPTBuilder){
            yml_file.set("type","ChatGPT");
            yml_file.set("modelOrProjectId","ChatGPT_" + models);
            ((ChatGPTBuilder) core).writeFile(yml_file);
        } else {
            yml_file.set("type","DialogFlow");
            yml_file.set("modelOrProjectId",((DialogFlowBuilder)core).getProjectId());
        }
        yml_file.set("DialogTime",dialogTime);
        yml_file.save(saving);
    }

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
            name = yml_file.getString("name","NF");
            description = yml_file.getString("description","NF");
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
            String p = yml_file.getString("modelOrProjectId","text-davinci-003");
            String s = yml_file.getString("type","ChatGPT");
            core = getCore(dialogTime1, s, p);
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_CORE_TYPE);
        }
        dialogTime = dialogTime1;

        if(core instanceof ChatGPTBuilder){
            ((ChatGPTBuilder)core).readFile(yml_file);
        }
    }

    public static MessageBuilder getCore(Long dialogTime1, String model, String projectId) throws InvalidChatterException {
        switch (model) {
            case "ChatGPT":
                return ChatGPTBuilder.getDefault();
            case "DialogFlow":
                return new DialogFlowBuilder(projectId, dialogTime1);
            default:
                if(model.contains("ChatGPT")) {
                    ChatGPTBuilder aDefault = ChatGPTBuilder.getDefault();
                    aDefault.setModel(model.replace("ChatGPT_",""));
                    return aDefault;
                }
                throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_CORE_TYPE);
        }
    }

    public void delete() {
        FileProcess.deleteChatterFile(uuid);
    }
}
