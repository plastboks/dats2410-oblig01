/**
 * Created by Erlend on 10.03.2015.
 */

package main.java.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Circle;
import main.java.util.Constants;
import main.java.view.Logger;

import java.io.IOException;


public class ServerController
{
    private boolean autoToggle;
    private ServerDispatcher dispatcher;
    private AutoLogic auto;

    @FXML
    private Circle redCircle, yellowCircle, greenCircle;

    @FXML
    private CheckBox redBox, yellowBox, greenBox;

    @FXML
    private Logger logger;

    public ServerController() throws IOException
    {
    }

    public ServerController(ServerDispatcher dispatcher) throws IOException
    {
        this();
        this.dispatcher = dispatcher;
        auto = new AutoLogic(dispatcher);
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

    public void pushLoggerToDispatcher() throws IOException
    {
        ServerDispatcher.getInstance().setLogger(logger);
    }

    private void toggleCheckboxes()
    {
        redBox.setDisable(autoToggle);
        yellowBox.setDisable(autoToggle);
        greenBox.setDisable(autoToggle);
    }

    private void setRedCircle(boolean state)
    {
        if (state) {
            redCircle.setFill(Constants.RED);
        } else {
            redCircle.setFill(Constants.GRAY);
        }
    }

    private void setYellowCircle(boolean state)
    {
        if (state) {
            yellowCircle.setFill(Constants.YELLOW);
        } else {
            yellowCircle.setFill(Constants.GRAY);
        }
    }

    private void setGreenCircle(boolean state)
    {
        if (state) {
            greenCircle.setFill(Constants.GREEN);
        } else {
            greenCircle.setFill(Constants.GRAY);
        }
    }

    public Logger getLogger()
    {
        return logger;
    }
}
