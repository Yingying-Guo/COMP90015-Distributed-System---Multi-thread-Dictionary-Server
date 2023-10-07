package model.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;


public class ClientApplication extends Application {

    public static String[] args;

    public static Client client;

    @Override
    public void init() {
//        args = new String[2];
//        args[0] = "localhost";
//        args[1] = "3005";
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (args.length == 2) {
            client = new Client(args[0], Integer.valueOf(args[1]));
            if (client.getPort() < 1024 || client.getPort() > 65535) {
                System.out.println("Invaild input");
                System.exit(1);
            }
        } else {
            System.out.println("Invaild input");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("/model/Client/Client.fxml"));
        Pane root = fxmlLoader.load();
        client.startConnection();
        ClientController clientController = fxmlLoader.getController();
        clientController.setClient(client);
        Scene scene = new Scene(root, 420, 505);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args1) {
        args = new String[2];
        args = args1;
        // execute the UI
        launch(args1);
    }
}


