package data;

import data.properties.Classification;
import data.properties.EnergyClassification;
import data.properties.FoodClassification;
import data.properties.SmithoreClassification;
import driver.GameController;
import enums.MuleType;

/**
 * Represents a property that could be assigned to a specific player
 *
 */
public class Property {

	/**
	 * id of player who owns the property
	 */
	private int playerId;
	private Classification classification;
	private MuleType propertyType;
	/**
	 * tile to which the property belongs 
	 */
	private Tile tile; // Tile to which this property belongs.
	
	public Property(Tile tile) {
		this.playerId = -1;
		this.tile = tile;		
		this.classification = null;
		this.propertyType = MuleType.NONE;
	}
	
	/**
	 * @return id of player who owns the property
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	/**
	 * @param playerId id of player who should own the property
	 * sets the player who owns the property
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	/**
	 * @return Tile assigned to the property
	 */
	public Tile getTile() {
		return tile;
	}
	
	/**
	 * Sets the property type and the classification of the property given the type.
	 * @param type
	 */
	public void setClassification(MuleType type){
		switch(type){
		case ENERGY: 
			classification = new EnergyClassification();
			propertyType = MuleType.ENERGY;
			break;
		case FOOD:
			classification = new FoodClassification();
			propertyType = MuleType.FOOD;
			break;
		case SMITHORE: 
			classification = new SmithoreClassification();
			propertyType = MuleType.SMITHORE;
			break;
		}
	}
	
	public void produce(){
		if(propertyType != MuleType.BLANK && propertyType != MuleType.NONE){
			classification.produce(GameController.getInstance().getPlayers().get(playerId),tile.getTerrain());
		}
	}
	
	/**
	 * Returns the classification object of the property.
	 * @return classification
	 */
	public Classification getClassification() {
		return classification;
	}
	
	/**
	 * Returns the property type of the property.
	 * @return propertyType
	 */
	public MuleType getPropertyType() {
		return propertyType;
	}
}
