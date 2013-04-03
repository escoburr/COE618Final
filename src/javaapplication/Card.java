
public abstract class Card {

	protected String suit;
	protected int value;

	public String getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
	
	protected boolean equals( Card c ){
		if ( value == c.value && suit == c.suit )
			return true;
		else
			return false;
	}
	
}
