import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;


public class Dealer{
	
	private ArrayList<FrenchCard> hand;
	private int score, scoreWithAce;
	
	public Dealer(){
		this.hand = new ArrayList<FrenchCard>();
		this.score = 0;
		this.scoreWithAce = 0;
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
		String stringAux = new String("The dealer has: \n    ");
			for (int i=0; i<hand.size(); i++){
				switch ( hand.get(i).getValue() ) {
					case 1: stringAux += "A";
						break;
					case 11: stringAux += "J";
						break;
					case 12: stringAux += "Q";
						break;
					case 13: stringAux += "K";
						break;
	
					default: stringAux += Integer.toString(hand.get(i).getValue());
						break;
				}
				
				stringAux += "  ";
			}
			return stringAux;
	}

	
	public void dealCard( ArrayList<FrenchCard> tableCards ){

		FrenchCard cardAux = tableCards.get(0);
		
		score += Math.min(cardAux.getValue(), 10);		
		if (cardAux.getValue() == 1){
			scoreWithAce += 11;
		} else {
			scoreWithAce += Math.min(cardAux.getValue(), 10);
		}
		
		hand.add( cardAux );
		tableCards.remove(0);
	}
	
	public boolean finishHand( ArrayList<FrenchCard> tableCards ){
		
		boolean finish;
		
		dealCard( tableCards );
		if ( scoreWithAce > 17 && scoreWithAce < 22 ){
			finish = true;
		} else if (scoreWithAce > 21 && score > 16){
			finish = true;
		} else {
			finish = false;
		}
		return finish;
	}

	public void resetHand() {
		ArrayList<FrenchCard> newHand = new ArrayList<FrenchCard>();
		hand = newHand;
		score = 0;
		scoreWithAce = 0;
	}

	public int getSizeHand() {
		return hand.size();
	}
	
	public ArrayList<FrenchCard> getHand() {
		return hand;
	}
}