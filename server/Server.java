package com.company.server;

import java.util.ArrayList;
import java.util.List;

class Server {

    private int port;
    private List<ConnectedClient> clients;

    int getPort() {
        return port;
    }

    Server(int port) {
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();

        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    void addClient(ConnectedClient newClient) {
        for (ConnectedClient client : clients) {
            client.sendMessage("Le client "+newClient.getId()+" vient de se connecter");
        }
        this.clients.add(newClient);
    }

    void broadcastMessage(String m, int id) {
        for (ConnectedClient client : clients) {
            if (client.getId() != id) {
                client.sendMessage("Message de "+id+" : " + m);
            }
        }
    }

    void disconnectedClient(ConnectedClient discClient) {
        for (ConnectedClient client : clients) {
            client.sendMessage("Le client " + discClient.getId() + " nous a quitté");
        }
    }
}
