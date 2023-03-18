package ltseed.chatinmc;

import lombok.NonNull;
import ltseed.chatinmc.PlayerInteraction.GUI.Button;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterCreateGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.FinalGUI.ChatterManageGUI;
import ltseed.chatinmc.PlayerInteraction.GUI.PaginatedGUI;
import ltseed.chatinmc.Talker.Chatter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

        if (command.getName().equalsIgnoreCase("cim") && args.length > 0 && args[0].equalsIgnoreCase("manage")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("此命令只能由玩家执行！");
                return true;
            }

            Player player = (Player) sender;

            ArrayList<Button> allButtons = new ArrayList<>();

            for (Chatter value : ChatInMC.chatters.values()) {
                List<String> d = new ArrayList<>();
                value.describe().forEach((k,v) -> d.add(k+": "+v));
                allButtons.add(new Button(0, Material.WRITABLE_BOOK, value.getName(), d) {
                    @Override
                    public void call(Player player) {
                        ChatterManageGUI.getInstance(player,value).open(player);
                    }
                });
            }

            new PaginatedGUI("选择你要修改的Chatter!", allButtons).open(player,1);

            return true;
        }
        return false;
    }
}
