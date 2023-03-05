package ltseed.chatinmc;

import com.google.cloud.dialogflow.v2beta1.*;
import com.google.protobuf.*;

import java.io.IOException;

public class DialogFlowTalker implements Talkative {

    private final SessionsClient sessionsClient;

    private final String projectId;

    public DialogFlowTalker(String projectId) {
        this.sessionsClient = SessionsClient.create();
        this.projectId = projectId;
    }

    @Override
    public String chat(String string) {
        try {
            // Set up the session ID and language code
            String sessionId = java.util.UUID.randomUUID().toString();
            SessionName session = SessionName.of(this.projectId, sessionId);
            String languageCode = "en-US";

            // Set up the query input
            TextInput.Builder textInput = TextInput.newBuilder().setText(string).setLanguageCode(languageCode);
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            // Send the request and get the response
            DetectIntentResponse response = this.sessionsClient.detectIntent(session, queryInput);

            // Extract the response message
            String message = response.getQueryResult().getFulfillmentText();

            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return "Sorry, an error occurred while communicating with the DialogFlow API";
        }
    }
}
