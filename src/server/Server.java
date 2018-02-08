package server;

import shared.Message;
import shared.User;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private int port;
    private List<ConnectedClient> clients;
    private User serverUser = new User("server");

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
        Message connectionMessage = new Message(serverUser,"Le client " + newClient.getUser().getUsername() + " vient de se connecter.");
        for (ConnectedClient client : clients) {
            client.sendMessage(connectionMessage);
        }
        this.clients.add(newClient);
    }

    public void broadcastMessage(Message message) {
        for (ConnectedClient client : clients) {
            if (client.getUser() != message.getSender()) {
                client.sendMessage(message);
            }
        }
    }

    public void disconnectedClient(ConnectedClient discClient) {
        Message disconnectionMessage = new Message(serverUser,"Le client " + discClient.getUser().getUsername() + " vient de se connecter.");
        for(int i=0; i<this.clients.size(); i++) {
            if(clients.get(i).getId() == discClient.getId()) {
                this.clients.remove(i);
            }
            else {
                clients.get(i).sendMessage(disconnectionMessage);
            }
        }
    }

}
