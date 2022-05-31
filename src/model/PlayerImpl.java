package model;

import model.bet.Bet;
import model.bet.BetResult;
import model.card.Hand;
import model.card.HandImpl;

/**
 * @author jarmsa implementation of player interface
 */
public class PlayerImpl implements Player {
	// instance variables
	private String id;
	private String name;
	private int points;
	private Bet bet;
	private Hand playerHand;

	public PlayerImpl(String id, String name, int points)
			throws java.lang.NullPointerException, java.lang.IllegalArgumentException {
		super();
		this.id = id;
		this.name = name;
		this.points = points;
		this.bet = Bet.NO_BET;
		this.playerHand = new HandImpl();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override // after bet
	public int getPoints() {
		return this.points;
	}

	@Override
	public int getTotalPoints() {
		return this.getPoints() + this.bet.getAmount();
	}

	@Override
	public void assignBet(Bet bet) {
		this.bet = bet;
		this.points = this.points - bet.getAmount();
	}

	@Override
	public Bet getBet() {
		return this.bet;
	}

	@Override
	public Hand getHand() {
		return this.playerHand;
	}

	@Override // a bit weird because the win includes the mulitplier so use current points not
				// total
	public void applyBetResult(Hand houseHand) {
		if (houseHand == null)
			return;
		// different from test client answer but i dont know i like it this way
		// my interpretation id you gamble 100 and you either loose it, keep it or win
		// something
		// based on clients eg is incorrect in my opinion
		BetResult br = getBet().finaliseBet(houseHand);
		int betOutcome = bet.getOutcome(br);
		if (br == BetResult.PLAYER_WIN)
			points = getTotalPoints() + betOutcome;
		else if (br == BetResult.PLAYER_LOSS)
			points = getPoints();
		else // if(br==BetResult.DRAW) a draw
			points = getTotalPoints();
	}

	@Override
	public void resetBet() {
		this.bet = Bet.NO_BET;
	}

	@Override
	public String toString() {
		return String.format("Player id=%s, name=%s, points=%s, %s, %s", id, name, points, bet, playerHand);
	}

}
