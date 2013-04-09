package Package1;

import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public static int port = 3421;
    public static String ip = "";
    public static ServerSocket server;
    public static ArrayList<Socket> list_sockets = new ArrayList<Socket>();
    public static ArrayList<Integer> list_client_states = new ArrayList<Integer>();
    public static ArrayList<DataPackage> list_data = new ArrayList<DataPackage>();
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
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject("Welcome to This Server...");

                    list_clients_model.addElement(username + "-" + socket.getInetAddress().getHostAddress() + "-" + socket.getInetAddress().getHostName());
                    list_client_states.add(0);

                    list_data.add(new DataPackage());
                    list_sockets.add(socket);
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
                        if (client_state == 1)//Kicked by Server
                        {
                            disconnectClient(i);
                            i--;
                        } else if (client_state == 2)//Server Disconnected
                        {
                            disconnectClient(i);
                            i--;
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
                        ois = new ObjectInputStream(list_sockets.get(i).getInputStream());
                        int recieve_state = (Integer) ois.readObject();
                        ois = new ObjectInputStream(list_sockets.get(i).getInputStream());
                        DataPackage dp = (DataPackage) ois.readObject();

                        list_data.set(i, dp);

                        if (recieve_state == 1) {
                            disconnectClient(i);
                            i--;
                        }
                    } catch (Exception ex) { //Client Disconnected (Client didnt notify server)
                       disconnectClient(i);
                        i--;
                    }
                }
            }
        }
    };

    public static void disconnectClient(int index) {
        System.out.println (index);
        try {
            list_clients_model.removeElementAt(index);
            list_client_states.remove(index);
            list_data.remove(index);
            list_sockets.remove(index);
            list_sockets.get(index).close();

        } catch (Exception ex) {}
       finally {System.exit(0);
        }  
    }
    
    public static JFrame frame;
    public static JPanel content;
    public static JPanel panel1;
    public static JPanel panel2;
    public static JPanel panel3;
    public static JPanel panel4;
    public static JButton button_disconnect;
    public static JList list_clients;
    public static DefaultListModel list_clients_model;

    public static void main(String[] args) {
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception Ex){  
        }
        
        try {
            ip = InetAddress.getLocalHost().getHostAddress() + ":" + port;
            server = new ServerSocket(port, 0, InetAddress.getLocalHost());
            new Thread(accept).start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


        button_disconnect = new JButton();
        button_disconnect.setText("Disconnect");
        button_disconnect.addActionListener(new ActionListener() {
           
          @Override
            public void actionPerformed(ActionEvent e) {
                int selected = list_clients.getSelectedIndex();
                if (selected != -1) {
                }
                try {
                    list_client_states.set(selected, 1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error" + ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                }
                finally{System.exit(0);}
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
            @Override
            public void windowActivated(WindowEvent e) {}

            public void windowClose(WindowEvent e) {
                //System.exit(0);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                  while (!list_sockets.isEmpty()) {
                    try {
                        for (int i = 0; i < list_client_states.size(); i++) {
                            list_client_states.set(i, 2);
                        }
                    } catch (Exception ex) { }
                    finally{System.exit(0);}
                }
                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub
            }
        });


        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 1, 1, 1));
        panel1.add(button_disconnect);

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
