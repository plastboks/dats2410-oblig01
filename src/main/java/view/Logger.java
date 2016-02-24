package main.java.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import main.java.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alex on 2/24/16.
 */
public class Logger extends ListView<String>
{
    private ObservableList<String> list = FXCollections.observableArrayList();
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);

    public Logger()
    {
        push("Self: Logger started");
    }

    public void push(String str)
    {
        String line = String.format("[%s] %s", sdf.format(new Date()), str);
        list.add(line);

        Platform.runLater(() -> getItems().setAll(list));
    }
}
