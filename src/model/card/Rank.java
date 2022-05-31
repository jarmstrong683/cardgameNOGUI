package model.card;

/**
 * This enum represent the rank for each card
 * 
 * <p>
 * The natural order of rank should be Ace, 2, 3, ... 9, 10, Jack, Queen, King
 * 
 * <p>
 * <b>Note: </b>You must provide the method {@link Rank#getRankValue()} in the
 * enum and/or for each of it's values.
 * 
 * <p>
 * <b>Hint: </b>You may find it useful to override methods in the enum and/or on
 * each of the value.
 * 
 * <p>
 * <b>Hint: </b>Be sure to follow naming conventions for your enum values
 * 
 * <p>
 * <b>Note: </b> The {@link Rank#valueOf(String)} and {@link Rank#values()}
 * methods are provided by the API - you do not need to write or override them
 * yourself.
 * 
 * @author Ross Nye
 * 
 * @see model.card.Card
 * @see model.card.Suit
 *
 */
public enum Rank {
	// create enums in ascending order of value
	ACE("Ace", 1), TWO("TWO", 2), THREE("THREE", 3), FOUR("FOUR", 4), FIVE("FIVE", 5), SIX("SIX", 6), SEVEN("SEVEN", 7), EIGHT("EIGHT", 8),
	NINE("NINE", 9), TEN("TEN", 10), JACK("Jack", 10), QUEEN("Queen", 10), KING("King", 10);

	private String name;
	private int value;

	// constructor to give each enum the value specified and name
	private Rank(String name, int value) {
		this.value = value;
		this.name = name;
	}

	// returns the the value of the rank
	public int getRankValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return name;
	}
}
