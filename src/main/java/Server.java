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
import main.java.server.ServerDispatcher;

import java.io.IOException;

public class Server extends Application
{
    private ServerController serverController;

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

        serverController = loader.getController();
        serverController.pushLoggerToDispatcher();

        server.setTitle("Server");
        server.setScene(scene);
        server.show();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();

        serverController.killAutoThread();
    }
}


