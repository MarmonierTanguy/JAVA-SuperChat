package com.company.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private static int idCounter = 0;
    private int id;
    private String pseudo;
    private Server server;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    ConnectedClient(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.id = idCounter;
        idCounter++;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("Nouvelle connexion, id = " + id);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean isActive = true;
        while (isActive) {
            String message = null;
            try {
                message = in.readLine();
                if (message != null) {
                    server.broadcastMessage(message, id);
                }
                else {
                    server.disconnectedClient(this);
                    isActive = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String m) {
        this.out.println(m);
        this.out.flush();
    }

    int getId() {
        return id;
    }

    public void closeClient() {
        try {
            this.socket.close();
            this.in.close();
            this.out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
