package data.properties;

import data.Player;
import data.Resource;
import data.Terrain;
import enums.MuleType;

/**
 * Smithore Classification will produce certain amounts of smithore every round.
 * 
 * @author PaulPa
 * 
 */

public class SmithoreClassification implements Classification {
	/**
	 * Produces based on Smithore's specificiations and player's mule type
	 */
	public void produce(Player player, Terrain terrain) {
		// If player has no energy, then no production is done.
		if (player.getResources()
				.get(Resource.getIndexFromResourceName("Energy")).getAmount() == 0)
			return;
		player.getResources().get(Resource.getIndexFromResourceName("Energy"))
				.addAmount(-1);
		int toAdd = Terrain.getProductionRate(MuleType.SMITHORE, terrain);
		player.getResources()
				.get(Resource.getIndexFromResourceName("Smithore"))
				.addAmount(toAdd);
	}
}
