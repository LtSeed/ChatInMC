package ltseed.chatinmc.Talker;

import org.bukkit.entity.Player;

/**
 The MessageBuilder interface provides a contract for implementing classes that can build a Talkative object based on a Player.
 */
public interface MessageBuilder {
    /**
     * Builds a Talkative object based on a Player.
     *
     * @param talker the Player who is talking.
     * @return a Talkative object that represents the talking Player.
     */
    Talkative build(Player talker);
}
