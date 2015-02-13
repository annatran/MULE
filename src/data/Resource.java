package data;

import enums.MuleType;

/**
 * Represents a resource a player could have
 *
 */
public class Resource {
	/**
	 * Gives an index corresponding to a particular resource. (This index is used by store, player, etc)
	 * @param resourceName
	 * @return int index of the resource in our lists of resources
	 */
	public static int getIndexFromResourceName(String resourceName){
		int resource = 0;
		switch(resourceName){
		case "Money": resource = 0;
					break;
		case "Food": resource = 1;
					break;
		case "Energy": resource = 2;
					break;
		case "Smithore": resource = 3;
					break;
		case "Crystite": resource = 4;
					break;
		case "Mule": resource = 5;
		}
		return resource;
	}
	/**
	 * returns index given a particular mule type passed in
	 * @param muleType
	 * @return int representing the proper index for that muletype
	 */
	public static int getIndexFromMuleType(MuleType muleType) {
		int resource;
		switch (muleType) {
		case FOOD: resource = 1;
			break;
		case ENERGY: resource = 2;
			break;
		case SMITHORE: resource = 3;
			break;
		default: resource = 0;
			break;
		}
		return resource;
	}
	
	/**
	 * quanitity of resources
	 */
	private int amount;
	/**
	 * resource type
	 */
	private String type;
	/**
	 * Creates new resource given an input type. 
	 * @param type
	 */
	public Resource(String type) {
		amount = 0;
		this.type = type;
	}
	/**
	 * Creates a new resource given an input type and a starting amount.
	 * @param type
	 * @param startingAmount
	 */
	public Resource(String type, int startingAmount) {
		this.type = type;
		amount = startingAmount;
	}
	
	/**
	 * @return resource amount
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * @return resource type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param amount amount to increment the amount by
	 */
	public void addAmount(int amount) {
		this.amount += amount;
		if(this.amount<0) this.amount=0;
	}
	
	/**
	 * Sets the amount of this resource to the specified amount.
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String toString() {
		return type;
	}
}
