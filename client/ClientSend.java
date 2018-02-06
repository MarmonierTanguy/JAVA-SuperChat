package client;

import java.io.PrintWriter;

public class ClientSend {

    private PrintWriter out;

    ClientSend(PrintWriter out) {
        this.out = out;
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }
}
