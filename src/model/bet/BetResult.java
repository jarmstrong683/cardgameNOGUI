package model.bet;

/**
 * This enum is used to specify the result of a Bet and includes the four values
 * UNDETERMINED, PLAYER_LOSS, DRAW, PLAYER_WIN. The first of these values should
 * be the default results for all bets until the results are determined by
 * calling {@link model.bet.Bet#finaliseBet(model.card.Hand)}
 * 
 * <p>
 * <b>Hint: </b> You may find it useful to override methods in the enum and/or
 * on each of the value.
 * 
 * <p>
 * <b>Hint: </b>Be sure to follow naming conventions for your enum values
 * 
 * <p>
 * <b>Note: </b> The {@link BetResult#valueOf(String)} and
 * {@link BetResult#values()} methods are provided by the API - you do not need
 * to write or override them yourself.
 * 
 * @author Ross Nye
 *
 */
public enum BetResult {
	UNDETERMINED("Undetermined"), PLAYER_LOSS("Player Lose"), DRAW("Draw"), PLAYER_WIN("Player Win");

	private String result;

	@Override
	public String toString() {
		return result;
	}

	// constructor to give each enum the value specified
	private BetResult(String result) {
		this.result = result;
	}
	// you must complete the rest of the enum values
}
