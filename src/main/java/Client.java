package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.client.ClientController;

import java.io.IOException;
import java.util.List;

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

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/client.dialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        client.setTitle("Client Dialog");
        client.setScene(scene);
        client.show();*/

        Parameters params = getParameters();
        List<String> list = params.getRaw();

        if (list.size() < 2) {
            System.out.printf("Please provide the host and port argument!\n");
            System.exit(-1);
            return;
        }



        String host = list.get(0);
        int port;

        try {
            port = Integer.parseInt(list.get(1));
        } catch (NumberFormatException e) {
            System.out.printf("Could not parse arguments");
            System.exit(-1);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/client.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        clientController = loader.getController();
        clientController.onReady(host, port);

        client.setTitle("ClientSocket");
        client.setScene(scene);
        client.show();
    }


    @Override
    public void stop() throws Exception
    {
        super.stop();
        clientController.killSocket();
    }


}
