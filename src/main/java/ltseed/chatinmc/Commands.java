package ltseed.chatinmc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!label.equals("c")){
            return true;}

            if(args != null){
                if(args.length == 2){
                    if(args[0].equals("create")){
                        String name = args[1];
                    }
                }
            }

        return false;
    }
}
