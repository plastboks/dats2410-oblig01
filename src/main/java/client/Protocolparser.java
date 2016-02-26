package main.java.client;

import main.java.util.Payload;

/**
 * Created by Simon on 17.02.2016.
 */
public class Protocolparser {


    private static ClientController controller;
    private Payload payload;
    public Protocolparser(ClientController controller)
    {
      this.controller=controller;
    }
    public boolean signalparse(String in)
    {

        if(in.length()!=5){
            return false;
        }
        for(char c : in.toCharArray())
        {
            if((c!='0') && (c!='1') && (c!=';'))
            {
                return false;
            }
        }

        String[] s = in.split(";");

        if(s.length!=3)
        {
            return false;
        }


        payload = Payload.with()
                .green(s[0].equals("1") ? Payload.ValidState.ON : Payload.ValidState.OFF)
                .yellow(s[1].equals("1") ? Payload.ValidState.ON : Payload.ValidState.OFF)
                .red(s[2].equals("1") ? Payload.ValidState.ON : Payload.ValidState.OFF);


        return true;
    }

    public Payload getPayload()
    {
        return payload;
    }

}
