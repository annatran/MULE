package data;

import java.util.ArrayList;

import data.races.BonzoidRace;
import data.races.FlapperRace;
import data.races.BuzziteRace;
import data.races.HumanoidRace;
import data.races.UgaiteRace;
import data.races.MechtronRace;
import data.races.PackerRace;
import data.races.SpheroidRace;
import driver.GameController;
import enums.MuleType;

/**
 * Holds a Player's information
 *
 */
public class Player {
	private ArrayList<Resource> resources;
	private ArrayList<Property> properties;
	private int playerColor; // 0 - Red, 1 - Green, 2 - Blue, 3 - Yellow
	private String playerName;
	private int playerId;
	private Race race;
	private boolean isPassed;
	private int score;
	private int time; // Time given for the player to finish his turn.
	private MuleType muleType; // Type of Mule that the player is carrying right now.
	
	/**
	 * @param playerId the player's id, typically the their index in the Players array
	 * @param playerName String representing the player's name
	 * @param playerColor int corresponding to the player's color
	 * @param race String representing the player's race
	 */
	public Player(int playerId, String playerName, int playerColor, String race){
		this.playerId = playerId;
		this.playerName = playerName;
		this.playerColor = playerColor;
		this.race = createRace(race);
		this.time = 90;
		muleType = MuleType.NONE;
		isPassed = false;
		resources = new ArrayList<Resource>();
		resources.add(new Resource("Money", 1000));
		resources.add(new Resource("Food"));
		resources.add(new Resource("Energy"));
		resources.add(new Resource("Smithore"));
		resources.add(new Resource("Crystite"));
		resources.add(new Resource("Mule"));
		properties = new ArrayList<Property>();
	}
	/**
	 * returns a string rep of players name
	 */
	public String toString(){
		return playerName;
	}
	/**
	 * @return ArrayList of Resources the Player owns
	 */
	public ArrayList<Resource> getResources() {
		return resources;
	}
	
	/**
	 * @return ArrayList of Properties the Player owns
	 */
	public ArrayList<Property> getProperties() {
		return properties;
	}
	
	/**
	 * @return int representing the Player's color
	 */
	public int getPlayerColor() {
		return playerColor;
	}
	
	/**
	 * @return player's name
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * @return player's id
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	/**
	 * Changes the money by the specified amount.
	 * @param amount
	 */
	public void changeMoneyBy(int amount){
		resources.get(0).addAmount(amount); 
	}
	
	public MuleType getMuleType() {
		return muleType;
	}
	
	public void setMuleType(MuleType newMuleType) {
		muleType = newMuleType; 
	}
	
	/**
	 * @return player's Race
	 */
	public Race getPlayerRace() {
		return race;
	}
	
	/**
	 * Checks whether the player chose to pass or not.
	 * @return
	 */
	public boolean getIsPassed(){
		return isPassed;
	}
	
	/**
	 * Set passed status for a player.
	 * @param b
	 */
	public void setIsPassed(boolean b){
		isPassed = b;
	}
	
	/**
	 * Adds a certain amount to money
	 * TODO: Currently hard-coded to money.  Fix it. 
	 * @param amount
	 */
	public void addResources(int amount){
		resources.get(0).addAmount(amount);
	}
	
	/**
	 * Returns the player's score.
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Sets the player's score
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	// 0 - Mechtron, 1 - Buzzite, 2 - Packer, 3 - Bonzoid, 4 - Spheroid, 5 - Flapper, 6 - Leggite, 7 - Humanoid
	/**
	 * @param race String representing race
	 * @return Race object based on the input string
	 */
	protected Race createRace(String race) {
		switch (race) {
		case "Mechtron":
			return new MechtronRace(playerId);
		case "Buzzite":
			return new BuzziteRace(playerId);
		case "Packer":
			return new PackerRace(playerId);
		case "Bonzoid":
			return new BonzoidRace(playerId);
		case "Spheroid":
			return new SpheroidRace(playerId);
		case "Flapper":
			return new FlapperRace(playerId);
		case "Ugaite":
			return new UgaiteRace(playerId);
		default:
			return new HumanoidRace(playerId);
		}
	}
	
	/**
	 * Gets the time given for the player's turn.
	 * @return
	 */
	public int getTurnTime() {
		return time;
	}
	
	/**
	 * Sets the time given for the player's turn
	 * @param time
	 */
	public void setTurnTime(int time) {
		this.time = time;
	}
	/**
	 * calls produce on every property a player owns
	 */
	public void produceAll(){
		for(Tile tile : GameController.getInstance().getMap().getTilesAsArrayList()){
			Property property = tile.getProperty();
			if (property.getPlayerId() == playerId) {
				property.produce();
			}
		}
	}
}
