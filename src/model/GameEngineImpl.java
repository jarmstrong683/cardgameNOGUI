package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.bet.Bet;
import model.bet.ScoreBetImpl;
import model.bet.SuitBetImpl;
import model.card.Card;
import model.card.Deck;
import model.card.DeckImpl;
import model.card.Hand;
import model.card.HandImpl;
import model.card.Suit;
import view.GameCallback;
import view.GameCallbackCollection;

/**
 * @author jarmsa implementation of GameEngine interface
 */
public class GameEngineImpl implements GameEngine, GameCallbackCollection {
	// holds all the players including dealer? string player.id
	Collection<Player> players;
	private Collection<GameCallback> cbs;
	private Hand dealersHand;
	private Deck aDeck;

	// constructor
	public GameEngineImpl() {
		players = new ArrayList<Player>();
		cbs = new ArrayList<GameCallback>();
		dealersHand = new HandImpl();
		aDeck = null;
	}

	@Override
	public void registerCallback(GameCallback callBack) {
		cbs.add(callBack);
	}

	@Override
	public void removeCallback(GameCallback callBack) {
		cbs.remove(callBack);
	}

	// adds a player to the arraylist players
	// throws nullpointerException if player is null,
	// throws illegalArgumentException if player already exist
	@Override
	public void addPlayer(Player player) throws NullPointerException, IllegalArgumentException {
		// throw nullException if player is null
		if (player == null)
			throw new NullPointerException("Can't add, player is null.");
		// if player already exist throw illegal argumentException
		if (findPlayer(player.getId()) != null)
			throw new IllegalArgumentException("Can't add, player id already exist.");
		// if suitable to add then add to players list
		this.players.add(player);
		for (GameCallback cb : cbs) {
			cb.addPlayer(player);
		}
	}

	// removes a player with the a supplied player id from arraylist players
	// throws nullpointerException if player id is null,
	// throws illegalArgumentException if no player exist in the list
	@Override
	public void removePlayer(String playerId) throws NullPointerException, IllegalArgumentException {
		// throw nullException if player is null
		if (playerId == null)
			throw new NullPointerException("Can't remove, player id is null.");
		Player player = findPlayer(playerId);
		// if player.id match was not found, throw illegal argumentException
		if (player == null) {
			throw new IllegalArgumentException("Can't remove, player does not exist.");
		}
		// if a player with correct playerId is found then remove player;
		this.players.remove(player);
		for (GameCallback cb : cbs) {
			cb.removePlayer(player);
		}
	}

