package ltseed.chatinmc.Talker.ChatGPT;

import lombok.Getter;
import lombok.Setter;
import ltseed.chatinmc.ChatInMC;
import ltseed.chatinmc.Talker.MessageBuilder;
import ltseed.chatinmc.Talker.Talkative;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * This class represents a builder for creating instances of the Talkative interface
 * using OpenAI's GPT language model. It provides methods for configuring various
 * parameters that are used by the model to generate responses to user input.
 * This builder can be used to create instances of the ChatGPTCompletions or
 * ChatGPTChatUsingProxy classes, depending on whether a proxy server is being used
 * to connect to OpenAI's API or not.
 * This class implements the MessageBuilder interface and provides an implementation
 * of the build method, which takes a Player object as input and returns a Talkative
 * object that can be used to generate responses to user input.
 * This class uses Lombok to generate getters and setters for its properties, which
 * include various configuration parameters for the GPT language model, such as the
 * model name, the maximum number of tokens to generate, and the temperature parameter
 * that controls the randomness of the generated text.
 * This class also provides a basic training method that can be used to provide
 * some initial training data to the language model, in order to improve its accuracy
 * and generate more coherent responses to user input.
 *
 * @author ltseed
 * @version 1.0
 * @since 2023-03-19
 */
@Getter
@Setter
public class ChatGPTBuilder implements MessageBuilder {
    private static final Map<Player, Date> timer = new HashMap<>();
    private static final Map<Player, String> sessions = new HashMap<>();

    private static final List<String> prepareSessions = new ArrayList<>();
    private static final ChatGPTBuilder DEFAULT = new ChatGPTBuilder();
    long dialogTime = 60_000;
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
    Map<String, String> logit_bias;// = null;
    List<String> basicTrain = new ArrayList<>();

    /**
     * Constructs a new ChatGPTBuilder object with default values.
     */
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


    /**
     * Constructs a new ChatGPTBuilder object with the same parameter values as another ChatGPTBuilder object.
     *
     * @param askBuilder the ChatGPTBuilder object to copy parameter values from.
     */
    public ChatGPTBuilder(ChatGPTBuilder askBuilder) {
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
        this.basicTrain = askBuilder.basicTrain;
        prepareSessions();
    }

    public static ChatGPTBuilder getDefault() {
        return new ChatGPTBuilder(DEFAULT);
    }

    /**
     * Builds a new ChatGPT object with the specified parameters.
     * If ChatGPTCompletions.GPT_USE_PROXY is true, it creates a new session or uses an existing one and returns a ChatGPTChatUsingProxy object.
     * Otherwise, it returns a ChatGPTCompletions object.
     *
     * @param player the player that will use the ChatGPT
     * @return a Talkative object that implements the ChatGPT functionality
     */
    @Override
    public Talkative build(Player player) {
        prepareSessions();
        if (model.contains("[model]") && !ChatGPTCompletions.GPT_USE_PROXY) {
            player.sendMessage("未设定模型，使用默认值");
            model = "text-davinci-003";
        }
        if (ChatGPTCompletions.GPT_USE_PROXY) {
            Date now = new Date();
            String sessionId;
            if (player == null) return new ChatGPTChatUsingProxy(prepareSessions.remove(0));
            if (timer.containsKey(player)) {
                if (timer.get(player).getTime() - now.getTime() >= dialogTime) {
                    timer.put(player, now);
                    sessionId = prepareSessions.remove(0);
                    sessions.put(player, sessionId);
                } else {
                    timer.put(player, now);
                    sessionId = sessions.getOrDefault(player, prepareSessions.remove(0));
                    this.basicTrain(sessionId);
                }
            } else {
                timer.put(player, now);
                sessionId = prepareSessions.remove(0);
                sessions.put(player, sessionId);
                this.basicTrain(sessionId);
            }
            return new ChatGPTChatUsingProxy(sessionId);
        }
        return new ChatGPTCompletions(model, suffix, max_tokens, temperature, top_p, n, logprobs, presence_penalty, frequency_penalty, best_of, logit_bias);
    }

    /**
     * Creates new sessions if there are not enough. Each session corresponds to a new conversation.
     */
    private void prepareSessions() {
        if (prepareSessions.size() < 20) {
            String e = UUID.randomUUID().toString();
            prepareSessions.add(e);
            basicTrain(e);
        }
    }

    /**
     * Trains the model on the given text. The text will be used to generate the first response of each session.
     *
     * @param sessionId the session id to train the model on
     */
    private void basicTrain(String sessionId) {
        basicTrain.forEach(o -> {
            String chat = new ChatGPTChatUsingProxy(sessionId).chat(o);
            ChatInMC.debug.debugA(chat);
        });
    }

    public void setModel(String model) {
        if (this.model.contains("[model]"))
            this.model = this.model.replace("[model]", model);
        else this.model = model;
    }

    /**
     * Reads the configuration values for ChatGPT from a YamlConfiguration file and updates the corresponding properties.
     *
     * @param yml_file The YamlConfiguration file to read from.
     */
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
        this.basicTrain = yml_file.getStringList("Chat_GPT.basicTrain");
        prepareSessions();
    }

    /**
     * Writes the configuration values for ChatGPT to a YamlConfiguration file.
     *
     * @param yml_file The YamlConfiguration file to write to.
     */
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
        yml_file.set("Chat_GPT.basicTrain", this.basicTrain);
    }

}
