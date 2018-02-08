package client;


import shared.Message;
import shared.User;

import java.io.IOException;
import java.io.ObjectOutputStream;


public class ClientSend {

    private ObjectOutputStream out;

    ClientSend(ObjectOutputStream out) {
        this.out = out;
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendUser(User user){
        try {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
