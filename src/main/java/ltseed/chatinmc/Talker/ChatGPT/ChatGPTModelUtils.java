package ltseed.chatinmc.Talker.ChatGPT;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ltseed.chatinmc.Utils.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatGPTModelUtils {

    private static final String MODELS_URL = "https://api.openai.com/v1/models";

    public static List<String> getAvailableModels(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", "Bearer " + key);
        params.put("Content-Type", "application/json");
        JSONObject response;
        if (ChatGPTCompletions.GPT_USE_PROXY) {
            response = Request.get(MODELS_URL.replace("openai", "openai-proxy"),params);
        } else response = Request.get(MODELS_URL,params);

        List<String> models = new ArrayList<>();
        if (response != null && response.containsKey("data")) {
            JSONArray data = response.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject model = data.getJSONObject(i);
                models.add(model.getString("name"));
            }
        }
        return models;
    }
}
