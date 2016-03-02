package main.java.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by HansChristian on 25.02.2016.
 */
public class ClientDialogController {

    @FXML
    private TextField textField;

    public ClientDialogController() {}



    public void ipInput()
    {
      String host =  textField.getText();
    }

    public void portInput()
    {
        String portString = textField.getText();
        int port = Integer.parseInt(portString);
    }

}
