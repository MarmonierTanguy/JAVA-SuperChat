package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private String address;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientPanel clientPanel;

    /**
     * Constructor
     */
    Client(String address, int port, ClientPanel clientPanel) {
        this.address = address;
        this.port = port;
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Impossible d'ouvrir la connexion avec le serveur : " + e);
        }
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Impossible de créer le buffer/writer : " + e);
        }
        this.clientPanel = clientPanel;
        this.clientPanel.initServerProperties(this, this.in, this.out);
        Thread threadClientPanel = new Thread(this.clientPanel);
        threadClientPanel.start();
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
