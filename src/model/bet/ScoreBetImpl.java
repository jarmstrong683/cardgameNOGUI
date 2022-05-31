/**
 * 
 */
package model.bet;

import model.Player;
import model.card.Hand;

/**
 * @author jarmsa
 *
 */
public class ScoreBetImpl extends BetImpl {


	public ScoreBetImpl(Player player, int amount) throws NullPointerException, IllegalArgumentException {
		super(player, amount);
	}

	@Override
	public BetResult finaliseBet(Hand houseHand) {
		int playerScore = super.getPlayer().getHand().getScore();
		int houseScore = houseHand.getScore();
		if (playerScore == houseScore) {
			result = BetResult.DRAW;
		}
		if (playerScore > houseScore) {
			result = BetResult.PLAYER_WIN;
		}
		if (playerScore < houseScore) {
			result = BetResult.PLAYER_LOSS;
		}
		return result;
	}
	@Override
	public int getMultiplier() {
		return 2;
	}

	@Override
	public String toString() {
		return String.format("Score Bet for %s", super.getAmount());
	}

}
