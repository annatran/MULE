package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import driver.GameController;
import driver.GameUI;
import enums.MuleType;

/**
 * Facilitates the progression of Rounds throughout the Game
 *
 */
public class Round {
	
	private Player lowestScorePlayer;
	
	/**
	 * Represents the Players in the order they should have turns for a specific round
	 */
	private LinkedList<Player> playerOrder;
	/**
	 * ArrayList of players in the round
	 */
	private ArrayList<Player> players;
	/**
	 * Round's current player
	 */
	private Player currentPlayer;
	/**
	 * List of player scores (will be sorted when determining player order)
	 */
	private int[] scores;
	/**
	 * integer representation of the current round
	 */
	private int currentRound;
	/**
	 * integer representation of the current turn phase
	 * 0 -  Land Selection
	 * 1 - Town Phase
	 */
	private int currentTurnPhase; // 0 - Land Selection, 1 - Town Phase
	
	// Max and min turn times for a player
	final int MIN_TURN_TIME = 5;
	final int MAX_TURN_TIME = 50;
	
	public Round(ArrayList<Player> players) {
		this.players = players;
		playerOrder = new LinkedList<Player>();
		scores = new int[players.size()];
		currentTurnPhase = 0;
		currentPlayer = null;
		currentRound = 0;
	}
	
	/**
	 * @return Linked List representing the order the players will go in
	 */
	public LinkedList<Player> getPlayerOrder() {
		return playerOrder;
	}
	
	/**
	 * Sets the player order to the specified order.
	 * @param newOrder
	 */
	public void setPlayerOrder(LinkedList<Player> newOrder) {
		playerOrder = newOrder;
	}
	
	/**
	 * @return the current Player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * @param playerId of player to be current player
	 * sets current player
	 */
	public void setCurrentPlayer(int playerId) {
		currentPlayer = players.get(playerId);
	}
	
	/**
	 * @return integer representation of current round
	 */
	public int getCurrentRound() {
		return currentRound;
	}
	
	/**
	 * Sets the current round count to the specified new rount count.
	 * @param newRoundCount
	 */
	public void setCurrentRound(int newRoundCount) {
		currentRound = newRoundCount;
	}
	
	/**
	 * @return integer representation of current turn phase
	 */
	public int getTurnPhase() {
		return currentTurnPhase;
	}
	
	/**
	 * increments current turn phase
	 */
	public void nextTurnPhase() {
		currentTurnPhase++;
	}
	
	/**
	 * increments the current round and calculates player order and calls the next turn
	 */
	public void nextRound() {
		produceGoodsForAllPlayers();
		currentRound++;
		calculatePlayerOrder();
		calculatePlayerTurnTimes();
		nextTurn();
	}
	
	/**
	 * Handles production of all items for all players.
	 */
	public void produceGoodsForAllPlayers() {
		ArrayList<Player> plist = GameController.getInstance().getPlayers();
		for(Player p: plist){
			p.produceAll();
		}
	}
	
	/**
	 * If all players have gone, the next round is called. Else, facilitates the popping of players from playerOrder
	 */
	public void nextTurn() {
		if (GameController.getInstance().getCurrentTimerTask() != null)
			GameController.getInstance().getCurrentTimerTask().terminate();
		
		// Reset Mule Selection
		if (currentPlayer != null)
			currentPlayer.setMuleType(MuleType.NONE);
		
		if (playerOrder.isEmpty()) {
			nextRound();
		}
		else {
			currentPlayer = playerOrder.removeFirst();
			Event.performRandomEvent();
			currentTurnPhase = 0;
			TimerRunnableTask task = new TimerRunnableTask(new Timer(currentPlayer.getTurnTime()));
			GameController.getInstance().setCurrentTimerTask(task);
			Thread thread = new Thread(task);
			thread.start();
		}
	}
	
	/**
	 * Tells the Game Controller to kick off the game once a saved game has been loaded.
	 */
	public void resumeGame() {
		currentPlayer = playerOrder.removeFirst();
		currentTurnPhase = 0;
		TimerRunnableTask task = new TimerRunnableTask(new Timer(currentPlayer.getTurnTime()));
		GameController.getInstance().setCurrentTimerTask(task);
		Thread thread = new Thread(task);
		thread.start();
	}/*
	
	*//**
	 * Sorts a array of any type using insertion sort
	 * @param arr
	 *//*
	public static void insertionsort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			for (int j = i; j > 0; j--) {
				if (arr[j] < arr[j - 1]) {
					int temp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = temp;
				}
			}
		}
	}*/
	
	/**
	 * Gets the sum of all the player's resources and sets that as the player's score.
	 * @param player
	 * @return score
	 */
	public int setPlayerScore(Player player) {
		int sum = 0;
		for (int i = 0; i < player.getResources().size(); i++) {
			sum += player.getResources().get(i).getAmount();
		}
		player.setScore(sum);
		return sum;
	}
	
	/**
	 * Determine the order in which the players will play.
	 */
	public void calculatePlayerOrder() {
		int j = 0;
		for (Player player : players) {			
			int score = setPlayerScore(player);
			scores[j] = score;
			j++;
		}
		Arrays.sort(scores);
		playerOrder = new LinkedList<Player>();
		//System.out.println("Sorted Scores: " + Arrays.toString(scores));
		//System.out.println("Unsorted Players: " + Arrays.toString(playerOrder.toArray()));
		for (int score : scores) {
			for (Player player : players) {
				if (player.getScore() == score && !playerOrder.contains(player)) {
					playerOrder.addFirst(player);
				}
			}
		}
		//System.out.println("Sorted Players: " + Arrays.toString(playerOrder.toArray()));
	}
	
	/**
	 * Determine turn time for each player based on their score.  This should be called after player orders have
	 * been determined by calculatePlayerOrder() method.
	 * 
	 * TODO: Food shortage affects turn time.
	 */
	public void calculatePlayerTurnTimes() {
		int lowestPlayerScore = playerOrder.getLast().getScore();
		
		for (Player player : players) {
			double percentage = 0;
			if(player.getScore() ==  0){
				percentage = 1;
			}
			else {
				percentage = lowestPlayerScore / player.getScore();
			}
			Double timeDouble = new Double(percentage * (MAX_TURN_TIME - MIN_TURN_TIME));
			int time = timeDouble.intValue();
			player.setTurnTime(time);
		}
		
		lowestScorePlayer = playerOrder.peek();
	}
	
	public boolean getIfCurrentPlayerHasLowestScore(){
		return currentPlayer.equals(lowestScorePlayer);
	}
}
