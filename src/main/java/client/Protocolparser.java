package client;

/**
 * Created by Simon on 17.02.2016.
 */
public class Protocolparser {

    /* Ta i mot client APP.java insans */
    public static void signalparse(String in)
    {

        if(in.length()!=5){
            return;
        }
        for(char c : in.toCharArray())
        {
            if((c!='0') && (c!='1') && (c!=';'))
            {
                return;
            }
        }

        String[] s = in.split(";");

        if(s.length!=3)
        {
            return;
        }

        /* Send s til GUI for oversettelse til lys */
    }
}
