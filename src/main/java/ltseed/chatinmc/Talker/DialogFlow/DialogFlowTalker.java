package ltseed.chatinmc.Talker.DialogFlow;

import com.google.cloud.dialogflow.v2beta1.*;
import ltseed.chatinmc.Talker.Talkative;
import ltseed.chatinmc.Utils.Config;

import java.io.IOException;

public class DialogFlowTalker implements Talkative {

    private final SessionsClient sessionsClient;

    private final String projectId;
    private final String sessionId;

    public DialogFlowTalker(String sessionId) {
        projectId = Config.getDialogFlowProjectID();
        try {
            this.sessionsClient = SessionsClient.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sessionId = sessionId;
    }

    @Override
    public String chat(String string) {
        // Set up the session ID and language code

        SessionName session = SessionName.of(this.projectId, sessionId);
        String languageCode = "zh-CN";

        // Set up the query input
        TextInput.Builder textInput = TextInput.newBuilder().setText(string).setLanguageCode(languageCode);
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        // Send the request and get the response
        DetectIntentResponse response = this.sessionsClient.detectIntent(session, queryInput);

        // Extract the response message
        String message = response.getQueryResult().getFulfillmentText();

        return message;
    }
}
