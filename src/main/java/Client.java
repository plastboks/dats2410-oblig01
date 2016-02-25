package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.client.ClientController;

import java.io.IOException;

/**
 * Created by alex on 2/9/16.
 */


public class Client extends Application
{
    private ClientController clientController;

    public static void main(String... args)
    {
        launch(args);
    }

    @Override
    public void start(Stage client) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/client.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        clientController = loader.getController();


        client.setTitle("Server");
        client.setScene(scene);
        client.show();
    }


}
