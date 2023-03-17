package ltseed.chatinmc.Talker.ChatGPT;

import lombok.Getter;
import lombok.Setter;
import ltseed.chatinmc.Talker.MessageBuilder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

public class ChatGPTBuilder implements MessageBuilder {
    private static final ChatGPTBuilder DEFAULT = new ChatGPTBuilder("[model]",null,512,1,1,1,null,0,0,1,null);
    //public static final ChatGPTBuilder CONTENT_FILTER = new ChatGPTBuilder("[model]","<|endoftext|>[prompt]\n--\nLabel:",null,1,0,0,1,10,0,0,1,null);
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
    public ChatGPTCompletions build(Player ignore){
        if(model.contains("[model]"))return null;
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

    protected ChatGPTBuilder(String model, String suffix, int max_tokens, int temperature, int top_p, int n, Integer logprobs, double presence_penalty, double frequency_penalty, int best_of, Map<String, String> logit_bias) {
        this.model = model;
        this.suffix = suffix;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
        this.top_p = top_p;
        this.n = n;
        this.logprobs = logprobs;
        this.presence_penalty = presence_penalty;
        this.frequency_penalty = frequency_penalty;
        this.best_of = best_of;
        this.logit_bias = logit_bias;
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
    }



}
