package ltseed.chatinmc;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

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
            for (int i = 0; i < nearbyEntities.size(); i++) {
                Entity entity = nearbyEntities.get(i);
                player.sendMessage(i+": ");
                player.sendMessage("实体类型: " + entity.getType().name());
                player.sendMessage("实体ID: " + entity.getEntityId());
                player.sendMessage("实体位置: " + entity.getLocation().toString());
                player.sendMessage("实体名称: " + entity.getName());
                player.sendMessage("");
            }

            // 提供选项
            player.sendMessage("请选择一个实体来创建一个Ai:");
            PlayerConversation pc = new PlayerConversation(player);
            //pc.startConversation();

            return true;
        }

        return false;
    }
}
