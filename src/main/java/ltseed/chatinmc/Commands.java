package ltseed.chatinmc;

import lombok.NonNull;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterManageGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
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

        if (command.getName().equalsIgnoreCase("cim") && args.length > 0 && args[0].equalsIgnoreCase("manage")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("此命令只能由玩家执行！");
                return true;
            }

            Player player = (Player) sender;

            ChatterManageGUI.openManageChooseGUI(player);

            return true;
        }

        if(command.getName().equalsIgnoreCase("cim")){

            sender.sendMessage("/cim create 创建一个Chatter");
            sender.sendMessage("/cim manage 管理Chatters");
            return true;
        }
        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("cim")){
            List<String> list = new ArrayList<>();
            list.add("create");
            list.add("manage");
            return list;
        }
        return null;
    }
}
