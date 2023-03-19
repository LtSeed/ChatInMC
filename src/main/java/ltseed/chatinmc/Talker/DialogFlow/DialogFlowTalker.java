package ltseed.chatinmc.Talker.DialogFlow;

import com.google.cloud.dialogflow.v2beta1.*;
import ltseed.chatinmc.Talker.Talkative;

import java.io.IOException;

/**
 A class that implements the Talkative interface to communicate with DialogFlow to chat with users.
 @author ltseed
 @version 1.0
 */
public class DialogFlowTalker implements Talkative {

    private final SessionsClient sessionsClient;

    private final String projectId;
    private final String sessionId;

    /**
     * Constructor for the DialogFlowTalker class.
     *
     * @param projectId the Google Cloud project ID associated with the DialogFlow agent
     * @param sessionId a unique identifier for the session between the user and the DialogFlow agent
     */
    public DialogFlowTalker(String projectId, String sessionId) {
        this.projectId = projectId;
        try {
            this.sessionsClient = SessionsClient.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sessionId = sessionId;
    }

    /**
     * Chat with DialogFlow using the given text message.
     *
     * @param string the text message to send to DialogFlow for processing
     * @return the response message from DialogFlow
     */
    @Override
    public String chat(String string) {
        // Set up the session ID and language code

        SessionName session = SessionName.of(this.projectId, sessionId);
        String languageCode = "zh-CN";

        // Set up the query input
        TextInput.Builder textInput = TextInput.newBuilder().setText(string).setLanguageCode(languageCode);
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        // Send the post and get the response
        DetectIntentResponse response = this.sessionsClient.detectIntent(session, queryInput);
        // Extract the response message
        return response.getQueryResult().getFulfillmentText();
    }
}
