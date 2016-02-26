package main.java.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import main.java.util.Constants;
import main.java.util.Payload;
import main.java.view.Logger;

import java.io.IOException;

/**
 * Created by Simon on 25.02.2016.
 */
public class ClientController {



    @FXML
    private Circle red, yellow, green;

    @FXML
    private Logger logger;

    private ClientSocket clientSocket;


    public ClientController() {}

    public void initClientSocket()
    {
        if (clientSocket == null) {
            clientSocket = new ClientSocket(this);
            clientSocket.start();
        }
    }

    public void pushLoggerToClientSocket()
    {
       clientSocket.setLogger(logger);
    }

    private void setRedCircle(boolean state)
    {
        if (state) {
            red.setFill(Constants.RED);
        } else {
            red.setFill(Constants.GRAY);
        }
    }

    private void setYellowCircle(boolean state)
    {
        if (state) {
            yellow.setFill(Constants.YELLOW);
        } else {
            yellow.setFill(Constants.GRAY);
        }
    }

    private void setGreenCircle(boolean state)
    {
        if (state) {
            green.setFill(Constants.GREEN);
        } else {
            green.setFill(Constants.GRAY);
        }
    }

    public void setLights(Payload payload)
    {
        setRedCircle(payload.getRed() == Payload.ValidState.ON);
        setYellowCircle(payload.getYellow() == Payload.ValidState.ON);
        setGreenCircle(payload.getGreen() == Payload.ValidState.ON);
    }


    @FXML
    public void exitApplication() {
        Platform.exit();
    }


}
