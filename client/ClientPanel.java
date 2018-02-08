package client;

import javafx.application.Platform;
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

import java.util.Observer;

public class ClientPanel extends Parent implements Observer {


    private Pane chatPannel;

    private TextArea textToSend;
    private ScrollPane scrollReceivedText;
    private Text connectedText;
    private TextFlow receivedText;
    private Button sendBtn;
    private Button clearBtn;
    private TextArea connected;
    private Text membersText;

    private Pane connectionPanel;

    private TextArea port;
    private TextArea address;
    private TextArea usernameText;
    private Button connectBtn;
    private Text welcomText;
    private String btnConnectLabel = "Connexion";
    private String messageAcceuilLabel = "WELCOME TO SUPERCHAT";

    private Client client;
    private NewMessageObservable newMessageObservable;
    private ClientSend clientSend;
    private String username;

    /**
     * Constructor
     */
    ClientPanel(NewMessageObservable newMessageObservable) {
        this.initBasicGraphs();
        this.initButtons();
        this.newMessageObservable = newMessageObservable;
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

        this.connectedText = new Text();
        this.connectedText.setLayoutX(50);
        this.connectedText.setLayoutY(40);
        this.connectedText.setText("Messages");

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

        this.membersText = new Text();
        this.membersText.setLayoutX(470);
        this.membersText.setLayoutY(40);
        this.membersText.setText("Connectés");

        this.scrollReceivedText.setContent(this.receivedText);
        this.scrollReceivedText.vvalueProperty().bind(this.receivedText.heightProperty());

        this.usernameText = new TextArea();
        usernameText.setLayoutX(20);
        usernameText.setLayoutY(60);
        usernameText.setPrefHeight(25);
        usernameText.setPrefWidth(165);
        usernameText.setPromptText("Pseudo");

        this.port = new TextArea();
        port.setLayoutX(125);
        port.setLayoutY(20);
        port.setPrefHeight(25);
        port.setPrefWidth(60);
        port.setPromptText("Port");

        this.address = new TextArea();
        address.setLayoutX(20);
        address.setLayoutY(20);
        address.setPrefHeight(25);
        address.setPrefWidth(100);
        address.setPromptText("Address");

        this.connectBtn = new Button();
        connectBtn.setLayoutX(55);
        connectBtn.setLayoutY(100);
        connectBtn.setPrefHeight(25);
        connectBtn.setPrefWidth(100);
        connectBtn.setText(btnConnectLabel);
        connectBtn.setVisible(true);

        this.welcomText = new Text();
        welcomText.setLayoutX(35);
        welcomText.setLayoutY(15);
        welcomText.setText(messageAcceuilLabel);
        welcomText.setTextAlignment(TextAlignment.CENTER);

        chatPannel = new Pane();
        connectionPanel = new Pane();


        chatPannel.getChildren().add(receivedText);
        chatPannel.getChildren().add(connected);
        chatPannel.getChildren().add(membersText);
        chatPannel.getChildren().add(scrollReceivedText);
        chatPannel.getChildren().add(textToSend);
        chatPannel.getChildren().add(clearBtn);
        chatPannel.getChildren().add(sendBtn);

        connectionPanel.getChildren().add(usernameText);
        connectionPanel.getChildren().add(welcomText);
        connectionPanel.getChildren().add(connectBtn);
        connectionPanel.getChildren().add(port);
        connectionPanel.getChildren().add(address);


        connectionPanel.setLayoutX(200);
        connectionPanel.setLayoutY(150);
        chatPannel.setVisible(false);
        this.getChildren().add(chatPannel);
        this.getChildren().add(connectionPanel);
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
                newMessageObservable.setMessage( username" : " + newMessage);

                // Reset text from input.
                textToSend.setText("");

                // Send message to server.
                clientSend.sendMessage(newMessage);
            }
        });
        //Init connectionButton
        this.connectBtn.setOnAction(event ->{
            connectionPanel.setVisible(false);
            chatPannel.setVisible(true);
            this.client = new Client(address.getText(),Integer.parseInt(port.getText()));
            this.clientSend = this.client.getClientSend();
            this.username = usernameText.getText();
        } );
    }

    /**
     * Private method used to print a new message on panel.
     */
    private void printNewMessage(String message) {
        Calendar now = Calendar.getInstance();
        System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        Label newMessage = new Label("[" + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + "] " + message);
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
