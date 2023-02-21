package ltseed.chatinmc;

import org.bukkit.ChatColor;

import java.util.function.Consumer;

public enum Debug {
    A,B,N;
    Consumer<String> info;
    public void loadDebug(Consumer<String> info){
        this.info = info;
    }

    public void info(String info){
        this.info.accept("[ChatInMC][info]"+info);
    }
    public void warn(String warn){
        this.info.accept("[ChatInMC]" + ChatColor.YELLOW + "[warn]" + warn);
    }
    public void err(String err){
        this.info.accept("[ChatInMC]" + ChatColor.RED + "[ERROR]" + err);
    }

    public void debugA(String log){
        if(this == A) info.accept("[ChatInMC]" + ChatColor.DARK_GREEN + "[DEBUG_A]" + log);
    }

    public void debugB(String log){
        if(this == B) info.accept("[ChatInMC]" + ChatColor.DARK_GREEN + "[DEBUG_B]" + log);
    }

}
