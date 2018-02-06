package client;

import java.util.Observable;

public class NewMessageObservable extends Observable {

    private String message;

    /**
     * Constructor
     */
    NewMessageObservable() {
        this.message = "";
    }

    /**
     * Public method used to get the message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Public method used to set the message.
     */
    public void setMessage(String message) {
        this.message = message;
        setChanged();
        notifyObservers(message);
    }
}