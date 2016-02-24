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
        private ValidState red = ValidState.OFF, yellow = ValidState.OFF,
                green = ValidState.OFF;

        private Payload()
        {
            generate();
        }

        private void generate()
        {
            load = String.format("%d;%d;%d",
                    green == ValidState.OFF ? 0 : 1,
                    yellow == ValidState.OFF ? 0 : 1,
                    red == ValidState.OFF ? 0 : 1);
        }

        public static Payload with() { return new Payload(); }

        public Payload red(ValidState red)
        {
            this.red = red;
            generate();
            return this;
        }

        public Payload yellow(ValidState yellow)
        {
            this.yellow = yellow;
            generate();
            return this;
        }

        public Payload green(ValidState green)
        {
            this.green = green;
            generate();
            return this;
        }

        public String get() { return load; }

        public ValidState getRed()
        {
            return red;
        }

        public void setRed(ValidState red)
        {
            this.red = red;
        }

        public ValidState getYellow()
        {
            return yellow;
        }

        public void setYellow(ValidState yellow)
        {
            this.yellow = yellow;
        }

        public ValidState getGreen()
        {
            return green;
        }

        public void setGreen(ValidState green)
        {
            this.green = green;
        }
    }
}
