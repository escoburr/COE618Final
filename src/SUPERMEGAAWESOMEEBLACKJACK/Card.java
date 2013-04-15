package SUPERMEGAAWESOMEEBLACKJACK;

import java.awt.Image;

/**
 * Standard playing card (4 suits and 13 face values) 
 * @author ytwytw
 */
public class Card {
    /**
     * Define Suit 
     */
    public enum Suit {
        /**
         *
         */
        CLUBS,
        /**
         *
         */
        SPADES,
        /**
         *
         */
        HEARTS,
        /**
         *
         */
        DIAMONDS}
	
    /**
     * Define Value of the card number
     */
    public enum FaceValue {
        /**
         *
         */
        ACE(1), 
        /**
         *
         */
        KING(10), 
        /**
         *
         */
        QUEEN(10), 
        /**
         *
         */
        JACK(10), 
        /**
         *
         */
        TEN(10), 
        /**
         *
         */
        NINE(9), 
        /**
         *
         */
        EIGHT(8), 
        /**
         *
         */
        SEVEN(7),
        /**
         *
         */
        SIX(6),
        /**
         *
         */
        FIVE(5),
        /**
         *
         */
        FOUR(4),
        /**
         *
         */
        THREE(3),
        /**
         *
         */
        TWO(2);
        private int intValue;
        FaceValue(int intValue) {
            this.intValue = intValue;
        }
		
        /**
         * This returns the value of the card 
         * @return
         */
        public int getIntValue() {
			return this.intValue;
		}
	}
	
	private Suit suit;
	private FaceValue faceValue;
	private Image image;
	
    /**
     * image of the card
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
