/**
 * Created by Erlend on 10.03.2015.
 */

package main.java.server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Circle;
import main.java.util.Constants;
import main.java.view.ClientList;
import main.java.view.Logger;

import java.io.IOException;

import main.java.util.Payload.ValidState;
import main.java.util.Payload;



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
    private ToggleButton autoButt;

    @FXML
    private Logger logger;

    @FXML
    private ClientList clients;


    public ServerController() throws IOException
    {
        dispatcher = ServerDispatcher.getInstance();
        auto = new AutoLogic(this, dispatcher);
    }

    public void onReady() throws IOException
    {
        ServerDispatcher.getInstance().setLogger(logger);
        ServerDispatcher.getInstance().setClientList(clients);

        dispatcher.start();
        auto.start();
    }

    public void onReady(int port) throws IOException
    {
        dispatcher.setPort(port);
        onReady();
    }

    public void killAutoThread()
    {
        auto.kill();
    }

    @FXML
    private void toggleAutoBtn(ActionEvent event)
    {
        autoToggle = !autoToggle;
        auto.setState(autoToggle);
        toggleCheckboxes();
        autoButt.setText(autoToggle ? "Auto" : "Manual");
    }

    @FXML
    public void onCheckbox(ActionEvent event)
    {
        Payload payload = Payload.with()
                .red(redBox.isSelected() ? ValidState.ON : ValidState.OFF)
                .yellow(yellowBox.isSelected() ? ValidState.ON : ValidState.OFF)
                .green(greenBox.isSelected() ? ValidState.ON : ValidState.OFF);
        dispatcher.newMessage(payload);
        setLights(payload);

    }

    @FXML
    public void exitApplication()
    {
        killAutoThread();
        Platform.exit();
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

    public void setLights(Payload payload)
    {
        setRedCircle(payload.getRed() == ValidState.ON);
        setYellowCircle(payload.getYellow() == ValidState.ON);
        setGreenCircle(payload.getGreen() == ValidState.ON);
    }
}
