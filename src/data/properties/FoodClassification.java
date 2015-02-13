package data.properties;

import data.Player;
import data.Resource;
import data.Terrain;
import enums.MuleType;

/**
 * Food Classification will produce certain amounts of food every round.
 * 
 * @author PaulPa
 * 
 */

public class FoodClassification implements Classification {
	/**
	 * Produces based on Food's specifications and player's multype
	 */
	public void produce(Player player, Terrain terrain) {
		// If player has no energy, then no production is done.
		if (player.getResources()
				.get(Resource.getIndexFromResourceName("Energy")).getAmount() == 0)
			return;
		player.getResources().get(Resource.getIndexFromResourceName("Energy"))
				.addAmount(-1);
		int toAdd = Terrain.getProductionRate(MuleType.FOOD, terrain);
		player.getResources().get(Resource.getIndexFromResourceName("Food"))
				.addAmount(toAdd);
	}
}
