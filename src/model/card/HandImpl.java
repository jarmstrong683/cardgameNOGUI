/**
 * 
 */
package model.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * @author jarmsa
 *
 */
public class HandImpl implements Hand {
	// a hand is just a collection of cards
	private Collection<Card> cards;

	public HandImpl() {
		cards = new ArrayList<Card>();
	}

	@Override // add the card to collection if it wont cause the user to go bust
	public boolean dealCard(Card card) {
		if ((card.getValue() + this.getScore()) <= Hand.BUST_SCORE) {
			this.cards.add(card);
			return true;
		} else
			return false;
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public int getNumberOfCards() {
		return cards.size();
	}

	@Override // return total value of cards as per definition
	public int getScore() {
		int score = 0;
		if (isEmpty())
			return 0;
		for (Card aCard : this.cards)
			score += aCard.getValue();
		return score;
	}

	@Override // return how many cards of a suit the caller has
	public int getSuitCount(Suit suit) {
		int amount = 0;
		if (suit == null) {
			return 0;
		}
		for (Card aCard : this.cards)
			if (aCard.getSuit() == suit)
				amount++;
		return amount;
	}

	@Override
	public Collection<Card> getCards() {
		return Collections.unmodifiableCollection(cards);
	}

	@Override
	public void reset() {
		cards.clear();
	}

	@Override
	public String toString() {
		if (cards.isEmpty()) {
			return String.format("Empty Hand");
		}
		return String.format("Hand of %s cards %s Score: %s", getNumberOfCards(), getCards(), getScore());
	}

	// wasn't allowed to override the hash as would pass the validator
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof HandImpl)) {
			return false;
		}
		HandImpl other = (HandImpl) obj;
		return Objects.equals(cards, other.cards);
	}
}
