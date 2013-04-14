package Package1;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Deck with 52 cards without jokers 
 * @author ytwytw
 */
public class Deck {
	
	static final int MAX = 52;	
	//ArrayList deck
	private ArrayList<Card> deck = new ArrayList<Card>();

    /**
     * initiate and create the deck of 52 cards
     * 
     */
    public Deck() {
		int index = 0;	
		for(Card.FaceValue faceValue : Card.FaceValue.values())
			for(Card.Suit suit: Card.Suit.values()) {
				index++;
				deck.add(new Card(suit, faceValue, new ImageIcon("pictures/" + index + ".png").getImage()));	
			}	
	}
	
    /**
     * deal a card from a deck
     * @return
     */
    public Card dealCard() {
		//remove dealed card from a deck 
		int index = (int)(Math.random()*deck.size()); 
		return deck.remove(index);		
	}
			
}
