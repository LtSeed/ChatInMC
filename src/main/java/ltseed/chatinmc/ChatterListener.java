package ltseed.chatinmc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ChatterListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chatWithAI(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        for (Map.Entry<UUID, Chatter> entry : ChatInMC.chatters.entrySet()) {
            Chatter chatter = entry.getValue();
            Entity entity = chatter.getEntity();
            if(entity == null) continue;
            if(entity.getLocation().distance(location) <= chatter.getTalk_distance()){
                String m = chatter.chat(event.getMessage());
                player.sendMessage("[" + entity.getCustomName() + "] -> 你：" + m);
            }
        }
    }



}
