package ltseed.chatinmc.Talker;

import lombok.Getter;
import lombok.Setter;
import ltseed.Exception.InvalidChatterException;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.Talker.ChatGPT.ChatGPTBuilder;
import ltseed.chatinmc.Talker.DialogFlow.DialogFlowBuilder;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static ltseed.chatinmc.ChatInMC.models;
import static ltseed.chatinmc.ChatInMC.ts;
@Getter
@Setter
public class Chatter {

    private String name;
    private String description;

    private UUID uuid;
    //private final double default_temperature;
    private double talk_distance = 10;
    //private Map<UUID, Double> temperature = new HashMap<>();
    //private final String model;

    private Long dialogTime = 60*1000L;

    private MessageBuilder core;

    private Entity te = null;
    @Nullable
    public Entity getEntity(){
        if(te == null) te = ts.getEntity(uuid);
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
        // Add any additional properties you want to describe here
        return descriptionMap;
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveToFile(File Folder) throws IOException {
        ChatInMC.debug.debugB("ltseed.chatinmc.Talker.Chatter#saveToFile called");
        File saving = new File(Folder, String.valueOf(uuid));
        saving.createNewFile();
        YamlConfiguration yml_file = new YamlConfiguration();
        yml_file.set("uuid",String.valueOf(uuid));
        //yml_file.set("default_temperature",default_temperature);
        //yml_file.set("user_temperature",temperature.entrySet());
        //yml_file.set("model",model);
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

        if(dialogTime != null)
            yml_file.set("DialogTime",dialogTime);
        yml_file.save(saving);
    }

    //读取文件时使用
    public Chatter(File chatter) throws IOException, InvalidConfigurationException, InvalidChatterException {
        Long dialogTime1;
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
//        try {
//            default_temperature = yml_file.getDouble("default_temperature");
//        } catch (Exception e) {
//            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_DEFAULT_TEMPERATURE);
//        }
        try {
            talk_distance = yml_file.getDouble("talk_distance");
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_TALK_DISTANCE);
        }
        try {
            dialogTime1 = yml_file.getLong("DialogTime",-1);
            if(dialogTime1 == -1) dialogTime1 = null;
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_DIALOG_TIME);
        }
        try {
            String p = yml_file.getString("modelOrProjectId","text-davinci-003");
            String s = yml_file.getString("type","ChatGPT");
            core = getCore(dialogTime1, s, p);
            if(dialogTime1 == null) dialogTime1 = (long) (60 * 1000);
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
}
