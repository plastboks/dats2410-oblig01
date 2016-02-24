package main.java.util;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Created by alex on 2/24/16.
 */
public class Constants
{
    public static final Background RED = new Background(
            new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));

    public static final Background YELLOW = new Background(
            new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));

    public static final Background GREEN = new Background(
            new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));

    public static final Background GRAY = new Background(
            new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
}
