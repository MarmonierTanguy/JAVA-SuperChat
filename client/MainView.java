package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Init message observable (used to update the gui).
        NewMessageObservable newMessageObservable = new NewMessageObservable();

        // Init panel.
        Group root = new Group();
        ClientPanel clientPanel = new ClientPanel(newMessageObservable);
        root.getChildren().add(clientPanel);
        primaryStage.setTitle("SuperChat");
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Init observer/observable.
        newMessageObservable.addObserver(clientPanel);

    }
}