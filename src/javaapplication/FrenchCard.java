
public class FrenchCard extends Card {
	
	public FrenchCard(String suit, int value){
		if (suit=="spades" || suit=="hearts" || suit=="diamonds" || suit=="clubs")
			if (value<14 && value>0){
				this.suit = suit; this.value = value;
		}
	}

	public boolean equals( Card c ) {
		if (c instanceof FrenchCard){
			super.equals(c);
		}
		return false;
	}
	
	public String toString(){
		String valueString = "";
		switch (value){
			case 1: valueString = "Ace"; break;
			case 11: valueString = "J"; break;
			case 12: valueString = "Q"; break;
			case 13: valueString = "K"; break;
			default: valueString = Integer.toString( value );
		}
		return valueString + " of " + suit;
	}
}
