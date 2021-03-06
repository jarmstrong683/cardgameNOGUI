package model.card;

/**
 * This enum represent the suit for each card
 * 
 * <p>
 * The natural order of suits should be ascending alphabetically: clubs
 * (lowest), followed by diamonds, hearts, and spades (highest)
 * 
 * <p>
 * <b>Hint: </b>You may find it useful to override methods in the enum and/or on
 * each of the value.
 * 
 * <p>
 * <b>Hint: </b>Be sure to follow naming conventions for your enum values
 * 
 * <p>
 * <b>Note: </b> The {@link Suit#valueOf(String)} and {@link Suit#values()}
 * methods are provided by the API - you do not need to write or override them
 * yourself.
 * 
 * @author Ross Nye
 * 
 * @see model.card.Card
 * @see model.card.Rank
 *
 */
//enum for suit of cards ascending alphabetical order
public enum Suit {
	CLUBS("Clubs"), DIAMONDS("Diamonds"), HEARTS("Hearts"), SPADES("Spades");

	private String suit;

	@Override
	public String toString() {
		return suit;
	}

	// constructor to give each enum the value specified
	private Suit(String suit) {
		this.suit = suit;
	}
}
