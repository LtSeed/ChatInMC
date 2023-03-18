package ltseed.chatinmc.Utils;

import org.bukkit.ChatColor;

import java.util.function.Consumer;

public enum Debug {
    @SuppressWarnings("unused") A,B,N;
    Consumer<String> info;
    public void loadDebug(Consumer<String> info){
        this.info = info;
    }

    public void info(String info){
        this.info.accept("[info]"+info);
    }
    public void warn(String warn){
        this.info.accept(ChatColor.YELLOW + "[warn]" + warn);
    }
    public void err(String err){
        this.info.accept(ChatColor.RED + "[ERROR]" + err);
    }

    public void debugA(String log){
        if(this != N) info.accept(ChatColor.GREEN + "[DEBUG_A]" + log);
    }

    public void debugB(String log){
        if(this == B) info.accept(ChatColor.BLUE+ "[DEBUG_B]" + log);
    }

}
