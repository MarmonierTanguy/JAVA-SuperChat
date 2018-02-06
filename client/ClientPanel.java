package client;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import java.util.Observer;
import java.util.Scanner;

public class ClientPanel extends Parent implements Observer {

    private TextArea textToSend;
    private ScrollPane scrollReceivedText;
    private Text textConnected;
    private TextFlow receivedText;
    private Button sendBtn;
    private Button clearBtn;
    private TextArea connected;
    private Text textMembers;
    private NewMessageObservable newMessageObservable;
    private ClientSend clientSend;

    /**
     * Constructor
     */
    ClientPanel(ClientSend clientSend, NewMessageObservable newMessageObservable) {
        this.initBasicGraphs();
        this.initButtons();
        this.newMessageObservable = newMessageObservable;
        this.clientSend = clientSend;
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

                // Update newMessageObservable.
                newMessageObservable.setMessage("[moi] : " + newMessage);

                // Reset text from input.
                textToSend.setText("");

                // Send message to server.
                clientSend.sendMessage(newMessage);
            }
        });
    }

    /**
     * Private method used to print a new message on panel.
     */
    private void printNewMessage(String message) {
        Label newMessage = new Label(message);
        newMessage.setPrefWidth(400);
        newMessage.setWrapText(true);
        newMessage.setPadding(new Insets(5, 0, 0, 0));
        receivedText.getChildren().add(newMessage);
    }

    /**
     * Public method called on new message (observer method).
     */
    @Override
    public void update(java.util.Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                printNewMessage(newMessageObservable.getMessage());
            }
        });
    }
}
