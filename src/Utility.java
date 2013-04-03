
/*
 * utility class, includes methods to decide the winner
 */
public class Utility {
	
	//blackjack value
	static final int BLACK_JACK = 21;
	//who is the winner
	public enum Winner {PLAYER, DEALER, TIE}
	
	/*
	 * determine winner by selecting the biggest hand from player's and dealer's hands.
	 * Four hands altogether (player: ace=1, ace=11) (dealer: ace=1, ace=11) 
	 */
	public Winner determineWinner (Player player, Player dealer) {
		int biggestPlayerHand = 0;
		int biggestDealerHand = 0;
		//find the biggest of the player's hands under 21
		if (player.getValueOfHand()[0] > BLACK_JACK) {
			biggestPlayerHand = player.getValueOfHand()[1];
		} else if (player.getValueOfHand()[1] > BLACK_JACK) {
			biggestPlayerHand = player.getValueOfHand()[0];
		} else if (player.getValueOfHand()[0] >= player.getValueOfHand()[1]) {
			biggestPlayerHand = player.getValueOfHand()[0];
		} else {
			biggestPlayerHand = player.getValueOfHand()[1];
		}
		//find the biggest of the dealer's hands under 21
		if (dealer.getValueOfHand()[0] > BLACK_JACK) {
			biggestDealerHand = dealer.getValueOfHand()[1];
		} else if (dealer.getValueOfHand()[1] > BLACK_JACK) {
			biggestDealerHand = dealer.getValueOfHand()[0];
		} else if (dealer.getValueOfHand()[0] >= dealer.getValueOfHand()[1]) {
			biggestDealerHand = dealer.getValueOfHand()[0];
		} else {
			biggestDealerHand = dealer.getValueOfHand()[1];
		}
		//find the biggest hand
		//tie
		if (biggestPlayerHand == biggestDealerHand) {
			return Winner.TIE;
		//player is the winner
		} else if (biggestPlayerHand > biggestDealerHand) {
			return Winner.PLAYER;
		//dealer is the winner
		} else {
			return Winner.DEALER;
		}

	}
	
	/*
	 * check if the player has a blackjack.
	 * This is checked after the first two cards are dealt
	 */
	public boolean checkBlackJack (Player player) {
		if ((player.getValueOfHand()[0] == BLACK_JACK) || (player.getValueOfHand()[1] == BLACK_JACK)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * check if player bust (over 21)
	 * this is checked after hit button event
	 */
	public boolean checkBust(Player player) {
		if ((player.getValueOfHand()[0] > BLACK_JACK) && (player.getValueOfHand()[1] > BLACK_JACK)) {
			return true;	
		} else {
			return false;
		}
	}
	
}
