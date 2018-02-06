package client;

import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceive implements Runnable{

    private Client client;
    private BufferedReader in;
    private NewMessageObservable newMessageObservable;

    ClientReceive(Client client, BufferedReader in, NewMessageObservable newMessageObservable) {
        this.client = client;
        this.in = in;
        this.newMessageObservable = newMessageObservable;
    }

    @Override
    public void run() {
        boolean isActive = true;
        while(isActive) {
            try {
                String message = in.readLine();
                if(message != null) {
                    System.out.println("\nNouveau message reçu : " + message);
                    this.newMessageObservable.setMessage(message);
                }
                else {
                    isActive = false;
                }
            }
            catch(IOException e) {
                System.out.println("Erreur lors de la réception du message : " + e);
            }
        }
        client.disconnectedServer();
    }

}
