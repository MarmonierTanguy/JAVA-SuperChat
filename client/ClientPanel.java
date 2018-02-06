package com.company.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

class ClientPanel extends Parent {

    private TextArea textToSend;
    private ScrollPane scrollReceivedText;
    private TextFlow receivedText;
    private Button sendBtn;
    private Button clearBtn;
    private TextArea connected;
    private Text textMembers;

    private TextArea port;
    private TextArea address;

    private Pane connectionPanel;

    private Pane chatPannel;

    private String labelBtnSend = "Envoyer";
    private String labelBtnClear = "Effacer";

    private TextArea textPseudo;
    private Button sendPseudoBtn;
    private Text textRules;

    private String labelBtnConnect = "Connexion";
    private String labelMessageAccueil = "WELCOME TO SUPERCHAT";
    private Client client;

    ClientPanel() {

        this.textToSend = new TextArea();
        textToSend.setLayoutX(50);
        textToSend.setLayoutY(350);
        textToSend.setPrefHeight(100);
        textToSend.setPrefWidth(400);

        this.scrollReceivedText = new ScrollPane();
        scrollReceivedText.setLayoutX(50);
        scrollReceivedText.setLayoutY(50);

        this.receivedText = new TextFlow();
        receivedText.setLayoutX(50);
        receivedText.setLayoutY(50);
        receivedText.setPrefHeight(270);
        receivedText.setPrefWidth(400);
        receivedText.setVisible(true);

        this.sendBtn = new Button();
        sendBtn.setLayoutX(470);
        sendBtn.setLayoutY(350);
        sendBtn.setPrefHeight(25);
        sendBtn.setPrefWidth(100);
        sendBtn.setText(labelBtnSend);
        sendBtn.setVisible(true);

        this.clearBtn = new Button();
        clearBtn.setLayoutX(470);
        clearBtn.setLayoutY(380);
        clearBtn.setPrefHeight(25);
        clearBtn.setPrefWidth(100);
        clearBtn.setText(labelBtnClear);
        clearBtn.setVisible(true);

        this.connected = new TextArea();
        connected.setLayoutX(470);
        connected.setLayoutY(50);
        connected.setPrefHeight(270);
        connected.setPrefWidth(100);
        connected.setEditable(false);

        this.textMembers = new Text();
        textMembers.setLayoutX(470);
        textMembers.setLayoutY(50);

        scrollReceivedText.setContent(receivedText);
        scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());

        this.textPseudo = new TextArea();
        textPseudo.setLayoutX(20);
        textPseudo.setLayoutY(60);
        textPseudo.setPrefHeight(25);
        textPseudo.setPrefWidth(165);
        textPseudo.setPromptText("Pseudo");

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

        this.sendPseudoBtn = new Button();
        sendPseudoBtn.setLayoutX(55);
        sendPseudoBtn.setLayoutY(100);
        sendPseudoBtn.setPrefHeight(25);
        sendPseudoBtn.setPrefWidth(100);
        sendPseudoBtn.setText(labelBtnConnect);
        sendPseudoBtn.setVisible(true);

        this.textRules = new Text();
        textRules.setLayoutX(35);
        textRules.setLayoutY(15);
        textRules.setText(labelMessageAccueil);
        textRules.setTextAlignment(TextAlignment.CENTER);



        sendPseudoBtn.setOnAction(event ->{
            connectionPanel.setVisible(false);
            chatPannel.setVisible(true);
            this.client = new Client(address.getText(),Integer.parseInt(port.getText()));//,this)
        } );

        chatPannel = new Pane();
        connectionPanel = new Pane();


        chatPannel.getChildren().add(receivedText);
        chatPannel.getChildren().add(connected);
        chatPannel.getChildren().add(textMembers);
        chatPannel.getChildren().add(scrollReceivedText);
        chatPannel.getChildren().add(textToSend);
        chatPannel.getChildren().add(clearBtn);
        chatPannel.getChildren().add(sendBtn);

        connectionPanel.getChildren().add(textPseudo);
        connectionPanel.getChildren().add(textRules);
        connectionPanel.getChildren().add(sendPseudoBtn);
        connectionPanel.getChildren().add(port);
        connectionPanel.getChildren().add(address);


        connectionPanel.setLayoutX(200);
        connectionPanel.setLayoutY(150);
        chatPannel.setVisible(false);
        this.getChildren().add(chatPannel);
        this.getChildren().add(connectionPanel);
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textToSend.clear();
            }
        });

        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String textSendable = textToSend.getText();
                if (!textSendable.isEmpty()) {
                    Label labToAdd = new Label(textToSend.getText());
                    labToAdd.setPrefWidth(270);
                    receivedText.getChildren().add(labToAdd);
                    textToSend.clear();
                }
            }
        });
    }



}
