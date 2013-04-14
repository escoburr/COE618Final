package Package1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * This is the main GUI of the blackjack game 
 * @author ytwytw
 */
public class BlackJackGUI {

    /**dealer will stand on DEALER_LIMIT
     * 
     */ 
    static final int DEALER_LIMIT = 17;
    /**
     * define GUI
     */
    public JFrame frame;
    /**
     * Setup interface
     */
    public JFrame mainframe;
    /**
     * initialize deck of cards
     */
    public Deck deck;
    /**
     * create drawing panel
     */
    public DrawFrame drawPanel;
    /**
     * create players
     */
    public Player player;
    /**
     * create dealers 
     */
    public Player dealer;
    /**
     * message text
     */
    public String message = "";
    /**
     * message setup
     */
    public String mplayer = "";
    /**
     * message setup
     */
    public String mdealer = "";
    /**
     * utility help class
     */
    public Utility help;
    /**
     * game on
     */
    public boolean gameOn;
    /**
     * net setup
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
     * define win counts 
     */
    public static int wincount = 0;
    /**
     * define lose counts 
     */
    public static int losecount = 0;
    /**
     * define tie counts 
     */
    public static int tiecount = 0;
    /**
     * define total game counts 
     */
    public static int gamecount = 0;
    

    /**
     * Main Running code of the GUI 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Client c = new Client();
        BlackJackGUI gui = new BlackJackGUI();
        gui.init();
    }

    /**
     * initialize the GUI
     * @throws InterruptedException
     */
    public void init() throws InterruptedException {
        //setup main screen
        mainframe = new JFrame();
        BufferedImage img2 = null;
        try {
            File f1 = new File("pictures/title.png");
            img2 = ImageIO.read(f1);
            System.out.println("File " + f1.toString());
        } catch (Exception e) {
            System.out.println("Cannot read file: " + e);
        }
        Background background2 = new Background(img2, Background.SCALED, 0.50f, 0.5f);
        mainframe.setContentPane(background2);
        mainframe.setSize(455, 345);
        mainframe.setTitle("SUPER MEGA AWSOME BLACKJACK GAME!");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setResizable(false);
        mainframe.setVisible(true);
        JButton playButton = new JButton("");
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setBounds(0, 0, 700, 700);
        mainframe.add(playButton);
        playButton.addActionListener(new NewGameListener());



        //set up game frame
        frame = new JFrame();
        BufferedImage img = null;
        try {
            File f = new File("pictures/table.png");
            img = ImageIO.read(f);
            System.out.println("File " + f.toString());
        } catch (Exception e) {
            System.out.println("Cannot read file: " + e);
        }
        Background background = new Background(img, Background.ACTUAL, 0.50f, 0.5f);
        frame.setContentPane(background);
        frame.setSize(893, 575);
        frame.setTitle("SUPER MEGA AWSOME BLACKJACK GAME!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //new draw panel
        drawPanel = new DrawFrame();
        drawPanel.setBounds(0, 0, 880, 600);
        drawPanel.setLayout(null);

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(drawPanel);
        //add new game button to panel
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.setBounds(105, 495, 200, 40);
        drawPanel.add(newGameButton);
        //add hit button to panel
        JButton hitButton = new JButton("HIT");
        hitButton.setBounds(560, 495, 160, 40);
        drawPanel.add(hitButton);
        //add stand button to panel
        JButton standButton = new JButton("STAND");
        standButton.setBounds(725, 495, 155, 40);
        drawPanel.add(standButton);
        //register hit button event listener
        hitButton.addActionListener(new HitListener1());
        //register new game button event listener
        newGameButton.addActionListener(new NewGameListener());
        //register stand button event listener
        standButton.addActionListener(new standListener());
    }

    /**
     * set up a new game
     */
    private void setupNewGame() {
        //create a new deck
        deck = new Deck();
        //new player
        player = new Player();
        //new dealer
        dealer = new Player();
        //new help class
        help = new Utility();
        //clear message
        message = "";
        mplayer = "";
        mdealer = "X";
        //game is on
        gameOn = true;
    }

    /**
     * new game button event handling
     */
    class NewGameListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            frame.setVisible(true);
            mainframe.setVisible(false);
            //start new game
            if (!gameOn) {
                setupNewGame();
                //deal two cards to dealer and player
                for (int i = 0; i < 2; i++) {
                    dealer.addCard(deck.dealCard());
                    player.addCard(deck.dealCard());
                }
                //check if the player has a blackjack
                if (help.checkBlackJack(player)) {
                    //dealer has also blackjack => tie
                    if (help.determineWinner(player, dealer) == Utility.Winner.TIE) {
                        message = "Blackjack ! Tie !";
                        tiecount++;
                        gameOn = false;
                    } else {
                        message = "Blackjack ! You win !";
                        wincount++;
                        gameOn = false;
                    }
                }
                //draw hands
                drawPanel.setDealerHand(dealer.getHand());
                drawPanel.setPlayerHand(player.getHand());
                if (player.getValueOfHand()[0] > 21) {
                    mplayer = "" + player.getValueOfHand()[1];
                } else if (player.getValueOfHand()[1] > 21) {
                    mplayer = "" + player.getValueOfHand()[0];
                } else if (player.getValueOfHand()[0] >= player.getValueOfHand()[1]) {
                    mplayer = "" + player.getValueOfHand()[0];
                } else {
                    mplayer = "" + player.getValueOfHand()[1];
                }
                drawPanel.setmplayer(mplayer);
                mdealer = "X";
                drawPanel.setmdealer(mdealer);
                drawPanel.setMessage(message);
                drawPanel.setwincount(wincount);
                drawPanel.setlosecount(losecount);
                drawPanel.settiecount(tiecount);
                drawPanel.setGameOn(gameOn);
                frame.repaint();
            }
        }
    }

    /**
     * hit button event handling
     */
    class HitListener1 implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //only if game is still on
            if (gameOn) {
                //deal a card and add the card to player's hand
                player.addCard(deck.dealCard());
                if (player.getValueOfHand()[0] > 21) {
                    mplayer = "" + player.getValueOfHand()[1];
                } else if (player.getValueOfHand()[1] > 21) {
                    mplayer = "" + player.getValueOfHand()[0];
                } else if (player.getValueOfHand()[0] >= player.getValueOfHand()[1]) {
                    mplayer = "" + player.getValueOfHand()[0];
                } else {
                    mplayer = "" + player.getValueOfHand()[1];
                }
                //check if the player has busted (> 21)
                //winner = help.determineWinner(player, dealer);
                if (help.checkBust(player)) {
                    message = "Busted ! You lose !";
                    losecount++;
                    gameOn = false;
                    if (dealer.getValueOfHand()[0] > 21) {
                        mdealer = "" + dealer.getValueOfHand()[1];
                    } else if (dealer.getValueOfHand()[1] > 21) {
                        mdealer = "" + dealer.getValueOfHand()[0];
                    } else if (dealer.getValueOfHand()[0] >= dealer.getValueOfHand()[1]) {
                        mdealer = "" + dealer.getValueOfHand()[0];
                    } else {
                        mdealer = "" + dealer.getValueOfHand()[1];
                    }
                    drawPanel.setmdealer(mdealer);
                }
                //draw player's hand
                drawPanel.setPlayerHand(player.getHand());
                drawPanel.setMessage(message);
                drawPanel.setmplayer(mplayer);
                drawPanel.setwincount(wincount);
                drawPanel.setlosecount(losecount);
                drawPanel.settiecount(tiecount);
                drawPanel.setGameOn(gameOn);
                frame.repaint();
            }
        }
    }

    /**
     * stand button event handling
     */
    class standListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            Utility.Winner winner;
            if (gameOn) {
                gameOn = false;
                //deal a card if the dealer's hand is valued under DEALER_LIMIT
                while ((dealer.getValueOfHand()[0] < DEALER_LIMIT) && (dealer.getValueOfHand()[1] < DEALER_LIMIT)) {
                    dealer.addCard(deck.dealCard());
                }
                //is the dealer busted
                if (help.checkBust(dealer)) {
                    message = "You win !";
                    wincount++;
                    drawPanel.setMessage(message);
                    drawPanel.setwincount(wincount);
                    drawPanel.setlosecount(losecount);
                    drawPanel.settiecount(tiecount);
                    drawPanel.setGameOn(gameOn);
                    frame.repaint();
                    if (dealer.getValueOfHand()[0] > 21) {
                        mdealer = "" + dealer.getValueOfHand()[1];
                    } else if (dealer.getValueOfHand()[1] > 21) {
                        mdealer = "" + dealer.getValueOfHand()[0];
                    } else if (dealer.getValueOfHand()[0] >= dealer.getValueOfHand()[1]) {
                        mdealer = "" + dealer.getValueOfHand()[0];
                    } else {
                        mdealer = "" + dealer.getValueOfHand()[1];
                    }
                    drawPanel.setmdealer(mdealer);
                } else {
                    //determine the winner
                    winner = help.determineWinner(player, dealer);
                    gamecount = wincount+losecount +tiecount;
                    switch (winner) {
                        case PLAYER:
                            message = "You win !";
                            wincount++;
                            break;
                        case DEALER:
                            message = "You lose !";
                            losecount++;
                            break;
                        case TIE:
                            message = "Tie !";
                            tiecount++;
                            break;
                        default:
                            
                            break;
                    }
                    if (dealer.getValueOfHand()[0] > 21) {
                        mdealer = "" + dealer.getValueOfHand()[1];
                    } else if (dealer.getValueOfHand()[1] > 21) {
                        mdealer = "" + dealer.getValueOfHand()[0];
                    } else if (dealer.getValueOfHand()[0] >= dealer.getValueOfHand()[1]) {
                        mdealer = "" + dealer.getValueOfHand()[0];
                    } else {
                        mdealer = "" + dealer.getValueOfHand()[1];
                    }
                    drawPanel.setmdealer(mdealer);
                    drawPanel.setMessage(message);
                    drawPanel.setwincount(wincount);
                    drawPanel.setlosecount(losecount);
                    drawPanel.settiecount(tiecount);
                    drawPanel.setGameOn(gameOn);
                    frame.repaint();
                }
            }
        }
    }

    class playListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            frame.setVisible(true);
            mainframe.setVisible(false);

        }
    }
}
/**
 * class used to draw the panel
 */

