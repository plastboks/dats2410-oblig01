/**
 * Created by alex on 2/9/16.
 */

package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Server extends Application
{
    public static void main(String ... args)
    {
        launch(args);
    }

    @Override
    public void start(Stage server) throws IOException {


        Parent parent = FXMLLoader.load(getClass().getResource("gui/server.mockup.fxml"));
        Scene scene = new Scene(parent);
        server.setTitle("Server");
        server.setScene(scene);
        server.show();

    }

    public void send()
    {
        System.out.println("Kommando send klient");
    }
}
