package ltseed.chatinmc.PlayerInteraction;

import ltseed.chatinmc.ChatInMC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ltseed.chatinmc.ChatInMC.tp;
import static ltseed.chatinmc.ChatInMC.ts;
import static org.bukkit.event.EventPriority.LOW;

/**
 * A class representing a conversation with a player.
 * Only one conversation can be active with a player at a time.
 *
 * @author LtSeed
 * @version 1.0
 */
public class PlayerConversation implements Listener {

    //一个玩家同时只能进行一个对话。
    public static final Map<Player, PlayerConversation> conversationMap = new HashMap<>();
    private final UUID playerUUID;
    private ConversationCallback callbacks;
    private boolean called = false;

    /**
     * Constructs a new PlayerConversation for the specified player.
     *
     * @param player the player to start the conversation with.
     */
    public PlayerConversation(Player player) {
        conversationMap.put(player, this);
        ts.getPluginManager().registerEvents(this, tp);
        this.playerUUID = player.getUniqueId();
    }

    /**
     * Starts a new conversation with the player, displaying the specified prompt message.
     *
     * @param prompt   the message to display to the player as a prompt.
     * @param callback the callback function to invoke when the player responds.
     */
    public void startConversation(String prompt, ConversationCallback callback) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            player.closeInventory();
            player.sendMessage(prompt);
        }
        callbacks = callback;
    }

    /**
     * Handles the player's response to the conversation prompt.
     *
     * @param response the player's response.
     */
    public void handleResponse(String response) {
        if (callbacks != null) {
            callbacks.onResponse(response);
            called = true;
        } else {
            sendMessage("无效的响应，请重试");
        }
    }

    /**
     * Sends a message to the player.
     *
     * @param message the message to send.
     */
    public void sendMessage(String message) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            player.sendMessage(message);
        }
    }

    /**
     * Event handler for player chat events.
     * Cancels the event and invokes the handleResponse function for the conversation.
     */
    @EventHandler(priority = LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        ChatInMC.debug.debugB("ltseed.chatinmc.PlayerInteraction.PlayerConversation.onPlayerChat called");
        if (called) return;
        Player player = event.getPlayer();
        if (conversationMap.get(player) != this) return;
        if (player.getUniqueId().equals(playerUUID)) {
            event.setCancelled(true);
            String response = event.getMessage().toLowerCase();
            handleResponse(response);
        }
    }

    /**
     * An interface defining the callback function for a conversation response.
     */
    public interface ConversationCallback {
        /**
         * Invoked when the player responds to a conversation prompt.
         *
         * @param response the player's response.
         */
        void onResponse(String response);
    }
}
