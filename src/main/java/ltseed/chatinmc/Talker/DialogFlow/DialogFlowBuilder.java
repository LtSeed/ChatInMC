package ltseed.chatinmc.Talker.DialogFlow;

import lombok.Getter;
import ltseed.chatinmc.Talker.MessageBuilder;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class DialogFlowBuilder implements MessageBuilder {

    private static final Map<Player, Date> timer = new HashMap<>();
    private static final Map<Player, String> sessions = new HashMap<>();

    private final Long dialogTime;

    private final String projectId;

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

    public DialogFlowBuilder(String projectId,Long time){
        this.projectId = projectId;
        this.dialogTime = time;
    }
}