class DrawFrame extends JPanel {

    //player's hand
    private ArrayList<Card> playerHand;
    //dealer's hand
    private ArrayList<Card> dealerHand;
    //message
    String message = "";
    String mplayer = "";
    String mdealer = "";
    String wincount = "Win: ";
    String losecount = "Lose: ";
    String tiecount = "Tie: ";
    //game on
    boolean gameOn;

    /**
     * set player hand to be drawn on panel
     */
    public void setPlayerHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

    /**
     * set dealer hand to be drawn on panel
     */
    public void setDealerHand(ArrayList<Card> dealerHand) {
        this.dealerHand = dealerHand;
    }

    /**
     * set message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void setmplayer(String mplayer) {
        this.mplayer = mplayer;
    }

    public void setmdealer(String mdealer) {
        this.mdealer = mdealer;
    }

    public void setwincount(int wincount) {
        this.wincount = "Win: " + wincount;
    }

    public void setlosecount(int losecount) {
        this.losecount = "Lost: " + losecount;
    }

    public void settiecount(int tiecount) {
        this.tiecount = "Tie: " + tiecount;
    }
    /**
     * set gameOn signal
     */

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    /**
     * the actual method used to draw the panel
     */
    @Override
    public void paintComponent(Graphics g) {
        //draw message
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(new Color(1.0f, 0.0f, 0.0f));
        g.drawString(message, 350, 325);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(new Color(94, 201, 240));
        g.drawString(mplayer, 300, 450);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(new Color(94, 201, 240));
        g.drawString(mdealer, 300, 200);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(new Color(94, 201, 240));
        g.drawString(wincount, 50, 100);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(new Color(94, 201, 240));
        g.drawString(losecount, 50, 200);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(new Color(94, 201, 240));
        g.drawString(tiecount, 50, 300);

        //draw player's hand
        if (playerHand != null) {
            for (int i = 0; i < playerHand.size(); i++) {
                Image image = playerHand.get(i).getImage();
                g.drawImage(image, (380 + i * 20), (375), this);
            }
        }
        //draw dealer's hand
        if (dealerHand != null) {
            for (int i = 0; i < dealerHand.size(); i++) {
                Image image;
                if (gameOn) {
                    //first card face down if game on
                    if (i == 0) {
                        image = new ImageIcon("pictures/b1fv.png").getImage();
                    } else {
                        image = dealerHand.get(i).getImage();
                    }
                    //reveal the card when game ends
                } else {
                    image = dealerHand.get(i).getImage();
                }
                g.drawImage(image, (380 + i * 20), (175), this);
            }
        }
    }
}
