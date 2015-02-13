package driver;

import java.util.ArrayList;

import data.Controller;
import data.Map;
import data.Player;
import data.Round;
import data.Store;
import data.TimerRunnableTask;

public class GameController {
	private int difficulty;
	private ArrayList<Player> players;
	private Map map;
	// EventRandomizer eventRandomizer;
	private Round round;
	private Store store;
	// Controller for the scene that is currently being displayed on the screen.
	private Controller currentController;
	private int currentPlayerRemainingTime;
	private int currentPlayerInitialTime;

	private static GameController instance;
	private boolean isMapGenerated;

	// Keeps track of the current task that keeps track of the current player's
	// remaining time.
	private TimerRunnableTask currentTimerTask;

	/**
	 * Constructs GameController in Singleton Pattern
	 */
	public GameController(int difficulty, ArrayList<Player> players, int mapType) {
		instance = this;
		this.difficulty = difficulty;
		this.players = players;
		map = new Map(mapType);
		round = new Round(players);
		isMapGenerated = false;
		currentTimerTask = null;
		store = new Store();
		currentPlayerRemainingTime = currentPlayerInitialTime = 0;
	}

	/**
	 * Constructs GameController without passing in a map type. Used when
	 * loading a saved game.
	 * 
	 * @param difficulty
	 * @param players
	 */
	public GameController(int difficulty, ArrayList<Player> players) {
		instance = this;
		this.difficulty = difficulty;
		this.players = players;
		map = null;
		round = new Round(players);
		isMapGenerated = true;
		currentTimerTask = null;
		store = new Store();
		currentPlayerRemainingTime = currentPlayerInitialTime = 0;
	}

	public void setCurrentTimerTask(TimerRunnableTask task) {
		currentTimerTask = task;
	}

	public TimerRunnableTask getCurrentTimerTask() {
		return currentTimerTask;
	}

	public void setPlayer(int playerIndex, String name, int chosenColor,
			String chosenRace) {
		players.add(playerIndex, new Player(playerIndex, name, chosenColor,
				chosenRace));
	}

	public int getCurrentPlayerRemainingTime() {
		return currentPlayerRemainingTime;
	}

	public int getCurrentPlayerInitialTime() {
		return currentPlayerInitialTime;
	}

	public static GameController getInstance() {
		return instance;
	}

	public void setCurrentController(Controller controller) {
		currentController = controller;
	}

	public Controller getCurrentController() {
		return currentController;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public Round getRound() {
		return round;
	}

	public Store getStore() {
		return store;
	}

	public void beginGame() {
		round.nextRound();
	}

	public boolean getIsMapGenerated() {
		return isMapGenerated;
	}

	public void setIsMapGenerated(boolean b) {
		isMapGenerated = b;
	}

	/**
	 * Signals to the application that the time has run out for the current
	 * player's turn.
	 */
	public void timeIsUp() {
		getCurrentController().passTurn();
	}

	/**
	 * Signals to the current controller to update the timer indicator with the
	 * passed-in time remaining.
	 * 
	 * @param remainingTime
	 * @param initialTime
	 *            ;
	 */
	public void updateTime(int remainingTime, int initialTime) {
		currentPlayerRemainingTime = remainingTime;
		currentPlayerInitialTime = initialTime;
		if (currentController != null)
			currentController.updateTime();
	}

	/**
	 * Performs a turn for a player. If the return value is other than 0, then
	 * something has gone wrong!
	 * 
	 * @return return value
	 */
	public int selectProperty(int x, int y) {
		if (map.getTiles()[y][x].getProperty().getPlayerId() > -1) {
			return -1; // Property is already owned by someone!
		}

		if (round.getCurrentPlayer().getResources().get(0).getAmount() < 500) {
			return -1;
		}

		map.getTiles()[y][x].getProperty().setPlayerId(
				round.getCurrentPlayer().getPlayerId());
		round.getCurrentPlayer().getProperties()
				.add((map.getTiles()[y][x].getProperty()));

		if (round.getCurrentRound() > 2) {
			round.getCurrentPlayer().getResources().get(0).addAmount(-500); // All
																			// properties
																			// shouldn't
																			// cost
																			// 100
																			// money.
		} else {
			round.nextTurn();
		}

		return round.getCurrentPlayer().getPlayerColor();
	}

	/**
	 * Clears instance of GameController
	 */
	public void clearInstance() {
		instance = null;
	}
}
