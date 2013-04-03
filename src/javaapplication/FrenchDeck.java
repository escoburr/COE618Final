
import java.util.ArrayList;
import java.util.Collections;

public class FrenchDeck implements Deck{
	
	ArrayList<FrenchCard> deck;
	final int TOTAL_CARDS = 52;
	
	public FrenchDeck(){
		deck = new ArrayList<FrenchCard>(TOTAL_CARDS);
		for (int i=1; i<14; i++){
			deck.add( new FrenchCard("spades", i) );
			deck.add( new FrenchCard("clubs", i) );
			deck.add( new FrenchCard("hearts", i) );
			deck.add( new FrenchCard("diamonds", i) );
		}
	}

	public FrenchCard getFirst(){
		return deck.get(0);
	}
	
	public void burnFirst(){
		FrenchCard aux;
		aux = getFirst();
		deck.remove(0);
		deck.add(aux);
	}
	
	public void printByConsole(){
		for (int i=0; i<deck.size(); i++){
			System.out.print( "Position " + i + ": " );
			System.out.println( deck.get(i).toString() );
		}
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public FrenchCard getAndRemoveFirst(){
		FrenchCard aux;
		aux = deck.get(0);
		deck.remove(0);
		return aux;
	}
	
}
