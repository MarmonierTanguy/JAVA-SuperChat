package com.company.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client {
    private String address;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    Client(String address, int port) {
        this.address = address;
        this.port = port;
        try {
            this.socket = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Thread threadClientSend = new Thread(new ClientSend(this.out));
        threadClientSend.start();
        Thread threadClientReceive = new Thread(new ClientReceive(this, this.in));
        threadClientReceive.start();
    }

  void disconnectedServer() {
      try {
          in.close();
          out.close();
          socket.close();
      }
      catch (IOException e) {
          e.printStackTrace();
      }
      System.exit(0);
  }
}
