/**
 * 
 */
package model.card;

import java.util.Objects;

/**
 * @author jarmsa
 *
 */
public class CardImpl implements Card {

	private Suit suit;
	private Rank rank;

	// constructor
	public CardImpl(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	@Override
	public Suit getSuit() {
		// return the suit of the card
		return this.suit;
	}

	@Override
	public Rank getRank() {
		// return the rank of the card
		return this.rank;
	}

	// return the value of the card
	@Override
	public int getValue() {
		return this.rank.getRankValue();
	}

	@Override
	// if suits are the same compare rank enums
	// otherwise compare suit enums
	public int compareTo(Card card) {
		// use compareTo of enums to return answer per spec
		// if the same suit then compare rank and return comparison
		if (this.getSuit() == card.getSuit()) {
			return this.rank.compareTo(card.getRank());
		} else
			return this.suit.compareTo(card.getSuit());
	}

	@Override
	public String toString() {
		return String.format("%s of %s", getRank(), getSuit());
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, suit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CardImpl)) {
			return false;
		}
		CardImpl other = (CardImpl) obj;
		return rank == other.rank && suit == other.suit;
	}

}
