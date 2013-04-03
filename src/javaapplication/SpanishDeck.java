
import java.util.ArrayList;
import java.util.Collections;

public class SpanishDeck implements Deck{

	ArrayList<SpanishCard> deck;
	final int TOTAL_CARDS = 40;
	
	public SpanishDeck(){
		deck = new ArrayList<SpanishCard>(TOTAL_CARDS);
		for (int i = 0; i<10; i++){
			deck.add( new SpanishCard("oros", i+1) );
			deck.add( new SpanishCard("copas", i+1) );
			deck.add( new SpanishCard("bastos", i+1) );
			deck.add( new SpanishCard("espadas", i+1) );
		}
	}
	
	public SpanishCard getFirst(){
		return deck.get(0);
	}
	
	public void burnFirst(){
		SpanishCard aux;
		aux = getFirst();
		deck.remove(0);
		deck.add(aux);
	}
	
	public void printByConsole(){
		for (int i=0; i<deck.size(); i++){
			System.out.print( "Posici—n " + i + ": " );
			System.out.println( deck.get(i).toString() );
		}
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}

	public SpanishCard getAndRemoveFirst(){
		SpanishCard aux;
		aux = deck.get(0);
		deck.remove(0);
		return aux;
	}
	
}
