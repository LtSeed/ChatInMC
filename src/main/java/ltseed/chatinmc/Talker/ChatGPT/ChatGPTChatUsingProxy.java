package ltseed.chatinmc.Talker.ChatGPT;

import com.alibaba.fastjson.JSONObject;
import ltseed.chatinmc.Talker.Talkative;
import ltseed.chatinmc.Utils.Config;
import ltseed.chatinmc.Utils.Request;

import java.util.HashMap;
import java.util.Map;

/**
 A Talkative implementation that uses the OpenAI GPT-3 API to generate text responses for a given input.
 This implementation uses a proxy server to send requests to the OpenAI API.
 The chat() method takes in a String representing the user's input and returns a String representing the generated response.
 The setKey() method allows for the API key used to authenticate requests to be updated.
 This class requires the Config.chatGPT_key field to be set with a valid API key before it can be used.
 Example usage:
 ChatGPTChatUsingProxy chatbot = new ChatGPTChatUsingProxy("session_id");
 String response = chatbot.chat("Hello!");
 @author ltseed
 @version 1.0
 */
public class ChatGPTChatUsingProxy implements Talkative {
    final Map<String, String> header = new HashMap<>();
    final Map<String, Object> params = new HashMap<>();

    /**
     * 构造方法，创建一个新的ChatGPTChatUsingProxy对象并设置sessionId和apiKey参数。
     *
     * @param sessionId 用于ChatGPT API的会话ID
     * @throws NullPointerException 如果sessionId为null
     * */
    public ChatGPTChatUsingProxy(String sessionId) {
        header.put("Content-Type","application/json; charset=utf-8");
        params.put("sessionId", sessionId);
        params.put("apiKey", Config.chatGPT_key);
    }

    /**
     * 设置apiKey参数的值。
     *
     * @param key apiKey的值
     * @throws NullPointerException 如果key为null
     * */
    public void setKey(String key){
        params.put("apiKey", key);
    }

    /**
     * 聊天方法，使用给定的输入字符串与ChatGPT API进行交互，并返回生成的文本。
     *
     * @param string 输入的字符串
     * @return 生成的文本
     * @throws NullPointerException 如果输入字符串为null
     * @throws RuntimeException 如果请求失败或生成的文本为null
     * */
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
