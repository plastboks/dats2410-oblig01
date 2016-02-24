/**
 * Created by alex on 2/9/16.
 */

package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.server.ServerController;

import java.io.IOException;

public class Server extends Application
{
    public static void main(String... args)
    {
        launch(args);
    }

    @Override
    public void start(Stage server) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/server.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        ServerController serverController = loader.getController();
        serverController.pushLoggerToDispatcher();

        server.setTitle("Server");
        server.setScene(scene);
        server.show();
    }
}


