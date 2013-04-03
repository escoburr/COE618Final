
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Blackjack newGame = new Blackjack(2);
		GUI gui = new GUI( newGame );
		
		
		
		System.out.println( newGame.playerHandToString() );
		
		System.out.println( newGame.dealerHandToString() );
	}

}
