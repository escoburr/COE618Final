
public class SpanishCard extends Card{
	
	public SpanishCard( String suit, int value ){
		if (suit=="oros" || suit=="espadas" || suit=="copas" || suit=="bastos")
			if (value<11 && value>0){
				this.suit = suit; this.value = value;
		}
	}

	public boolean equals(Card c) {
		if (c instanceof SpanishCard){
			super.equals(c);
		}
		return false;
	}
	
	public String toString(){
		String valueString;
		switch (value){
			case 1: valueString = "As"; break;
			case 8: valueString = "Sota"; break;
			case 9: valueString = "Caballo"; break;
			case 10: valueString = "Rey"; break;
			default: valueString = Integer.toString( value );
		}
		return valueString + " de " + suit;
	}

}