	// return the list of player/// shallow copy
	@Override
	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(players);
		// return players.;
	}

	// places a bet for the supplied amount and throws null pointer if playerid is
	// null
	// throws illegalargument if playerid is not found in players
	// or supplied amount is not a positive number
	// or player does not have enough points
	// when replacing bet, the bet is not greater than exisiting bet
	@Override
	public void placeBet(String playerId, int amount) throws NullPointerException, IllegalArgumentException {
		if (!canBet(playerId, amount))
			return;
		Player aPlayer = findPlayer(playerId);
		Bet abet = new ScoreBetImpl(aPlayer, amount);
		// abet.getOutcome();
		aPlayer.assignBet(abet);
		for (GameCallback cb : cbs) {
			cb.betUpdated(aPlayer);
		}
	}

	@Override
	public void placeBet(String playerId, int amount, Suit suit) throws NullPointerException, IllegalArgumentException {
		if (!canBet(playerId, amount))
			return;
		if (suit == null)
			throw new NullPointerException("Can't place bet, suit id is null.");
		Player aPlayer = findPlayer(playerId);
		Bet abet = new SuitBetImpl(aPlayer, amount, suit);
		aPlayer.assignBet(abet);
		for (GameCallback cb : cbs) {
			cb.betUpdated(aPlayer);
		}

	}

	// helper that checks if the player is capable of betting ie money id etc
	private boolean canBet(String playerId, int amount) throws NullPointerException, IllegalArgumentException {
		boolean betOK = false;
		if (playerId == null)
			throw new NullPointerException("Can't place bet, player id is null.");
		Player aPlayer = findPlayer(playerId);
		// if player.id match was not found, throw illegal argumentException
		if (aPlayer == null) {
			throw new IllegalArgumentException("Can't place bet, player does not exist.");
		}
		// must bet a positive amount of money
		if (amount <= 0) {
			throw new IllegalArgumentException("Can't place bet, must bet a positive amount");
		}
		// bet amount can not exceed total points of player
		if (amount > aPlayer.getTotalPoints())
			throw new IllegalArgumentException("Can't place bet, insufficient points.");
		// to replace and existing bet the new bet amount needs to exceed existing bet
		// amount
		if (aPlayer.getBet().getAmount() >= amount)
			throw new IllegalArgumentException("Can't place bet, new bet must be higer than existing");
		// we made it
		betOK = true;
		return betOK;
	}

	@Override
	public void dealPlayer(String playerId, int delay)
			throws NullPointerException, IllegalArgumentException, IllegalStateException {
		Player player = findPlayer(playerId);
		if (playerId == null)
			throw new NullPointerException("Can't deal, player id is null.");
		if (player == null)
			throw new IllegalArgumentException("Can't deal,  player does not exist.");
		if (delay < 0)
			throw new IllegalArgumentException("Can't deal, can not have a negative delay");
		if (player.getBet() == null)
			throw new IllegalStateException("Can't deal, player has not made a bet");
		if (!(player.getHand().isEmpty()))
			throw new IllegalStateException("Can't deal, player has already been dealt");
		// new deck of shuffled cards
		if (aDeck == null) {
			aDeck = DeckImpl.createShuffledDeck();
			for (GameCallback cb : cbs) {
				cb.newDeck(aDeck);
			}
		}
		// deal cards to player until bust
		boolean notBust = true;
		while (notBust) {
			// if deck is empty replace deck
			if (aDeck.cardsInDeck() == 0) {
				aDeck = DeckImpl.createShuffledDeck();
				for (GameCallback cb : cbs) {
					cb.newDeck(aDeck);
				}
			}
			// take the card off the deck and give to the player
			Card aCard = aDeck.removeNextCard();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// if player is bust dont give the card. burn card. ie loose track of it.
			notBust = player.getHand().dealCard(aCard);
			// process call backs
			for (GameCallback cb : cbs) {
				if (notBust) {
					cb.playerCard(player, aCard);
				} else {
					cb.playerBust(player, aCard);
				}
			}
		}
	}

	@Override
	public void dealHouse(int delay) throws IllegalArgumentException {
		if (delay < 0)
			throw new IllegalArgumentException("Can't deal the house, can not have a negative delay");
		// deal cards to dealer until bust
		boolean notBust = true;
		while (notBust) {

			// if deck is empty replace deck
			if (aDeck.cardsInDeck() == 0) {
				aDeck = DeckImpl.createShuffledDeck();
				for (GameCallback cb : cbs) {
					cb.newDeck(aDeck);
				}
			}
			// take the card off the deck and give to the dealer
			Card aCard = aDeck.removeNextCard();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// if dealer is bust dont give the card. burn card. ie loose track of it.
			notBust = dealersHand.dealCard(aCard);
			// process call backs
			for (GameCallback cb : cbs) {
				if (notBust) {
					cb.houseCard(dealersHand, aCard);
				} else {
					// make sure all bets are in
					for (Player aPlayer : players) {
						if (aPlayer.getBet() == Bet.NO_BET)
							;
						else
							aPlayer.applyBetResult(dealersHand);
					}
					// safe to do callback*/
					cb.houseBust(dealersHand, aCard);
				}
			}
		}
	}

	@Override
	public void resetAllBetsAndHands() {
		for (Player player : players) {
			player.resetBet();
			player.getHand().reset();
			for (GameCallback cb : cbs) {
				cb.betUpdated(player);
			}
		}
	}

	// helper method the finds a player based on their playerid,
	// returns player when found or null if not found.
	private Player findPlayer(String playerId) {
		for (Player p : this.players) {
			if (p.getId().equals(playerId)) {
				return p;
			}
		}
		// if not found
		return null;
	}
}
