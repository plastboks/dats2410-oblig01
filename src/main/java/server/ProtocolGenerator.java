package main.java.server;

/**
 * Created by alex on 2/17/16.
 */
public class ProtocolGenerator
{
    public enum ValidState { ON,OFF }

    public static String generate(ValidState red, ValidState yellow, ValidState green)
    {
        return String.format("%d;%d;%d",
                red == ValidState.OFF ? 0 : 1,
                yellow == ValidState.OFF ? 0 : 1,
                green == ValidState.OFF ? 0 : 1);
    }
}
