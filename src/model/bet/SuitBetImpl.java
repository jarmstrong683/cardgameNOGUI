/**
 * 
 */
package model.bet;

import model.Player;
import model.card.Hand;
import model.card.Suit;

/**
 * @author jarmsa
 *
 */
public class SuitBetImpl extends BetImpl implements SuitBet{
	
	private Suit suit;

	public SuitBetImpl(Player player, int amount, Suit suit) throws NullPointerException, IllegalArgumentException {
		super( player,  amount);
		this.suit = suit;
	}

	@Override
	public int getMultiplier() {
		return 4;
	}
	@Override
	public BetResult finaliseBet(Hand houseHand) {
		int playerScore = super.getPlayer().getHand().getSuitCount(getSuit());
		int houseScore = houseHand.getSuitCount(getSuit());
		if (playerScore > houseScore)
			result = BetResult.PLAYER_WIN;
		if (playerScore <= houseScore)
			result = BetResult.PLAYER_LOSS;
		return result;
	}
	
	@Override
	public Suit getSuit() {
		return this.suit;
	}

	@Override
	public String toString() {
		return String.format("Suit Bet for %s on %s", super.getAmount(), getSuit());
	}
}
