package Package1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * This is the client connects to the server
 * @author ytwytw
 */
public class Client {

    /**
     * connecting tryout
     */
    public Client() {
        try {
            String local;

            try {
                local = InetAddress.getLocalHost().getHostAddress() + ":" + port;
            } catch (UnknownHostException ex) {
                local = "Network Error";
            }

            ip = (String) JOptionPane.showInputDialog(null, "IP: ", "Info", JOptionPane.INFORMATION_MESSAGE, null, null, local);

            port = Integer.parseInt(ip.substring(ip.indexOf(":") + 1));
            ip = ip.substring(0, ip.indexOf(":"));

            socket = new Socket(ip, port);

            String set_username = System.getProperty("user.name");
            set_username = (String) JOptionPane.showInputDialog(null, "Username: ", "Info", JOptionPane.INFORMATION_MESSAGE, null, null, set_username);
            username = set_username;

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(username);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String response = (String) ois.readObject();

            JOptionPane.showMessageDialog(null, response, "Message", JOptionPane.INFORMATION_MESSAGE);

            if (response.equals("Your name is already taken!")) {
                System.exit(0);
            }

            new Thread(send).start();
            new Thread(receive).start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    } 
    /**
     * state setup passing msg
     */
    public int state = 0;
    /**
     * connected thread perform task
     */
    public boolean connected = true;
    Runnable send = new Runnable() {
        @Override
        public void run() {
            ObjectOutputStream oos;

            while (connected) {
                if (socket != null) {
                    try {
                        DataPackage dp1 = new DataPackage();
                        dp1.gamecount = BlackJackGUI.wincount+BlackJackGUI.losecount+BlackJackGUI.tiecount;
                        dp1.wincount = BlackJackGUI.wincount;
                        dp1.username = username;
                        dp1.stateofgame = state;
                        dp1.ip = ip;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {
                            //Handle exception
                        }
                        
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(dp1);
                        
                        if (state == 1) // Client Disconnected
                        {
                            connected = false;
                            socket = null;

                            JOptionPane.showMessageDialog(null, "Client Disconnected", "Info", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                    } catch (Exception ex) {
                    }
                } else {
                    break;
                }
            }
        }
    };
    Runnable receive = new Runnable() {
        @Override
        public void run() {
            ObjectInputStream ois;

            while (connected) {
                try {
                    ois = new ObjectInputStream(socket.getInputStream());
                    int receive_state = (Integer) ois.readObject();

                    if (receive_state == 1) // Kicked / Disconnected by Server
                    {
                        connected = false;
                        socket = null;

                        JOptionPane.showMessageDialog(null, "Disconnected by Server", "Info", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    } else if (receive_state == 2) // Server Disconnected
                    {
                        connected = false;
                        socket = null;

                        JOptionPane.showMessageDialog(null, "Server Disconnected", "Info", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }

                    ois = new ObjectInputStream(socket.getInputStream());
                    ArrayList<DataPackage> list_data = (ArrayList<DataPackage>) ois.readObject();

                    for (int i = 0; i < list_data.size(); i++) {
                        DataPackage dp = list_data.get(i);

                        if (list_data.size() != others.size()) {
                            if (list_data.size() > others.size()) {
                                others.add(dp);
                            }
                            if (list_data.size() < others.size()) {
                                others.remove(0);
                            }
                        } else {
                            others.set(i, dp);
                        }
                    }
                } catch (Exception ex) {
                }
            }
        }
    };
    /**
     * create socket
     */
    public static Socket socket;
    /**
     * define port
     */
    public static int port = 3421;
    /**
     * define ip
     */
    public static String ip = "";
    /**
     * define total game counts in the game package 
     */
    public int gamecount = 0;
    /**
     * define total win counts in the game package 
     */
    public int wincount = 0;
    /**
     * define client username in the game package 
     */
    public String username = "";
    /**
     * define array list of the game data package 
     */
    public static ArrayList<DataPackage> others = new ArrayList<DataPackage>();

    /**
     * update the game data to the package 
     */
    public void update() {
        for (int i = 0; i < others.size(); i++) {
            try {
                DataPackage dp = others.get(i);

                dp.gamecount = BlackJackGUI.gamecount;
                dp.wincount = BlackJackGUI.wincount;

            } catch (Exception ex) {
            }
        }
    }
}
