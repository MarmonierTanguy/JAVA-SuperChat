package client;

import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

public class ClientPanel extends Parent implements Runnable {

    private TextArea textToSend;
    private ScrollPane scrollReceivedText;
    private Text textConnected;
    private TextFlow receivedText;
    private Button sendBtn;
    private Button clearBtn;
    private Button refreshBtn;
    private TextArea connected;
    private Text textMembers;
    private Client client;
    private PrintWriter out;
    private BufferedReader in;
    private NewMessage newMessageGlobal;

    /**
     * Constructor
     */
    ClientPanel() {
        this.initBasicGraphs();
        this.initButtons();
        this.newMessageGlobal = new NewMessage();
    }

    /**
     * Private method used to draw basic graphs.
     */
    private void initBasicGraphs() {
        this.textToSend = new TextArea();
        this.textToSend.setLayoutX(50);
        this.textToSend.setLayoutY(350);
        this.textToSend.setPrefWidth(400);
        this.textToSend.setPrefHeight(100);
        this.scrollReceivedText = new ScrollPane();
        this.scrollReceivedText.setLayoutX(50);
        this.scrollReceivedText.setLayoutY(50);
        this.scrollReceivedText.setPrefWidth(400);
        this.scrollReceivedText.setPrefHeight(280);
        this.textConnected = new Text();
        this.textConnected.setLayoutX(50);
        this.textConnected.setLayoutY(40);
        this.textConnected.setText("Messages");
        this.receivedText = new TextFlow();
        this.receivedText.setLayoutX(50);
        this.receivedText.setLayoutY(50);
        this.receivedText.setPrefWidth(380);
        this.receivedText.setPrefHeight(250);
        this.receivedText.setVisible(true);
        this.sendBtn = new Button();
        this.sendBtn.setLayoutX(470);
        this.sendBtn.setLayoutY(350);
        this.sendBtn.setPrefWidth(100);
        this.sendBtn.setPrefHeight(30);
        this.sendBtn.setText("Envoyer");
        this.sendBtn.setVisible(true);
        this.clearBtn = new Button();
        this.clearBtn.setLayoutX(470);
        this.clearBtn.setLayoutY(385);
        this.clearBtn.setPrefWidth(100);
        this.clearBtn.setPrefHeight(30);
        this.clearBtn.setText("Effacer");
        this.clearBtn.setVisible(true);
        this.refreshBtn = new Button();
        this.refreshBtn.setLayoutX(470);
        this.refreshBtn.setLayoutY(420);
        this.refreshBtn.setPrefWidth(100);
        this.refreshBtn.setPrefHeight(30);
        this.refreshBtn.setText("Rafraichir");
        this.refreshBtn.setVisible(true);
        this.connected = new TextArea();
        this.connected.setLayoutX(470);
        this.connected.setLayoutY(50);
        this.connected.setPrefWidth(100);
        this.connected.setPrefHeight(280);
        this.connected.setEditable(false);
        this.textMembers = new Text();
        this.textMembers.setLayoutX(470);
        this.textMembers.setLayoutY(40);
        this.textMembers.setText("Connectés");
        this.scrollReceivedText.setContent(this.receivedText);
        this.scrollReceivedText.vvalueProperty().bind(this.receivedText.heightProperty());
        this.getChildren().add(this.textToSend);
        this.getChildren().add(this.scrollReceivedText);
        this.getChildren().add(this.receivedText);
        this.getChildren().add(this.textConnected);
        this.getChildren().add(this.sendBtn);
        this.getChildren().add(this.clearBtn);
        this.getChildren().add(this.refreshBtn);
        this.getChildren().add(this.connected);
        this.getChildren().add(this.textMembers);

    }

    /**
     * Private method used to init the buttons events.
     */
    private void initButtons() {
        // Init clear button.
        this.clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textToSend.setText("");
            }
        });
        // Init send button.
        this.sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // Get text from input.
                String newMessage = textToSend.getText();

                // Update new global message.
                newMessageGlobal.setMessage(newMessage);

                // Print message on panel
                printNewMessage();

                // Reset text from input.
                textToSend.setText("");

                // Send message to server.
                sendNewMessageOnServer(newMessage);
            }
        });
        // Init refresh button.
        this.refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printNewMessage();
            }
        });
    }

    /**
     * Private method used to print a new message on panel.
     */
    private void printNewMessage() {
        Label newMessage = new Label(newMessageGlobal.getMessage());
        newMessage.setPrefWidth(400);
        newMessage.setWrapText(true);
        newMessage.setPadding(new Insets(5, 0, 0, 0));
        receivedText.getChildren().add(newMessage);
    }

    /**
     * Public method used to send new message on server.
     */
    private void sendNewMessageOnServer(String message) {
        this.out.println(message);
        this.out.flush();
    }

    /**
     * Public method used to init the server properties of the client panel.
     */
    public void initServerProperties(Client client, BufferedReader in, PrintWriter out) {
        this.client = client;
        this.in = in;
        this.out = out;
    }

    /**
     * Thread used to watch for new messages.
     */
    @Override
    public void run() {
        boolean isActive = true;
        while(isActive) {
            try {
                String message = in.readLine();
                if(message != null) {
                    newMessageGlobal.setMessage(message);
                }
                else {
                    isActive = false;
                }
            }
            catch(IOException e) {
                System.out.println("Erreur lors de la réception du message : " + e);
            }
        }
        client.disconnectedServer();
    }

}
