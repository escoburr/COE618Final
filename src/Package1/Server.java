package Package1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Server {

    public static int port = 3421;
    public static String ip = "";
    public static int gamecount =0;
    public static int wincount =0;
    public static ServerSocket server;
    public static List<Socket> list_sockets = Collections.synchronizedList(new ArrayList<Socket>());
    public static List<Integer> list_client_states = Collections.synchronizedList(new ArrayList<Integer>());
    public static List<DataPackage> list_data = Collections.synchronizedList(new ArrayList<DataPackage>());
    private static Runnable accept = new Runnable() {
        @Override
        public void run() {
            new Thread(send).start();
            new Thread(receive).start();

            while (true) {
                try {
                    Socket socket = server.accept();

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String username = (String) ois.readObject();

                    boolean accepted = true;

                    for (int i = 0; i < list_data.size(); i++) {
                        if (list_data.get(i).username.toLowerCase().equals(username.toLowerCase())) {
                            accepted = false;
                            break;
                        }
                    }

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                    if (accepted) {
                        oos.writeObject("Welcome To BlackJack Server!");

                        list_clients_model.addElement(username + " - " + socket.getInetAddress().getHostAddress() + " Wins: " + wincount + " Games: " + gamecount);
                        list_client_states.add(0);

                        list_data.add(new DataPackage());
                        list_sockets.add(socket);
                    } else {
                        oos.writeObject("Your name is already taken!");
                    }
                } catch (Exception ex) {
                }
            }
        }
    };
    private static Runnable send = new Runnable() {
        @Override
        public void run() {
            ObjectOutputStream oos;

            while (true) {
                for (int i = 0; i < list_sockets.size(); i++) {
                    try {
                        oos = new ObjectOutputStream(list_sockets.get(i).getOutputStream());
                        int client_state = list_client_states.get(i);
                        oos.writeObject(client_state);

                        oos = new ObjectOutputStream(list_sockets.get(i).getOutputStream());
                        oos.writeObject(list_data);

                        if (client_state == 1) // Kicked by Server
                        {
                            disconnectClient(i);
                            i--;
                            JOptionPane.showMessageDialog(null, "Disconnected by Server", "Info", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        } else if (client_state == 2) // Server Disconnected
                        {
                            disconnectClient(i);
                            i--;
                            JOptionPane.showMessageDialog(null, "Server Disconnected", "Info", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }

                    } catch (Exception ex) {
                    }
                }
            }
        }
    };
    private static Runnable receive = new Runnable() {
        @Override
        public void run() {
            ObjectInputStream ois;
            while (true) {
                for (int i = 0; i < list_sockets.size(); i++) {
                    try {
//                        ois = new ObjectInputStream(list_sockets.get(i).getInputStream());
//                        int receive_state = (Integer) ois.readObject();

                        ois = new ObjectInputStream(list_sockets.get(i).getInputStream());
                        DataPackage dp2 = new DataPackage();
                        dp2.gamecount = 0;
                        dp2.wincount = 0;
                        dp2.username = "";
                        dp2.stateofgame = 0;
                        dp2.ip = "";
                        dp2 = (DataPackage) ois.readObject();
                        int receive_state;
                        receive_state = dp2.stateofgame;
                        
                        list_clients_model.set(i, dp2.username + " - " + dp2.ip+ " Wins: " + dp2.wincount + " Games: " + dp2.gamecount);
                        
                        
                        list_data.set(i, dp2);

                        if (receive_state == 1) // Client Disconnected by User
                        {
                            disconnectClient(i);
                            i--;
                        }
                    } catch (Exception ex) // Client Disconnected (Client Didn't Notify Server About Disconnecting)
                    {
                        disconnectClient(i);
                        i--;

                    }

                }
            }
        }
    };

    public static void disconnectClient(int index) {
        try {
            list_clients_model.removeElementAt(index);
            list_client_states.remove(index);
            list_data.remove(index);
            list_sockets.remove(index);
        } catch (Exception ex) {
        }
    }
    public static JFrame frame;
    public static JPanel content;
    public static JPanel panel1;
    public static JPanel panel2;
    public static JPanel panel3;
    public static JButton btn_disconnect;
    public static JList list_clients;
    public static DefaultListModel list_clients_model;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        try {
            ip = InetAddress.getLocalHost().getHostAddress() + ":" + port;

            server = new ServerSocket(port, 0, InetAddress.getLocalHost());
            new Thread(accept).start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }



        btn_disconnect = new JButton();
        btn_disconnect.setText("Disconnect");
        btn_disconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = list_clients.getSelectedIndex();

                if (selected != -1) {
                    try {
                        list_client_states.set(selected, 1);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        list_clients_model = new DefaultListModel();
        list_clients = new JList(list_clients_model);
        list_clients.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    System.out.println(list_clients.getSelectedIndex());
                }
            }
        });

        frame = new JFrame();
        frame.setTitle("Server - " + ip);

        frame.addWindowListener(new WindowListener() {
            public void windowActivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                while (list_sockets.size() != 0) {
                    try {
                        for (int i = 0; i < list_client_states.size(); i++) {
                            list_client_states.set(i, 2);
                        }
                    } catch (Exception ex) {
                    }
                }

                System.exit(0);
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }
        });

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 1, 1, 1));
        panel1.add(btn_disconnect);

        panel2 = new JPanel();
        panel2.add(new JLabel(ip));

        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(1, 1));
        panel3.add(panel1, BorderLayout.NORTH);
        panel3.add(new JScrollPane(list_clients), BorderLayout.CENTER);
        panel3.add(panel2, BorderLayout.SOUTH);

        content = new JPanel();
        content.setLayout(new GridLayout(1, 1, 1, 1));
        content.add(panel3);

        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.setContentPane(content);
        frame.pack();
        frame.setSize(350, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
