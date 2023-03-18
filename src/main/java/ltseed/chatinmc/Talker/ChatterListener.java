package ltseed.chatinmc.Talker;

import ltseed.chatinmc.ChatInMC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;

public class ChatterListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void chatWithAI(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        Location location = player.getLocation();
        for (Map.Entry<UUID, Chatter> entry : ChatInMC.chatters.entrySet()) {
            final Chatter chatter = entry.getValue();
            Entity entity = chatter.getEntity();
            if(entity == null) continue;
            if(entity.getLocation().distance(location) <= chatter.getTalk_distance()){
                new Thread(()->{
                    String m = chatter.getCore().build(player).chat(event.getMessage());
                    player.sendMessage("[" + chatter.getName() + "] -> 你：" + m);
                }).start();
            }
        }
    }



}
