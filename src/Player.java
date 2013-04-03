import java.util.ArrayList;

/*
 * player class
 */
public class Player {
	
	//player's hand
	private ArrayList<Card> hand = new ArrayList<Card>();
	//valueOfHand[0] includes the value with ace valued as 11
	//valueOfHand[1] includes the value with ace valued as 1
	private int[] valueOfHand;
	
	public Player() {
		valueOfHand = new int[2];
	}
	
	/*
	 * add card to hand and increase the value of the hand
	 */
	public void addCard(Card card) {
		boolean aceFound = false;

		//find out if the hand already has an ace
		if (getHand().size() > 0) {
			for (Card index : getHand()) {
				if (index.getFaceValue() == Card.FaceValue.ACE) {
					aceFound = true;
				}
			}			
		}
		//add card to hand
		getHand().add(card);
		//calculate the value of hand when ace interpreted as one or eleven
		if (card.getFaceValue() == Card.FaceValue.ACE) {
			//if hand already has an ace, interpret ace as one instead of 11
			if (aceFound) {
				valueOfHand[0] += 1;
			} else {
				valueOfHand[0] += 11;
			}
			valueOfHand[1] += 1;
		} else {
			//calculate other cards using their face values
			valueOfHand[0] += card.getFaceValue().getIntValue(); 	
			valueOfHand[1] += card.getFaceValue().getIntValue(); 
		}
	}
	
	/*
	 * get hand
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	/*
	 * get the value of the hand
	 */
	public int[] getValueOfHand () {
		return valueOfHand;
	}
	
	/*
	 * set the value of the hand
	 */
	public void setValueOfHand (int[] valueOfHand) {
		this.valueOfHand = valueOfHand;
	}
	
	/*
	 * print hand as a string 
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < this.hand.size(); i++) {
			sb.append(hand.get(i).getFaceValue().toString() + "of" + hand.get(i).getSuit().toString() + " ");
		}
		return sb.toString();
	}
	
}
