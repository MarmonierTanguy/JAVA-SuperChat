package com.company.client;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApp extends Application {
    private boolean choosedName = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

            /*ConnexionPanel connexionPanel = new ConnexionPanel();
            Group racine = new Group();
            racine.getChildren().add(connexionPanel);*/

            /*Scene scene1 = new Scene(racine, 500, 120);
            primaryStage.setTitle("Register");
            primaryStage.setScene(scene1);
            primaryStage.show();


           while (primaryStage.isFocused()) {
                wait();
            }
*/
            //primaryStage.close();

            ClientPanel clientPanel = new ClientPanel();
            Group root = new Group();
            root.getChildren().add(clientPanel);
            Scene scene = new Scene(root, 600, 500);
            primaryStage.setTitle("Mon chat");
            primaryStage.setScene(scene);
            primaryStage.show();
    }
}

