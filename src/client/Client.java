package client;

import java.io.*;
import java.net.Socket;

public class Client {

    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ClientSend clientSend;
    private ClientReceive clientReceive;
    private NewMessageObservable newMessageObservable;

    /**
     * Constructor
     */
    Client(String address, int port, NewMessageObservable newMessageObservable) {
        this.address = address;
        this.port = port;
        this.newMessageObservable = newMessageObservable;
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Impossible d'ouvrir la connexion avec le serveur : " + e);
        }
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Impossible de créer le buffer/writer : " + e);
        }
        this.clientSend = new ClientSend(this.out);
        this.clientReceive = new ClientReceive(this, this.in, this.newMessageObservable);
        Thread threadClientSend = new Thread(this.clientReceive);
        threadClientSend.start();
    }

    /**
     * Public method used to get the clientSend.
     */
    public ClientSend getClientSend() {
        return clientSend;
    }

    /**
     * Public method used to disconnect the server.
     */
    public void disconnectedServer() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Impossible de fermer la connexion : " + e);
        }
        System.exit(0);
    }

}
