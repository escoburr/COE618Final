import java.util.ArrayList;


public class Human {
	
	private ArrayList<FrenchCard> hand;
	private int score, scoreWithAce;
	
	public Human(){
		hand = new ArrayList<FrenchCard>();
		score = 0;
		scoreWithAce = 0;
	}
}
