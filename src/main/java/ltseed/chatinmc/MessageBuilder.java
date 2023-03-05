package ltseed.chatinmc;

import org.bukkit.entity.Player;

public interface MessageBuilder {
    Talkative build(Player talker);
}
