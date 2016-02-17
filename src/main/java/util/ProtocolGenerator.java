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
            generate(red, yellow, green);
        }

        private void generate(ValidState green, ValidState yellow, ValidState red)
        {
            load = String.format("%d;%d;%d",
                    green == ValidState.OFF ? 0 : 1,
                    yellow == ValidState.OFF ? 0 : 1,
                    red == ValidState.OFF ? 0 : 1);
        }

        public static Payload with() { return new Payload(); }

        public Payload red(ValidState red)
        {
            generate(this.green, this.yellow, red);
            return this;
        }

        public Payload yellow(ValidState yellow)
        {
            generate(this.green, yellow, this.red);
            return this;
        }

        public Payload green(ValidState green)
        {
            generate(green, this.yellow, this.red);
            return this;
        }

        public String get() { return load; }
    }
}
