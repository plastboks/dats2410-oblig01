package main.java.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import main.java.*;
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

    @FXML
    private void handleButtonAction(ActionEvent event) {

        Platform.exit();
    }


    public ClientController() throws IOException
    {
        new main.java.Client();
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

    public void setLights(String[] s)
    {
        setRedCircle(s[2].equals("1"));
        setYellowCircle(s[1].equals("1"));
        setGreenCircle(s[0].equals("1"));
    }


    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }


}
