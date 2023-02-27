package ltseed.chatinmc;

import lombok.Getter;
import ltseed.Exception.InvalidChatterException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static ltseed.chatinmc.ChatInMC.ts;
@Getter
public class Chatter {

    private final UUID uuid;
    private final double default_temperature;
    private final double talk_distance;
    private Map<UUID, Double> temperature = new HashMap<>();
    private final String model;

    private MessageBuilder core;

    private Entity te = null;
    @Nullable
    public Entity getEntity(){
        if(te == null) te = ts.getEntity(uuid);
        return te;
    }

    public String chat(String message){
        return core.build().chat(message);
    }

    //新建实例时使用
    Chatter(UUID uuid, double default_temperature, double talk_distance, String model) {
        this.uuid = uuid;
        this.default_temperature = default_temperature;
        this.talk_distance = talk_distance;
        this.model = model;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveToFile(File Folder) throws IOException {
        ChatInMC.debug.debugB("ltseed.chatinmc.Chatter#saveToFile called");
        File saving = new File(Folder, String.valueOf(uuid));
        saving.createNewFile();
        YamlConfiguration yml_file = new YamlConfiguration();
        yml_file.set("uuid",String.valueOf(uuid));
        yml_file.set("default_temperature",default_temperature);
        yml_file.set("user_temperature",temperature.entrySet());
        yml_file.set("model",model);
        yml_file.set("talk_distance",talk_distance);
        yml_file.set("type","chatGPT");
        yml_file.save(saving);
    }

    //读取文件时使用
    Chatter(File chatter) throws IOException, InvalidConfigurationException, InvalidChatterException {
        YamlConfiguration yml_file = new YamlConfiguration();
        yml_file.load(chatter);
        try {
            uuid = UUID.fromString(Objects.requireNonNull(yml_file.getString("uuid")));
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_UUID);
        }
        try {
            default_temperature = yml_file.getDouble("default_temperature");
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_DEFAULT_TEMPERATURE);
        }
        try {
            talk_distance = yml_file.getDouble("talk_distance");
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_TALK_DISTANCE);
        }
        try {
            List<Map<?, ?>> user_temperature = yml_file.getMapList("user_temperature");
            for (Map<?, ?> map : user_temperature) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    temperature.put(UUID.fromString((String) entry.getKey()), Double.parseDouble((String) entry.getValue()));
                }
            }
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_USER_TEMPERATURE);
        }
        try {
            model = yml_file.getString("model");
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.UNFOUNDED_MODEL);
        }
        try {
            String s = yml_file.getString("type","chatGPT");
            if(s.equals("chatGPT")){
                core = GPTChatterBuilder.DEFAULT;
            } else throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_CORE_TYPE);
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.INVALID_CORE_TYPE);
        }

    }

}
