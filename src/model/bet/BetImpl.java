package model.bet;

import model.Player;
import model.card.Hand;

public abstract class BetImpl implements Bet{
	private Player player;
	private int amount;
	BetResult result;

	public BetImpl(Player player, int amount) throws NullPointerException, IllegalArgumentException {
		// throw nullException if player is null
		if (player == null)
			throw new NullPointerException("Can't Bet, player is null.");
		if (amount <= 0)
			throw new IllegalArgumentException("Can't bet, not a valid amount.");
		if (amount > player.getTotalPoints())
			throw new IllegalArgumentException("Can't bet, not enough points.");

		this.player = player;
		this.amount = amount;
		result = BetResult.UNDETERMINED;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}

	@Override
	public abstract int getMultiplier();

	@Override
	public abstract BetResult finaliseBet(Hand houseHand);

	@Override
	public BetResult getResult() {
		return this.result;
	}

	@Override
	public int getOutcome() {
		int amount = 0;
		if (getResult() == BetResult.DRAW)
			amount = 0;
		if (getResult() == BetResult.PLAYER_WIN)
			amount = getMultiplier() * getAmount();
		if (getResult() == BetResult.PLAYER_LOSS)
			amount = -1 * getAmount();
		if (getResult() == BetResult.UNDETERMINED)
			amount = 0;
		return amount;
	}

	@Override
	public int getOutcome(BetResult aresult) {
		int amount = 0;
		if (aresult == BetResult.DRAW)
			amount = 0;
		if (aresult == BetResult.PLAYER_WIN)
			amount = getMultiplier() * getAmount();
		if (aresult == BetResult.PLAYER_LOSS)
			amount = -1 * getAmount();
		if (aresult == BetResult.UNDETERMINED)
			amount = 0;
		return amount;
	}

	@Override
	public int compareTo(Bet bet) {
		return this.getAmount() - bet.getAmount();
	}

	@Override
	public abstract String toString();

}
