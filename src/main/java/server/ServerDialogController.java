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


    @FXML
    public void okBtn(ActionEvent event){
        System.out.println("Gjør noe!!");
    }

}
