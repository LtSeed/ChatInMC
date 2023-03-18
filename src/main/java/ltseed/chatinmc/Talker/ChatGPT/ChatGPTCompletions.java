package ltseed.chatinmc.Talker.ChatGPT;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ltseed.chatinmc.Talker.Talkative;
import ltseed.chatinmc.Utils.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ltseed.chatinmc.Utils.Request.post;

public class ChatGPTCompletions implements Talkative {

    public static final boolean GPT_USE_PROXY = true;
    private final String model;
    private final String suffix;// = null;
    private final int max_tokens;// = 512;
    private final double temperature;// = 1;
    private final int top_p;// = 1;
    private final int n;// = 1;
    private final Integer logprobs;// = null;
    private final double presence_penalty;// = 0;
    private final double frequency_penalty;// = 0;
    private final int best_of;// = 1;
    private final Map<String,String> logit_bias;// = null;

    protected ChatGPTCompletions(String model, String suffix, int max_tokens, double temperature, int top_p, int n, Integer logprobs, double presence_penalty, double frequency_penalty, int best_of, Map<String, String> logit_bias) {
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

    public String ask(String prompt,String key) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("Authorization","Bearer " + key);
        stringStringHashMap.put("Content-Type","application/json");
        HashMap<String, Object> params = new HashMap<>();
        ChatGPTBuilder aDefault = ChatGPTBuilder.getDefault();
        params.put("prompt",prompt);
        if(!Objects.equals(this.model, aDefault.model)) params.put("model", this.model);
        if(!Objects.equals(this.suffix, aDefault.suffix)) params.put("suffix", this.suffix);
        if(!Objects.equals(this.max_tokens, 16)) params.put("max_tokens", this.max_tokens);
        if(!Objects.equals(this.temperature, aDefault.temperature)) params.put("temperature", this.temperature);
        if(!Objects.equals(this.top_p, aDefault.top_p)) params.put("top_p", this.top_p);
        if(!Objects.equals(this.n, aDefault.n)) params.put("n", this.n);
        if(!Objects.equals(this.logprobs, aDefault.logprobs)) params.put("logprobs", this.logprobs);
        if(!Objects.equals(this.presence_penalty, aDefault.presence_penalty)) params.put("presence_penalty", this.presence_penalty);
        if(!Objects.equals(this.frequency_penalty, aDefault.frequency_penalty)) params.put("frequency_penalty", this.frequency_penalty);
        if(!Objects.equals(this.best_of, aDefault.best_of)) params.put("best_of", this.best_of);
        if(!Objects.equals(this.logit_bias, aDefault.logit_bias)) params.put("logit_bias", this.logit_bias);
        System.out.println(params);
        String url = "https://api.openai.com/v1/completions";
        if(GPT_USE_PROXY) url = url.replace("openai","openai-proxy");
        JSONObject request = post(url, stringStringHashMap, params);
        System.out.println(request);
        if(request == null) return null;
        JSONArray choices = request.getJSONArray("choices");
        JSONObject text = choices.getJSONObject(0);
        return text.getString("text");
    }

    @Override
    public String chat(String string) {
        return ask(string, Config.chatGPT_key);
    }
}
