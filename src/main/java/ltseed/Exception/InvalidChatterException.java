package ltseed.Exception;

import ltseed.chatinmc.ChatInMC;
import org.bukkit.ChatColor;

public class InvalidChatterException extends Exception {

    final TYPE type;

    public InvalidChatterException(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return type;
    }

    @Override
    public String getMessage() {
        String s = super.getMessage() + ":" + type.name();
        ChatInMC.debug.warn(type.name());
        ChatInMC.debug.debugA(s);
        return s;
    }

    public enum TYPE {
        INVALID_UUID, INVALID_DEFAULT_TEMPERATURE, INVALID_USER_TEMPERATURE, UNFOUNDED_MODEL, INVALID_TALK_DISTANCE, INVALID_CORE_TYPE;
    }
}
