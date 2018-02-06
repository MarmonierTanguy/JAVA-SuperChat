package com.company.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceive implements Runnable{
    private Client client;
    private BufferedReader in;

    ClientReceive(Client client, BufferedReader in) {
        this.client = client;
        this.in = in;
    }

    @Override
    public void run() {
        boolean isActive = true ;
        while(isActive) {
            String message = null;
            try {
                message = in.readLine();
                if (message != null) {
                    System.out.println("\nMessage reçu : " + message);
                }
                else {
                    isActive = false;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.disconnectedServer();

    }
}
