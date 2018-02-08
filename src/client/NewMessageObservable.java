package client;

import shared.Message;

import java.util.Observable;

public class NewMessageObservable extends Observable {

    private Message message;

    /**
     * Constructor
     */
    NewMessageObservable() {
    }

    /**
     * Public method used to get the message.
     */
    public Message getMessage() {
        return this.message;
    }

    /**
     * Public method used to set the message.
     */
    public void setMessage(Message message) {
        this.message = message;
        setChanged();
        notifyObservers(message);
    }
}