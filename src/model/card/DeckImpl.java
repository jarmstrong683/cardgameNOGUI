/**
 * 
 */
package model.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jarmsa
 *
 */
public class DeckImpl implements Deck {
	// A deck is just a collection of cards. starts at 52
	private List<Card> cards;

	// private constuctor used to assign create deck methods to card collection
	private DeckImpl(List<Card> cards) {
		super();
		this.cards = cards;
	}

	// factory method traverse enums in order and add to deck.
	// then call private constructor
	public static Deck createSortedDeck() {
		List<Card> deckOfCards = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deckOfCards.add(new CardImpl(suit, rank));
			}
		}
		/*for (Card aCard :deckOfCards ) {
			System.out.println(aCard);
		}*/
		return new DeckImpl(deckOfCards);
	}

	// create sorted deck the suffle them
	public static Deck createShuffledDeck() {
		Deck aDeck = createSortedDeck();
		aDeck.shuffleDeck();
		return aDeck;
	}

	@Override // next card is the last card. remove last element
	public Card removeNextCard() throws IllegalStateException {
		if (cardsInDeck() == 0)
			throw new IllegalStateException("sorry,no more cards to remove");
		return cards.remove(cardsInDeck() - 1);
	}

	@Override
	public int cardsInDeck() {
		return this.cards.size();
	}

	@Override
	public void shuffleDeck() {
		Collections.shuffle(cards);
	}

	@Override
	public String toString() {
		return String.format("DeckImpl [cards=%s]", cards);
	}
}
