package server;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private int port;
    private List<ConnectedClient> clients;

    Server(int port) {
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public int getPort() {
        return this.port;
    }

    public void addClient(ConnectedClient newClient) {
        for (ConnectedClient client : clients) {
            client.sendMessage("Le client " + newClient.getId() + " vient de se connecter.");
        }
        this.clients.add(newClient);
    }

    public void broadcastMessage(String message, int id) {
        for (ConnectedClient client : clients) {
            if (client.getId() != id) {
                client.sendMessage(message);
            }
        }
    }

    public void disconnectedClient(ConnectedClient discClient) {
        for(int i=0; i<this.clients.size(); i++) {
            if(clients.get(i).getId() == discClient.getId()) {
                this.clients.remove(i);
            }
            else {
                clients.get(i).sendMessage("Le client " + discClient.getId() + " s'est déconnecté.");
            }
        }
    }

}
