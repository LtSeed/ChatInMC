package ltseed.chatinmc.PlayerInteraction;

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
import static org.bukkit.event.EventPriority.HIGH;


public class PlayerConversation implements Listener {

    //一个玩家同时只能进行一个对话。
    public static final Map<Player, PlayerConversation> conversationMap = new HashMap<>();
    private final UUID playerUUID;
    private ConversationCallback callbacks;
    private boolean called = false;

    public PlayerConversation(Player player) {
        conversationMap.put(player,this);
        ts.getPluginManager().registerEvents(this,tp);
        this.playerUUID = player.getUniqueId();
    }

    public void startConversation(String prompt, ConversationCallback callback) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            player.closeInventory();
            player.sendMessage(prompt);
        }
        callbacks = callback;
    }

    public void handleResponse(String response) {
        if (callbacks != null) {
            callbacks.onResponse(response);
            called = true;
        } else {
            sendMessage("无效的响应，请重试");
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

    @EventHandler(priority = HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(called) return;
        Player player = event.getPlayer();
        if(conversationMap.get(player) != this)return;
        if (player.getUniqueId().equals(playerUUID)) {
            event.setCancelled(true);
            String response = event.getMessage().toLowerCase();
            handleResponse(response);
        }
    }
}
