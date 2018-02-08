package server;

import shared.Message;
import shared.User;

import java.io.*;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    private static int idCounter = 0;
    private int id;
    private User user;
    private Server server;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    ConnectedClient(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        this.id = idCounter;
        this.idCounter++;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Impossible de créer le buffer/writer pour ce client : " + e);
        }
        System.out.println("Nouvelle connexion, id = " + this.id);
    }

    public int getId() {
        return this.id;
    }

    public User getUser() {
        return user;
    }

    public void sendMessage(Message message) {
        try {
            this.out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() {
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Impossible de fermer la connexion du client : " + e);
        }
    }

    @Override
    public void run() {
        boolean isActive = true;
        while (isActive) {
            Message message = null;
            try {
                Object obj = this.in.readObject();
                if (obj instanceof Message) {
                    message = (Message) obj;
                    if(message != null) {
                        this.server.broadcastMessage(message);
                    }
                    else {
                        server.disconnectedClient(this);
                        isActive = false;
                    }
                }
                else if(obj instanceof User){
                    this.user = (User) obj;
                }
            } catch (IOException e) {
                server.disconnectedClient(this);
                isActive = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
