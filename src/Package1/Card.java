package Package1;

/*
 /* Standard playing card (4 suits and 13 face values) 
 */
import java.awt.Image;

/**
 *
 * @author ytwytw
 */
public class Card {
	/**
         * define card
         * /
     *
     */
    public enum Suit {
        CLUBS,
        SPADES,
        HEARTS,
        DIAMONDS}
	
	/**
     *
     */
    public enum FaceValue {
		/**
                 * 
                 * define suit
         *
         */
        ACE(1), KING(10), 
        QUEEN(10), 
        JACK(10), 
        TEN(10), 
        NINE(9), 
        EIGHT(8), SEVEN(7),
        SIX(6),
        FIVE(5),
        FOUR(4),
        THREE(3),
        TWO(2);
		private int intValue;
	
		FaceValue(int intValue) {
			this.intValue = intValue;
		}
		
		/**
         *
         * @return
         */
        public int getIntValue() {
			return this.intValue;
		}
	}
	
	private Suit suit;
	private FaceValue faceValue;
	//image of the card
	private Image image;
	
	/**
     *
     * @param suit
     * @param faceValue
     * @param image
     */
    public Card(Suit suit, FaceValue faceValue, Image image) {
		this.suit = suit;
		this.faceValue = faceValue;
		this.image = image;
	}

	/**
	 * get suit
         * 
         * @return 
         */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * set suit
         * 
         * @param suit 
         */
	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	/**
	 * get faceValue
         * 
         * @return 
         */
	public FaceValue getFaceValue() {
		return faceValue;
	}

	/**
	 * set FaceValue
         * 
         * @param faceValue 
         */
	public void setFaceValue(FaceValue faceValue) {
		this.faceValue = faceValue;
	}

	/**
	 * return image
         * 
         * @return 
         */
	public Image getImage() {
		return image;
	}

	/**
	 * set image
         * 
         * @param image 
         */
	public void setImage(Image image) {
		this.image = image;
	}
	
}
