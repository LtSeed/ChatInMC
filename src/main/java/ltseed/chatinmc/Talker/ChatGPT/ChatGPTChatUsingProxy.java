package ltseed.chatinmc.Talker.ChatGPT;

import com.alibaba.fastjson.JSONObject;
import ltseed.chatinmc.Talker.Talkative;
import ltseed.chatinmc.Utils.Config;
import ltseed.chatinmc.Utils.Request;

import java.util.HashMap;
import java.util.Map;

public class ChatGPTChatUsingProxy implements Talkative {
    final Map<String, String> header = new HashMap<>();
    final Map<String, Object> params = new HashMap<>();

    public ChatGPTChatUsingProxy(String sessionId) {
        header.put("Content-Type","application/json; charset=utf-8");
        params.put("sessionId", sessionId);
        params.put("apiKey", Config.chatGPT_key);
    }

    public void setKey(String key){
        params.put("apiKey", key);
    }

    /**
     */
    @Override
    public String chat(String string) {
        params.put("content", string);
        JSONObject post = Request.post("https://api.openai-proxy.com/v1/chat/completions", header, params);
        if (post != null) {
            return post.getString("data");
        }
        return null;
    }
}
