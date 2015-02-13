package data.properties;

import data.Player;
import data.Terrain;

/**
 * Classification class as a part of Strategy design pattern to decide what each
 * property will produce depending on its classification.
 * 
 * @author PaulPa
 * 
 */

public interface Classification {
	/**
	 * Produce based on given terrain and player's mule
	 * 
	 * @param player
	 * @param terrain
	 */
	public void produce(Player player, Terrain terrain);
}
