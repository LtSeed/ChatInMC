package ltseed.Exception;

import ltseed.chatinmc.ChatInMC;

public class InvalidChatterException extends Exception {

    final TYPE type;

    public InvalidChatterException(TYPE type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        String s = super.getMessage() + ":" + type.name();
        ChatInMC.debug.warn(type.name());
        ChatInMC.debug.debugA(s);
        return s;
    }

    @SuppressWarnings("unused")
    public enum TYPE {
        INVALID_UUID, INVALID_DEFAULT_TEMPERATURE, INVALID_USER_TEMPERATURE, UNFOUNDED_MODEL, INVALID_TALK_DISTANCE, INVALID_CORE_TYPE, INVALID_NAME_OR_DESCRIPTION, INVALID_DIALOG_TIME
    }
}
