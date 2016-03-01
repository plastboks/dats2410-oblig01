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
import java.util.List;

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
        Parameters params = getParameters();
        List<String> list = params.getRaw();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/server.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        serverController = loader.getController();

        if (list.size() > 0) {
            try {
                int port = Integer.parseInt(list.get(0));
                if (port < 1000 || port > 10000)
                    throw new NumberFormatException();
                serverController.onReady(Integer.parseInt(list.get(0)));
            } catch (NumberFormatException ne) {
                System.out.println("Not a valid port number (1000 - 10000): " + list.get(0));
                System.exit(-1);
                return;
            }
        }

        server.setTitle("Server");
        server.setScene(scene);
        server.show();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
        serverController.killAutoThread();
        ServerDispatcher.getInstance().requestStop();
    }
}


