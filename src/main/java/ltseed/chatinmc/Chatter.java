package ltseed.chatinmc;

import ltseed.Exception.InvalidChatterException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static ltseed.chatinmc.ChatInMC.models;

public class Chatter {

    final UUID uuid;
    final double default_temperature;
    Map<UUID, Double> temperature = new HashMap<>();
    final Model model;

    //新建实例时使用
    Chatter(UUID uuid, double default_temperature, Model model) {
        this.uuid = uuid;
        this.default_temperature = default_temperature;
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
        yml_file.set("model",model.getName());
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
            model = models.get(yml_file.getString("model"));
        } catch (Exception e) {
            throw new InvalidChatterException(InvalidChatterException.TYPE.UNFOUNDED_MODEL);
        }
    }
}
