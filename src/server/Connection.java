package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {

    private Server server;
    private ServerSocket serverSocket;

    Connection(Server server) {
        this.server = server;
        try {
            this.serverSocket = new ServerSocket(server.getPort());
        } catch (IOException e) {
            System.out.println("Impossible de créer une nouvelle connexion serveur : " + e);
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("Attente de connexion...");
                Socket socketNewClient = this.serverSocket.accept();
                ConnectedClient newClient = new ConnectedClient(server, socketNewClient);
                server.addClient(newClient);
                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();
            } catch (IOException e) {
                System.out.println("Impossible de créer une nouvelle connexion client : " + e);
            }
        }
    }

}
