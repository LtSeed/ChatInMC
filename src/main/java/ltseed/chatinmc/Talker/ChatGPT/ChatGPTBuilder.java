package ltseed.chatinmc.Talker.ChatGPT;

import lombok.Getter;
import lombok.Setter;
import ltseed.chatinmc.Talker.MessageBuilder;
import ltseed.chatinmc.Talker.Talkative;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ChatGPTBuilder implements MessageBuilder {
    private static final Map<Player, Date> timer = new HashMap<>();
    private static final Map<Player, String> sessions = new HashMap<>();

    long dialogTime = 60_000;
    private static final ChatGPTBuilder DEFAULT = new ChatGPTBuilder();
    String model;
    String suffix;// = null;
    int max_tokens;// = 512;
    double temperature;// = 1;
    int top_p;// = 1;
    int n;// = 1;
    Integer logprobs;// = null;
    double presence_penalty;// = 0;
    double frequency_penalty;// = 0;
    int best_of;// = 1;
    Map<String,String> logit_bias;// = null;
    @Override
    public Talkative build(Player player){
        if(model.contains("[model]") && !ChatGPTCompletions.GPT_USE_PROXY) {
            player.sendMessage("未设定模型，使用默认值");
            model = "text-davinci-003";
        }
        if(ChatGPTCompletions.GPT_USE_PROXY){
            Date now = new Date();
            String sessionId;
            if(player == null) return new ChatGPTChatUsingProxy(UUID.randomUUID().toString());
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
            return new ChatGPTChatUsingProxy(sessionId);
        }
        return new ChatGPTCompletions(model,suffix,max_tokens,temperature,top_p,n,logprobs,presence_penalty,frequency_penalty,best_of,logit_bias);
    }

    public static ChatGPTBuilder getDefault(){
        return new ChatGPTBuilder(DEFAULT);
    }


    public void setModel(String model) {
        if(this.model.contains("[model]"))
            this.model = this.model.replace("[model]",model);
        else this.model = model;
    }

    protected ChatGPTBuilder() {
        this.model = "[model]";
        this.suffix = null;
        this.max_tokens = 512;
        this.temperature = 1;
        this.top_p = 1;
        this.n = 1;
        this.logprobs = null;
        this.presence_penalty = 0;
        this.frequency_penalty = 0;
        this.best_of = 1;
        this.logit_bias = null;
    }

    public ChatGPTBuilder(ChatGPTBuilder askBuilder){
        this.model = askBuilder.model;
        this.suffix = askBuilder.suffix;
        this.max_tokens = askBuilder.max_tokens;
        this.temperature = askBuilder.temperature;
        this.top_p = askBuilder.top_p;
        this.n = askBuilder.n;
        this.logprobs = askBuilder.logprobs;
        this.presence_penalty = askBuilder.presence_penalty;
        this.frequency_penalty = askBuilder.frequency_penalty;
        this.best_of = askBuilder.best_of;
        if (askBuilder.logit_bias != null)
            this.logit_bias = new HashMap<>(askBuilder.logit_bias);
        else this.logit_bias = null;
        this.dialogTime = askBuilder.dialogTime;
    }


    public void readFile(YamlConfiguration yml_file) {
        this.model = yml_file.getString("Chat_GPT.model");
        this.suffix = yml_file.getString("Chat_GPT.suffix");
        this.max_tokens = yml_file.getInt("Chat_GPT.max_tokens");
        this.temperature = yml_file.getDouble("Chat_GPT.temperature");
        this.top_p = yml_file.getInt("Chat_GPT.top_p");
        this.n = yml_file.getInt("Chat_GPT.n");
        this.logprobs = yml_file.getInt("Chat_GPT.logprobs");
        this.presence_penalty = yml_file.getDouble("Chat_GPT.presence_penalty");
        this.frequency_penalty = yml_file.getDouble("Chat_GPT.frequency_penalty");
        this.best_of = yml_file.getInt("Chat_GPT.best_of");
    }

    public void writeFile(YamlConfiguration yml_file) {
        yml_file.set("Chat_GPT.model", this.model);
        yml_file.set("Chat_GPT.suffix", this.suffix);
        yml_file.set("Chat_GPT.max_tokens", this.max_tokens);
        yml_file.set("Chat_GPT.temperature", this.temperature);
        yml_file.set("Chat_GPT.top_p", this.top_p);
        yml_file.set("Chat_GPT.n", this.n);
        yml_file.set("Chat_GPT.logprobs", this.logprobs);
        yml_file.set("Chat_GPT.presence_penalty", this.presence_penalty);
        yml_file.set("Chat_GPT.frequency_penalty", this.frequency_penalty);
        yml_file.set("Chat_GPT.best_of", this.best_of);
    }

}
