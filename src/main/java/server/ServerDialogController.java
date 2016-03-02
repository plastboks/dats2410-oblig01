package main.java.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by HansChristian on 25.02.2016.
 */
public class ServerDialogController {

    @FXML
    private Button okButton;

    public ServerDialogController() {}

    public void okButton(ActionEvent event){
        System.out.println("Gjør noe!!");
    }

}
