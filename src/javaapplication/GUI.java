
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;


public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3006365376404246951L;
	//	private static final String PROYECT_PATH = "/Users/raul/Downloads/classic-cards/";
	private static final int SCREEN_HEIGHT = 450, SCREEN_WIDTH = 600;
	private Blackjack bj;
	
	private JFrame myFrame;
	private JPanel basicPanel, panelPlayerCards, panelDealerCards;	
	
	public GUI(final Blackjack blackjack){
		
		this.bj = blackjack;
			
		basicPanel = new JPanel();
		panelPlayerCards = new JPanel();
		panelDealerCards = new JPanel();
		
		basicPanel.setLayout( new FlowLayout() );
		panelPlayerCards.setLayout( new FlowLayout() );
		panelDealerCards.setLayout( new FlowLayout() );
		
		final JTextArea handDealer = new JTextArea(2,30);
		handDealer.setText( bj.dealerHandToString() );
		
		final JTextArea scoreDealer = new JTextArea(1, 2);
		scoreDealer.setText( bj.dealerScoreToString() );
		
		final JTextArea handPlayer = new JTextArea(2,30);
		handPlayer.setText( bj.playerHandToString() );
		
		final JTextArea scorePlayer = new JTextArea(1, 2);
		scorePlayer.setText( bj.playerScoreToString() );

		final JTextArea result = new JTextArea(2,30);
		result.setText( "Hagan sus apuestas" );
		
		final JTextArea chips = new JTextArea(1,6);
		chips.setText( Float.toString( (bj.getChips() )  ));
		
		final JSpinner betSpinner = new JSpinner();
		betSpinner.setValue( 2 );
		
		final JButton cardButton = new JButton( "Carta" );
		cardButton.setEnabled( false );
		
		final JButton actionButton = new JButton( "Nueva mano" );
		
		cardButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				boolean over21 = bj.dealCardToPlayer();
				handPlayer.setText( bj.playerHandToString() );
				scorePlayer.setText( bj.playerScoreToString() );
				
				newCardPanel();
				
				if( over21 ){
					actionButton.setText( "Nueva mano" );
					cardButton.setEnabled( false );
					result.setText( "Lo siento, se ha pasado de 21." );
				}
			}
		});
		
		actionButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (actionButton.getText() == "Plantarse"){
					
					int gameState = 9;
					boolean finishedGame = false;
					
					do {
						finishedGame = bj.tryFinishHand();
						handDealer.setText( bj.dealerHandToString() );
						scoreDealer.setText( bj.dealerScoreToString() );
					} while ( finishedGame == false );
					
					handDealer.setText( bj.dealerHandToString() );
					scoreDealer.setText( bj.dealerScoreToString() );

					newCardPanel();
					
					gameState = bj.getGameState();
					
					if ( gameState == 1){
						result.setText( "Enhorabuena, ha ganado." );
					} else if (gameState == -1) {
						result.setText( "Lo lamento, ha perdido." );
					} else if (gameState == 0){
						result.setText( "Hay un empate.");
					}
					
					chips.setText( Float.toString( (bj.getChips() )  ));
					
					actionButton.setText( "Nueva mano" );
					cardButton.setEnabled( false );
					
				} else if (actionButton.getText() == "Nueva mano") {

					bj.setBet( (Integer) betSpinner.getValue() );
					bj.newHand();
					handDealer.setText( bj.dealerHandToString() );
					scoreDealer.setText(  bj.dealerScoreToString()  );
					
					handPlayer.setText( bj.playerHandToString() );
					
					scorePlayer.setText( bj.playerScoreToString() );
					
					chips.setText( Float.toString( (bj.getChips() )  ));
					
					result.setText( "Hagan sus apuestas" );
										
					actionButton.setText( "Plantarse" );
					cardButton.setEnabled( true );
					
					System.out.println("dealer hand antes de newPanel: " + bj.dealerHandToString());
					
					newCardPanel();
				}
			}
		});
		
		basicPanel.add(handDealer);
		basicPanel.add(scoreDealer);
		basicPanel.add(handPlayer);
		basicPanel.add(scorePlayer);
		basicPanel.add(result);
		basicPanel.add(chips);
		basicPanel.add(betSpinner);
		
		basicPanel.add(cardButton);
		basicPanel.add(actionButton);
		
		myFrame = new JFrame("RainBoW Productions. Blackjack");
		
		myFrame.setLayout( new BorderLayout() );
		myFrame.add( basicPanel, BorderLayout.CENTER );
		
		myFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		myFrame.setVisible(true);
		myFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		myFrame.setLocationRelativeTo( null );
	}
	
	protected void newCardPanel() {
		
		myFrame.remove( panelDealerCards );
		myFrame.remove( panelPlayerCards );
		
		panelPlayerCards = new JPanel();
		panelDealerCards = new JPanel();
		
		handDealerToImage();
		handPlayerToImage();
		
		myFrame.add( panelDealerCards, BorderLayout.NORTH );
		myFrame.add( panelPlayerCards, BorderLayout.SOUTH );
		
		myFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		myFrame.setVisible(true);
		myFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		myFrame.setLocationRelativeTo( null );
	}

	private void handDealerToImage(){
		
		String[] arrayHand = bj.dealerHandToArray();
		
		for (int i = 0; i < bj.getNumberCardsDealer(); i++){
			System.out.println("arrayHand dealer: "+ arrayHand[i]);
			panelDealerCards.add( createJLabel( arrayHand[i]) );
		}
	}
	
	private void handPlayerToImage(){

		String[] arrayHand = bj.playerHandToArray();

		for (int i = 0; i < bj.getNumberCardsPlayer(); i++){
			panelPlayerCards.add( createJLabel( arrayHand[i]) );
		}
	}
	
	private JLabel createJLabel( String cardName ){
		Image img = null;
		try {
			img = ImageIO.read(GUI.class.getResource("/classic-cards/" + cardName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon cardIcon = new ImageIcon( img );
		JLabel cardImage = new JLabel( cardIcon );
		cardImage.setSize(72, 96);
		
		return cardImage;
	} 
}
