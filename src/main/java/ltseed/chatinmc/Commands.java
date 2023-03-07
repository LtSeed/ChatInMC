package ltseed.chatinmc;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("c") && args.length > 0 && args[0].equalsIgnoreCase("create")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("此命令只能由玩家执行！");
                return true;
            }

            Player player = (Player) sender;
            //Location playerLocation = player.getLocation();

            // 获取玩家周围10格的实体
            List<Entity> nearbyEntities = player.getNearbyEntities(10, 10, 10);

            // 打印实体信息
            player.sendMessage("附近的实体:");
            for (int i = 1; i <= nearbyEntities.size(); i++) {
                Entity entity = nearbyEntities.get(i);
                player.sendMessage(i+": ");
                player.sendMessage("实体类型: " + entity.getType().name());
                player.sendMessage("实体ID: " + entity.getEntityId());
                player.sendMessage("实体位置: " + entity.getLocation().toString());
                player.sendMessage("实体名称: " + entity.getName());
                player.sendMessage("");
            }

            // 提供选项

            PlayerConversation pc = new PlayerConversation(player);
            pc.startConversation("请选择一个实体来创建一个Ai:(输入数字)",str -> {
                Entity entity = nearbyEntities.get(Integer.parseInt(str)-1);
                UUID uuid = entity.getUniqueId();
                Chatter c = new Chatter(uuid,100,60*1000L);
            });

            return true;
        }

        return false;
    }
}
