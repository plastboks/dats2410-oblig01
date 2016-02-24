/**
 * Created by Erlend on 10.03.2015.
 */

package main.java.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Circle;
import main.java.util.Constants;


public class ServerController
{
    private boolean autoToggle;
    private ServerDispatcher dispatcher;
    private AutoLogic auto;

    private Circle redCircle, yellowCircle, greenCircle;

    private CheckBox red, yellow, green;

    public ServerController(ServerDispatcher dispather)
    {
        this.dispatcher = dispather;
        auto = new AutoLogic(dispather);
        auto.run();
    }

    @FXML
    private void sendButtonAction(ActionEvent event)
    {
        System.out.println("Send kommando");
    }

    @FXML
    private void toggleAutoBtn(ActionEvent event)
    {
        autoToggle = !autoToggle;
        auto.setState(autoToggle);
        toggleCheckboxes();
    }

    private void toggleCheckboxes()
    {
        red.setDisable(autoToggle);
        yellow.setDisable(autoToggle);
        green.setDisable(autoToggle);
    }

    private void setRedCircle(boolean state)
    {
        if (state) {
            red.setBackground(Constants.RED);
        } else {
            red.setBackground(Constants.GRAY);
        }
    }

    private void setYellowCircle(boolean state)
    {
        if (state) {
            yellow.setBackground(Constants.YELLOW);
        } else {
            yellow.setBackground(Constants.GRAY);
        }
    }

    private void setGreenCircle(boolean state)
    {
        if (state) {
            green.setBackground(Constants.GREEN);
        } else {
            green.setBackground(Constants.GRAY);
        }
    }
}
