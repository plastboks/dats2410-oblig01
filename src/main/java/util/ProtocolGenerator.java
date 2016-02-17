package main.java.util;

/**
 * Created by alex on 2/17/16.
 */
public class ProtocolGenerator
{
    public enum ValidState { ON,OFF }

    public static class Payload
    {
        private String load;
        public String get() { return load; }
        public void set(String load) { this.load = load; }
    }

    public static Payload generate(ValidState red, ValidState yellow, ValidState green)
    {
        Payload payLoad = new Payload();
        payLoad.set(String.format("%d;%d;%d",
                green == ValidState.OFF ? 0 : 1,
                yellow == ValidState.OFF ? 0 : 1,
                red == ValidState.OFF ? 0 : 1));
        return payLoad;
    }
}
