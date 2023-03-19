package ltseed.chatinmc.Talker.DialogFlow;

import lombok.Getter;
import ltseed.chatinmc.Talker.MessageBuilder;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A builder class for creating instances of `DialogFlowTalker`.
 @author ltseed
 @version 1.0
 */
@Getter
public class DialogFlowBuilder implements MessageBuilder {

    /**
     * A map of players and the last time they engaged in a dialog.
     */
    private static final Map<Player, Date> timer = new HashMap<>();

    /**
     * A map of players and their current session IDs.
     */
    private static final Map<Player, String> sessions = new HashMap<>();

    /**
     * The maximum time between dialogs in milliseconds.
     */
    private final Long dialogTime;

    /**
     * The ID of the Dialogflow project to use.
     */
    private final String projectId;


    /**
     * Builds a new instance of `DialogFlowTalker` for the specified player.
     *
     * @param player the player to build the `DialogFlowTalker` instance for
     * @return a new instance of `DialogFlowTalker` for the specified player
     */
    @Override
    public DialogFlowTalker build(Player player) {
        Date now = new Date();
        String sessionId;
        if(player == null) return null;
        if(timer.containsKey(player)){
            if(timer.get(player).getTime() - now.getTime() >= dialogTime){
                timer.put(player,now);
                sessionId = UUID.randomUUID().toString();
                sessions.put(player, sessionId);
            } else {
                timer.put(player,now);
                sessionId = sessions.getOrDefault(player,UUID.randomUUID().toString());
            }
        } else {
            timer.put(player,now);
            sessionId = UUID.randomUUID().toString();
            sessions.put(player, sessionId);
        }
        return new DialogFlowTalker(projectId,sessionId);
    }


    /**
     * Creates a new `DialogFlowBuilder` instance with the specified project ID and dialog time.
     *
     * @param projectId  the ID of the Dialogflow project to use
     * @param dialogTime the maximum time between dialogs in milliseconds
     */
    public DialogFlowBuilder(String projectId,Long dialogTime){
        this.projectId = projectId;
        this.dialogTime = dialogTime;
    }
}
