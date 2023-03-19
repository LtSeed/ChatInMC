package ltseed.chatinmc.Utils;

import org.bukkit.ChatColor;

import java.util.function.Consumer;

/**

 An enum representing different levels of debug output, including A, B, and N (none).
 Each enum constant has methods for outputting information, warnings, and errors at its respective level.
 Additionally, there are methods for debugging at levels A and B.
 @author ltseed
 @version 1.0
 */
public enum Debug {
    @SuppressWarnings("unused")
    A,
    B,
    N;
    Consumer<String> info;
    /**
     * Sets the output consumer for this debug level.
     *
     * @param info The consumer to set.
     */
    public void loadDebug(Consumer<String> info){
        this.info = info;
    }

    /**
     * Outputs an informational message at this debug level.
     *
     * @param info The message to output.
     */
    public void info(String info){
        this.info.accept("[info]"+info);
    }

    /**
     * Outputs a warning message at this debug level.
     *
     * @param warn The warning message to output.
     */
    public void warn(String warn){
        this.info.accept(ChatColor.YELLOW + "[warn]" + warn);
    }

    /**
     * Outputs an error message at this debug level.
     *
     * @param err The error message to output.
     */
    public void err(String err){
        this.info.accept(ChatColor.RED + "[ERROR]" + err);
    }

    /**
     * Outputs a message for debugging at level A.
     *
     * @param log The message to output.
     */
    public void debugA(String log){
        if(this != N) info.accept(ChatColor.GREEN + "[DEBUG_A]" + log);
    }

    /**
     * Outputs a message for debugging at level B.
     *
     * @param log The message to output.
     */
    public void debugB(String log){
        if(this == B) info.accept(ChatColor.BLUE+ "[DEBUG_B]" + log);
    }

}
