package SUPERMEGAAWESOMEEBLACKJACK;

import java.io.Serializable;

/**
 * Data Package transmitting between server and client 
 * @author ytwytw
 */
public class DataPackage implements Serializable {

    /**
     * define username in the game package 
     */
    public String username = "";
    /**
     * define winner counts in the game package 
     */
    public int wincount =0;
    /**
     * define game counts in the game package 
     */
    public int gamecount =0;
    /**
     * define current client status 
     */
    public int stateofgame =0;
    /**
     * define client IP
     */
    public String ip = "";
    
}
