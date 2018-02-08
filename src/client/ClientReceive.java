package client;

import shared.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceive implements Runnable {

    private Client client;
    private ObjectInputStream in;
    private NewMessageObservable newMessageObservable;

    ClientReceive(Client client, ObjectInputStream in, NewMessageObservable newMessageObservable) {
        this.client = client;
        this.in = in;
        this.newMessageObservable = newMessageObservable;
    }

    @Override
    public void run() {
        boolean isActive = true;
        while (isActive) {
            try {
                Message message = (Message) in.readObject();
                if (message != null) {
                    System.out.println("\nNouveau message reçu : " + message.getText());
                    this.newMessageObservable.setMessage(message);
                } else {
                    isActive = false;
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la réception du message : " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        client.disconnectedServer();
    }

}
