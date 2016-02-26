package main.java.util;

/**
 * Created by alex on 2/25/16.
 */
public class Payload
{
    public enum ValidState { ON, OFF }

    private ValidState red = ValidState.OFF;
    private ValidState yellow = ValidState.OFF;
    private ValidState green = ValidState.OFF;

    private Payload() { }

    public static Payload with()
    {
        return new Payload();
    }

    public Payload red(ValidState red)
    {
        this.red = red;
        return this;
    }

    public Payload yellow(ValidState yellow)
    {
        this.yellow = yellow;
        return this;
    }

    public Payload green(ValidState green)
    {
        this.green = green;
        return this;
    }

    public ValidState getRed()
    {
        return red;
    }

    public ValidState getYellow()
    {
        return yellow;
    }

    public ValidState getGreen()
    {
        return green;
    }

    @Override
    public String toString()
    {
        return String.format("Green: %s, Yellow %s, Red %s",
                getGreen() == ValidState.ON ? "On" : "Off",
                getYellow() == ValidState.ON ? "On" : "Off",
                getRed() == ValidState.ON ? "On" : "Off"
        );
    }
}
