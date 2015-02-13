package data;

import java.util.ArrayList;

import enums.MuleType;

public class Store {
	private ArrayList<Resource> resources;
	
	private Resource food;
	private Resource energy;
	private Resource smithore;
	private Resource crystite;
	private Resource mule;
	/**
	 * Starting prices and quantities for resources of a store
	 */
	final public static int FOOD_PRICE = 30;
	final public static int ENERGY_PRICE = 25;
	final public static int SMITHORE_PRICE = 50;
	final public static int CRYSTITE_PRICE = 100;
	final public static int MULE_PRICE = 100;
	
	final public static int FOOD_STARTING_QUANTITY = 8;
	final public static int ENERGY_STARTING_QUANTITY = 8;
	final public static int SMITHORE_STARTING_QUANTITY = 8;
	final public static int CRYSTITE_STARTING_QUANTITY = 0;	
	final public static int MULE_STARTING_QUANTITY = 14;
	/**
	 * Creates new Store with the proper starting quantities
	 */
	public Store(){
		food = new Resource("Food", FOOD_STARTING_QUANTITY);
		energy = new Resource("Energy", ENERGY_STARTING_QUANTITY);
		smithore = new Resource("Smithore", SMITHORE_STARTING_QUANTITY);
		crystite = new Resource("Crystite", CRYSTITE_STARTING_QUANTITY);
		mule = new Resource("Mule", MULE_STARTING_QUANTITY);
		resources = new ArrayList();
		resources.add(new Resource("Money"));
		resources.add(food);
		resources.add(energy);
		resources.add(smithore);
		resources.add(crystite);
		resources.add(mule);
	}
	/**
	 * the player buys one of the resource from this store, if possible
	 * @param resourceName
	 * @param player
	 * @return String representing the description of the transaction that happened or didnt happen
	 */
	public String buy(String resourceName, Player player){
		Resource resource = getResourceFromName(resourceName);
		int resourcePrice = getResourcePriceFromName(resourceName);
		int resourceIndex = Resource.getIndexFromResourceName(resourceName);
		if(player.getResources().get(0).getAmount()<resourcePrice) return "You don't have enough money";
		else if(resource.getAmount()<1) return "Store is out of "+resourceName;
		if(resourceName.equals("Mule") && player.getResources().get(resourceIndex).getAmount()>0) return "You can only have one Mule at a time";
		if (resourceName.equals("Mule")) {
			player.setMuleType(MuleType.BLANK);
		}
		player.getResources().get(0).addAmount(-1*resourcePrice);
		resource.addAmount(-1);
		player.getResources().get(Resource.getIndexFromResourceName(resourceName)).addAmount(1);
		return "You bought "+resourceName+"!";
	}
	/**
	 * a player sells one of resource to this store, if possible
	 * @param resourceName
	 * @param player
	 * @return String representing the transaction that occured or didnt occur
	 */
	public String sell(String resourceName, Player player){
		Resource resource = getResourceFromName(resourceName);
		int resourcePrice = getResourcePriceFromName(resourceName);
		int resourceIndex = Resource.getIndexFromResourceName(resourceName);
		if(player.getResources().get(resourceIndex).getAmount() <1) return "You have no "+resourceName+" to sell.";
		if (resourceName.equals("Mule")) {
			player.setMuleType(MuleType.NONE);
		}
		getResourceFromName(resourceName).addAmount(1);
		player.getResources().get(0).addAmount(resourcePrice);
		player.getResources().get(resourceIndex).addAmount(-1);
		return "You sold 1 "+resourceName+" for "+resourcePrice+"!";
	}
	
	private Resource getResourceFromName(String resourceName){
		Resource resource = null;
		switch(resourceName){
		case "Food": resource = food;
					break;
		case "Energy": resource = energy;
					break;
		case "Smithore": resource = smithore;
					break;
		case "Crystite": resource = crystite;
					break;
		case "Mule": resource = mule;
		}
		return resource;
	}
	
	private int getResourcePriceFromName(String resourceName){
		int resource = 0;
		switch(resourceName){
		case "Food": resource = FOOD_PRICE;
					break;
		case "Energy": resource = ENERGY_PRICE;
					break;
		case "Smithore": resource = SMITHORE_PRICE;
					break;
		case "Crystite": resource = CRYSTITE_PRICE;
					break;
		case "Mule": resource = MULE_PRICE;
		}
		return resource;
	}
	
	public Resource getFood(){
		return food;
	}
	
	public Resource getEnergy(){
		return energy;
	}
	
	public Resource getSmithore(){
		return smithore;
	}
	
	public Resource getCrystite(){
		return crystite;
	}
	
	public Resource getMule(){
		return mule;
	}
}
