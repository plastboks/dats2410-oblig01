package main.java.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * Created by alex on 2/24/16.
 */
public class ClientList extends ListView<String>
{
    private ObservableList<String> list = FXCollections.observableArrayList();

    public ClientList() {  }

    public void clear()
    {
        list.removeAll(list);
    }

    public void insert(List<String> newList)
    {
        clear();
        newList.forEach(this::push);
    }

    public void push(String str)
    {
        list.add(str);
        Platform.runLater(() -> getItems().setAll(list));
    }
}
