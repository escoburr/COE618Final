import java.util.ArrayList;


public class Player{
	//0 playing/tie, -1 game lost, 1 win, 2 win with blackjack
	private int gameState = 0;
	private ArrayList<FrenchCard> hand;
	private int score, scoreWithAce;
	private int bet;
	private float chips;
	
	public Player(){
		score = 0;
		gameState = 9;
		scoreWithAce = 0;
		chips = 1000;
		hand = new ArrayList<FrenchCard>();
	}
	
	public int getScore(){
		return score;
	}
	
	public int getScoreWithAce(){
		return scoreWithAce;
	}
	
	public int getMaxScore(){
		int max;
		if (scoreWithAce < 22){
			max = scoreWithAce;
		} else if (score < 22) {
			max = score;
		} else {
			max = -1;
		}
		
		return max;
	}
	
	public String handToString(){
		String out = new String("Player 1 has: \n    ");
			for (int i=0; i<hand.size(); i++){
				switch ( hand.get(i).getValue() ) {
					case 1: out += "A";
						break;
					case 11: out += "J";
						break;
					case 12: out += "Q";
						break;
					case 13: out += "K";
						break;
	
					default: out += Integer.toString(hand.get(i).getValue());
						break;
				}
				
				out += "  ";
			}
			return out;
	}
	
	public void dealCard( ArrayList<FrenchCard> tableCards){
		FrenchCard auxCard = tableCards.get(0);
		
		score += Math.min(auxCard.getValue(), 10);		
		if (auxCard.getValue() == 1){
			scoreWithAce += 11;
		} else {
			scoreWithAce += Math.min(auxCard.getValue(), 10);
		}
		
		hand.add( auxCard );
		tableCards.remove(0);
	}

	public void gameOver(){
		gameState = -1;
	}
	
	public int getGameState(){
		return gameState;
	}
	
	public void setGameState( int newGameState ){
		gameState = newGameState;
	}
	
	public boolean isGameOver() {
		return gameState == -1;
	}

	//thanks to the var result, we avoid resolve bets in a unsuitable moment
	public void solveBet( int result ) {
		if ( result == 9 ){
			chips += bet;
		} else if (result == 1){
			chips += bet * 2;
		} else if (result == 2){
			chips += bet * 2.5;
		}
	}

	public float getChips() {
		return chips;
	}
	
	public void setBet( int newBet ){
		bet = newBet;
	}
	
	public int getBet(){
		return bet;
	}

	public void resetPlayerCards() {
		ArrayList<FrenchCard> newHand = new ArrayList<FrenchCard>();
		hand = newHand;
		score = 0;
		scoreWithAce = 0;
	}

	public void newHand() {
		resetPlayerCards();
		chips -= bet;
	}

	public int getSizeHand() {
		return hand.size();
	}
	
	public ArrayList<FrenchCard> getHand() {
		return hand;
	}
}
