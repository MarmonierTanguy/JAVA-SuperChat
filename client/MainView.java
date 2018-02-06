package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Init panel.
        Group root = new Group();
        ClientPanel clientPanel = new ClientPanel();
        root.getChildren().add(clientPanel);
        primaryStage.setTitle("SuperChat");
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Init client.
        String address = "localhost";
        Integer port = new Integer(8888);
        Client client = new Client(address, port, clientPanel);

    }
}