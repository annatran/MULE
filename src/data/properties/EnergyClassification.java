package data.properties;

import data.Player;
import data.Resource;
import data.Terrain;
import enums.MuleType;

/**
 * Energy Classification will produce certain amounts of energy every round.
 * 
 * @author PaulPa
 * 
 */

public class EnergyClassification implements Classification {
	/**
	 * Produces based on Energy's specifications and player's mule type
	 */
	public void produce(Player player, Terrain terrain) {
		// If player has no energy, then no production is done.
		if (player.getResources()
				.get(Resource.getIndexFromResourceName("Energy")).getAmount() == 0)
			return;
		player.getResources().get(Resource.getIndexFromResourceName("Energy"))
				.addAmount(-1);
		int toAdd = Terrain.getProductionRate(MuleType.ENERGY, terrain);
		player.getResources().get(Resource.getIndexFromResourceName("Energy"))
				.addAmount(toAdd);
	}
}
