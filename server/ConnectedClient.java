package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    private static int idCounter = 0;
    private int id;
    private Server server;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    ConnectedClient(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        this.id = idCounter;
        this.idCounter++;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Impossible de créer le buffer/writer pour ce client : " + e);
        }
        System.out.println("Nouvelle connexion, id = " + this.id);
    }

    public int getId() {
        return this.id;
    }

    public void sendMessage(String message) {
        this.out.println(message);
        this.out.flush();
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
            String message = null;
            try {
                message = this.in.readLine();
            } catch (IOException e) {
                server.disconnectedClient(this);
                isActive = false;
            }
            if(message != null) {
                this.server.broadcastMessage(message, id);
            }
            else {
                server.disconnectedClient(this);
                isActive = false;
            }
        }
    }
}
