package ltseed.chatinmc;

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


public class PlayerConversation implements Listener {
    private final UUID playerUUID;
    private final Map<String, ConversationCallback> callbacks;

    public PlayerConversation(Player player) {
        this.playerUUID = player.getUniqueId();
        this.callbacks = new HashMap<>();
    }

    public void startConversation(String prompt, ConversationCallback callback) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            ts.getPluginManager().registerEvents(new PlayerConversation(player),tp);
        }
        callbacks.put(prompt, callback);
        sendMessage(prompt);
    }

    public void handleResponse(String response) {
        ConversationCallback callback = callbacks.get(response);
        if (callback != null) {
            callbacks.remove(response);
            callback.onResponse(response);
        } else {
            sendMessage("无效的响应，请重试");
        }
    }

    public void endConversation() {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            callbacks.clear();
            player.closeInventory();
        }
    }

    public void sendMessage(String message) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            player.sendMessage(message);
        }
    }

    public interface ConversationCallback {
        void onResponse(String response);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.getUniqueId().equals(playerUUID)) {
            event.setCancelled(true);
            String response = event.getMessage().toLowerCase();
            handleResponse(response);
        }
    }
}
