package ltseed.chatinmc.Talker;

import org.bukkit.entity.Player;

public interface MessageBuilder {
    Talkative build(Player talker);
}
