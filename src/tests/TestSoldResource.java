package tests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import data.Player;
import data.Store;
//import data.Resource;

public class TestSoldResource {
	

	private Player p = new Player(0, "Bob", 0, "Buzzite");
	private Store s = new Store();

	
	@Test
	/* Test if player has enough food to sell */
	public void testFoodSell() {
		// Player starts off with 0 food items, so test fails.
		if (p.getResources().get(1).getAmount() < 1) {
			assertFalse("Not enough food to sell", false);
		}
	}
	
	@Test
	/* Test if player has enough energy to sell */
	public void testEnergySell() {
		p.getResources().get(2).setAmount(1); // Test when player has 1 energy
		if (p.getResources().get(2).getAmount() < 1) {
			assertFalse("Not enough energy to sell", true);
		}
	}
	
	@Test
	/* Test if player has enough smithore to sell */ 
	public void testSmithoreSell() {
		p.getResources().get(3).setAmount(-1); // Testing if negative # inputed 
		if (p.getResources().get(3).getAmount() < 1) {
			assertFalse("Not enough smithore to sell", false);
		}
	}
	
	@Test
	/* Test if player has enough crystite to sell */
	public void testCrystiteSell() { 
		p.getResources().get(4).setAmount(5); // Test when player has greater than 1
		if (p.getResources().get(4).getAmount() < 1) { 
			assertFalse("Not enough crystite to sell", true);
		}
	}

}
