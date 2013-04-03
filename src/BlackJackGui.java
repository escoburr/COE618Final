import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BlackJackGui {
	
	//dealer will stand on DEALER_LIMIT
	static final int DEALER_LIMIT = 17;
	
	private JFrame frame;
	//deck of cards
	private Deck deck;
	//draw panel
	private DrawFrame drawPanel;
	//player
	private Player player;
	//dealer
	private Player dealer;
	//message text
	private String message = "";
	//utility help class
	private Utility help;
	//game on
	private boolean gameOn;
	
	public static void main (String[] args) {
		BlackJackGui gui = new BlackJackGui ();
		gui.init();
	}
	
	/*
	 * initialize the GUI
	 */
	public void init() {
		//new frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//new draw panel
		drawPanel = new DrawFrame();
		drawPanel.setBounds(0, 0, 600, 500);
		drawPanel.setLayout(null);
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(drawPanel);
		frame.setSize(600,500);
		frame.setVisible(true);
		//add new game button to panel
		JButton newGameButton = new JButton("NEW GAME");
		newGameButton.setBounds(145, 415, 100, 35);
		drawPanel.add(newGameButton);
		//add hit button to panel
		JButton hitButton = new JButton("HIT");
		hitButton.setBounds(270, 415, 60, 35);
		drawPanel.add(hitButton);
		//add stand button to panel
		JButton standButton = new JButton("STAND");
		standButton.setBounds(355, 415, 75, 35);
		drawPanel.add(standButton);
		//register hit button event listener
		hitButton.addActionListener(new HitListener());
		//register new game button event listener
		newGameButton.addActionListener(new NewGameListener());
		//register stand button event listener
		standButton.addActionListener(new standListener());
	}
	
	/*
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
		//game is on
		gameOn = true;
	}
	
	/*
	 * new game button event handling
	 */
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//start new game
			if (!gameOn) {
				setupNewGame();
				//deal two cards to dealer and player
				for (int i=0; i < 2; i++) {
					dealer.addCard(deck.dealCard());
					player.addCard(deck.dealCard());
				}
				//check if the player has a blackjack
				if (help.checkBlackJack(player)) {
					//dealer has also blackjack => tie
					if (help.determineWinner(player, dealer) == Utility.Winner.TIE) {
						message = "Blackjack ! Tie !";
						gameOn = false;				
					} else {
						message = "Blackjack ! You win !";
						gameOn = false;								
					}
				}
				//draw hands
				drawPanel.setDealerHand(dealer.getHand());
				drawPanel.setPlayerHand(player.getHand());
				drawPanel.setMessage(message);
				drawPanel.setGameOn(gameOn);
				frame.repaint();
			}
		}
	}
	
	/*
	 * hit button event handling
	 */
	class HitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {		
			//only if game is still on
			if (gameOn) {
				//deal a card and add the card to player's hand
				player.addCard(deck.dealCard());
				//check if the player has busted (> 21)
				//winner = help.determineWinner(player, dealer);
				if (help.checkBust(player)) {
					message = "Busted ! You lose !";
					gameOn = false;
				}
				//draw player's hand
				drawPanel.setPlayerHand(player.getHand());
				drawPanel.setMessage(message);
				drawPanel.setGameOn(gameOn);
				frame.repaint();
			}
		}
	}
	
	/*
	 * stand button event handling
	 */
	class standListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Utility.Winner winner;
			if (gameOn) {
				gameOn = false;
				//deal a card if the dealer's hand is valued under DEALER_LIMIT
				while ((dealer.getValueOfHand()[0] < DEALER_LIMIT) && (dealer.getValueOfHand()[1] < DEALER_LIMIT))  {
					dealer.addCard(deck.dealCard());
				}
				//is the dealer busted
				if (help.checkBust(dealer)) {
					message = "You win !";
					drawPanel.setMessage(message);
					drawPanel.setGameOn(gameOn);
					frame.repaint();					
				} else {
					//determine the winner
					winner = help.determineWinner(player, dealer);
					switch (winner) {
						case PLAYER: 	message = "You win !";
										break;
						case DEALER: 	message = "You lose !";
										break;
						case TIE:		message = "Tie !";
										break;
						default:		break;
					}			
					drawPanel.setMessage(message);
					drawPanel.setGameOn(gameOn);
					frame.repaint();	
				}
			}
		}
	}
	
}

/*
 * class used to draw the panel
 */
class DrawFrame extends JPanel {
	
	//player's hand
	private ArrayList<Card> playerHand;
	//dealer's hand
	private ArrayList<Card> dealerHand;
	//message
	String message = "";
	//game on
	boolean gameOn;
	
	/*
	 * set player hand to be drawn on panel
	 */
	public void setPlayerHand(ArrayList<Card> playerHand) {
		this.playerHand = playerHand;
	}
	
	/*
	 * set dealer hand to be drawn on panel
	 */
	public void setDealerHand(ArrayList<Card> dealerHand) {
		this.dealerHand = dealerHand;
	}
	
	/*
	 * set message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/*
	 * set gameOn signal
	 */
	public void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
	}
	
	/*
	 * the actual method used to draw the panel
	 */
	public void paintComponent(Graphics g) {
		//green background
		g.setColor(new Color(0.0f, 0.5f, 0.0f));
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		//draw message
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(new Color(1.0f, 0.0f, 0.0f));
		g.drawString(message,240,225);
		//draw player's hand
		if (playerHand != null) {
			for (int i=0; i < playerHand.size(); i++) {
				Image image = playerHand.get(i).getImage();
				g.drawImage(image,(240+i*20),(285),this);
			}	
		}
		//draw dealer's hand
		if (dealerHand != null) {
			for (int i=0; i < dealerHand.size(); i++) {
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
				g.drawImage(image,(240+i*20),(50),this);
			}	
		}
	}
	
}
