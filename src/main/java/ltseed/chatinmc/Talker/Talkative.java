package ltseed.chatinmc.Talker;

/**
 An interface that defines the behavior of an object that can carry out a conversation with a person.
 */
public interface Talkative {
    /**
     Starts a conversation with the talker using the specified message.
     @param message the message to use to start the conversation
     @return the response message from the talkative object
     */
    String chat(String message);
}
