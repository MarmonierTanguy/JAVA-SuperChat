package client;

import javafx.beans.InvalidationListener;

public class NewMessage {

    private String message;

    NewMessage() {
        this.message = "";
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
