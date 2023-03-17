package ltseed.chatinmc;

import lombok.NonNull;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, Command command, @NonNull String label, @SuppressWarnings("NullableProblems") String[] args) {

        if (command.getName().equalsIgnoreCase("cim") && args.length > 0 && args[0].equalsIgnoreCase("create")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("此命令只能由玩家执行！");
                return true;
            }

            Player player = (Player) sender;
            ChatterCreateGUI.getInstance(player).open(player);

            return true;
        }

        return false;
    }
}
