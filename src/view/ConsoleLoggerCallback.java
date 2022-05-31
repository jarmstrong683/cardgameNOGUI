package view;

import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.GameEngine;
import model.GameEngineImpl;
import model.Player;
import model.bet.Bet;
import model.card.Card;
import model.card.Deck;
import model.card.Hand;

/**
 * An implementation of GameCallback which uses a Logger to log game events to
 * the console.
 * 
 * <p>
 * <b>Important!</b> DO NOT EDIT THE STATIC BLOCK THAT SETS UP THE LOGGER OR
 * IT'S DECLARATION!
 * 
 * <p>
 * <b>Note:</b> Logging message format should be consistent with the output
 * trace.
 * 
 * @author Ross Nye
 * 
 * @see view.GameCallback
 * @see view.GameCallbackCollection
 *
 */
public class ConsoleLoggerCallback implements GameCallback {
	/**
	 * A static {@link java.util.logging.Logger} object used for logging information
	 * (in this case to the console)
	 * 
	 * DO NOT EDIT!
	 */
	public static final Logger LOGGER;

	static {
		// DO NOT EDIT THIS STATIC BLOCK!!

		// Creating consoleHandler, add it and set the log levels.
		LOGGER = Logger.getLogger(ConsoleLoggerCallback.class.getName());
		LOGGER.setLevel(Level.FINER);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);
		LOGGER.setUseParentHandlers(false);
	}
	// get the reference of the game engine so we can have access to players etc
	final private GameEngine testEngine;

	// GameEngine engine
	public ConsoleLoggerCallback(GameEngine engine) {
		engine = new GameEngineImpl();
		// assign the reference to final variable
		testEngine = engine;
	}

	@Override
	public void addPlayer(Player player) {
		testEngine.addPlayer(player);
		LOGGER.info(String.format("Added Player %s", player));
	}

	@Override
	public void removePlayer(Player player) {
		testEngine.removePlayer(player.getId());
		LOGGER.info(String.format("Removed Player %s", player));
	}

	@Override
	public void betUpdated(Player player) {

		LOGGER.info(String.format("Bet updated for %s to %s", player.getId(), player.getBet()));
	}

	@Override
	public void newDeck(Deck deck) {

		LOGGER.info(String.format("A new deck of cards was created with %s cards", deck.cardsInDeck()));
	}

	@Override
	public void playerCard(Player player, Card card) {

		LOGGER.fine(String.format("Player %s dealt %s", player.getId(), card));
	}

	@Override
	public void playerBust(Player player, Card card) {
		// testEngine
		LOGGER.fine(String.format("Player %s bust on %s", player.getId(), card));
		LOGGER.info(String.format("Player %s score is %s", player.getId(), player.getHand().getScore()));
	}

	@Override
	public void houseCard(Hand houseHand, Card card) {
		LOGGER.fine(String.format("House is dealt %s", card));
	}

	@Override
	public void houseBust(Hand houseHand, Card card) {
		LOGGER.fine(String.format("House bust on %s", card));
		LOGGER.info(String.format("House Hand: %s", houseHand));
		Collection<Player> people = testEngine.getAllPlayers();
		// menu to print final test
		String resultsString = String.format("Final Results:\n");
		for (Player p : people) {
			resultsString += String.format("%s\n", p);
			if (p.getBet() == Bet.NO_BET)
				resultsString += String.format("Player: %-12s%-13s%10s\n", p.getId(), p.getName(), p.getBet());
			else
				resultsString += String.format("Player: %-13s%-13s%-13s%13s\n", p.getId(), p.getName(),
						p.getBet().getResult(), p.getBet().getOutcome());
		}
		LOGGER.info(resultsString);
	}
}
