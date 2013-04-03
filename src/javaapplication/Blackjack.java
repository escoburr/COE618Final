
import java.util.*;

public class Blackjack {
	
	private int numberOfDecks = 0;
	
	private int cardsInDeck = 0;
	private ArrayList<FrenchCard> tableCards;
	
	private Dealer dealer;
	private Player player1;
	
	public Blackjack(int decks){
		
		numberOfDecks = decks;
		
		newGame();
		
	}
	
	public String playerHandToString(){
		return player1.handToString();
	}
	
	public String dealerHandToString(){
		return dealer.handToString();
	}
	
	public String[] playerHandToArray(){
		
		String[] array = new String[ player1.getSizeHand() ];
		int index = 0;
		String stringAux;
		for(int i = 0; i < player1.getHand().size(); i++){	
			stringAux = Integer.toString( player1.getHand().get(i).getValue() );
			stringAux += player1.getHand().get(i).getSuit().charAt(0);
			array[index] = stringAux;
			index++;
		}
		
		return array;
	}
	
	public String[] dealerHandToArray(){
			
		String[] array = new String[ dealer.getSizeHand() ];
		int index = 0;
		String stringAux;
		
		for(int i = 0; i < dealer.getHand().size(); i++){	
			stringAux = Integer.toString( dealer.getHand().get(i).getValue() );
			stringAux += dealer.getHand().get(i).getSuit().charAt(0);
			array[index] = stringAux;
			index++;
		}
		
		for (int i = 0; i < array.length; i++){
			System.out.println("\tcontenido hand to array dealer it=" + i + ": " + array[i]);
		}
		
		return array;
	}
	
	public boolean dealCardToPlayer(){
		player1.dealCard(tableCards);
		cardsInDeck--;
		
		return ( player1.getMaxScore() == -1 );
	}
	
	public void dealCardToDealer(){
		dealer.dealCard( tableCards );
		cardsInDeck--;
	}
	
	public String playerScoreToString(){
		String out;
		out = Integer.toString(player1.getScore()) + "/" + Integer.toString( player1.getScoreWithAce() );
		return out;
	}
	
	public String dealerScoreToString(){
		String out;
		out = Integer.toString(dealer.getScore()) + "/" + Integer.toString( dealer.getScoreWithAce() );
		return out;
	}

	public int getDealerScore() {
		return dealer.getScore();
	}
	
	public int getDealerScoreWithAce() {
		return dealer.getScoreWithAce();
	}
	
	public int getPlayerScore() {
		return player1.getScore();
	}
	
	public int getPlayerScoreWithAce() {
		return player1.getScoreWithAce();
	}
	
	public boolean  tryFinishHand(){
		
		boolean finish = dealer.finishHand( tableCards );
		
		if (finish == true){
			checkScore();
		}
			
		return finish;
	}
	
	public void checkScore(){
		
		int result;

		if ( player1.getMaxScore() > dealer.getMaxScore() ){
			result = 1;
		} else if ( player1.getMaxScore() < dealer.getMaxScore() ){
			result = -1;
		} else if ( player1.getMaxScore() == 21 && (dealer.getMaxScore() < 21) ){
			//blackjack
			result = 2;
		} else {
			result = 9;
		}
		
		player1.setGameState( result );
		player1.solveBet( result );
	}

	public int getGameState(){
		return player1.getGameState();
	}
	
	public void newGame() {
		player1 = new Player();
		dealer = new Dealer();
				
		tableCards = new ArrayList<FrenchCard>();
		cardsInDeck = 52 * numberOfDecks;

		for (int i=0; i<numberOfDecks; i++){ 
			FrenchDeck barajaAux1 = new FrenchDeck();
			barajaAux1.shuffle();
			for (int j=0; j<barajaAux1.TOTAL_CARDS; j++){
				FrenchCard cartaAux = barajaAux1.getAndRemoveFirst();
				tableCards.add(cartaAux);
			}
		}
		Collections.shuffle( tableCards );
		
//		darCartaJugador();
//		darCartaBanca();
	}
	
	public boolean isGameOver(){
		return player1.isGameOver();
	}
	
	public void gameOver(){
		player1.gameOver();
	}

	public float getChips() {
		return player1.getChips();
	}

	public void newHand() {
		player1.newHand();
		dealer.resetHand();
		dealer.dealCard( tableCards );
	}

	public void setBet( int newBet ) {
		player1.setBet( newBet );
	}

	public int getNumberCardsDealer() {
		return dealer.getSizeHand();
	}
	
	public int getNumberCardsPlayer(){
		return player1.getSizeHand();
	}

}
