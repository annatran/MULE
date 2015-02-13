package tests;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import data.Pair;
import data.Player;
import data.Property;
import data.Resource;
import data.Terrain;
import data.Tile;
import driver.GameController;
import enums.MuleType;

/**
 * JUnit test cases for production associated with data.Property class.
 * @author Paul Park
 */
public class PropertyTest {
	
	// TODO: Test for different races.  I can't do that yet since we don't have race modifiers implemented into production yet.
	
	/**
	 * Sets up a property and calls produce() on it.
	 * 
	 * @param propertyType
	 * @param terrainType
	 * @param race
	 * @param initialAmount
	 * @param hasEnergy (if propertyType is ENERGY, then set this to false and use the initial amount instead)
	 * @return The new amount of resource associated with the property type after production.
	 */
	private int getAmountAfterProduction(MuleType propertyType, String terrainType, String race, int initialAmount, boolean hasEnergy) {
		Player testPlayer = new Player(0, "Test Player", 0, race);
		ArrayList<Player> testPlayersList = new ArrayList<Player>();
		testPlayersList.add(testPlayer);
		new GameController(0, testPlayersList, 0);
		Tile testTile = new Tile(new Terrain(terrainType), new Pair(1, 1));
		
		if (hasEnergy) 
			testPlayer.getResources().get(Resource.getIndexFromResourceName("Energy")).setAmount(1);
		
		testPlayer.getResources().get(Resource.getIndexFromMuleType(propertyType)).setAmount(initialAmount);		
		
		Property testProperty = new Property(testTile);
		testProperty.setPlayerId(testPlayer.getPlayerId());
		testProperty.setClassification(propertyType);
		testProperty.produce();
		
		return testPlayer.getResources().get(Resource.getIndexFromMuleType(propertyType)).getAmount();
	}
	
	// Energy Tests
	
	@Test
	public void testPropertyProductionEnergyWhenNoEnergy() {
		int energyAmount = getAmountAfterProduction(MuleType.ENERGY, "River", "Humanoid", 0, false);
		Assert.assertEquals(0, energyAmount);		
	}
	
	@Test
	public void testPropertyProductionEnergyFromRiver() {
		int energyAmount = getAmountAfterProduction(MuleType.ENERGY, "River", "Humanoid", 1, false);
		Assert.assertEquals(2, energyAmount);
	}
	
	@Test
	public void testPropertyProductionEnergyFromPlains() {
		int energyAmount = getAmountAfterProduction(MuleType.ENERGY, "Plain", "Humanoid", 1, false);
		Assert.assertEquals(3, energyAmount);
	}
	
	@Test
	public void testPropertyProductionEnergyFromOneMountain() {
		int energyAmount = getAmountAfterProduction(MuleType.ENERGY, "oneMountain", "Humanoid", 1, false);
		Assert.assertEquals(1, energyAmount);
	}
	
	@Test
	public void testPropertyProductionEnergyFromTwoMountains() {
		int energyAmount = getAmountAfterProduction(MuleType.ENERGY, "twoMountain", "Humanoid", 1, false);
		Assert.assertEquals(1, energyAmount);
	}

	@Test
	public void testPropertyProductionEnergyFromThreeMountains() {
		int energyAmount = getAmountAfterProduction(MuleType.ENERGY, "threeMountain", "Humanoid", 1, false);
		Assert.assertEquals(1, energyAmount);
	}
	
	// Food Tests
	
	@Test
	public void testPropertyProductionFoodWhenNoEnergy() {
		int energyAmount = getAmountAfterProduction(MuleType.FOOD, "River", "Humanoid", 0, false);
		Assert.assertEquals(0, energyAmount);		
	}
	
	@Test
	public void testPropertyProductionFoodFromRiver() {
		int energyAmount = getAmountAfterProduction(MuleType.FOOD, "River", "Humanoid", 0, true);
		Assert.assertEquals(4, energyAmount);
	}
	
	@Test
	public void testPropertyProductionFoodFromPlains() {
		int energyAmount = getAmountAfterProduction(MuleType.FOOD, "Plain", "Humanoid", 0, true);
		Assert.assertEquals(2, energyAmount);
	}
	
	@Test
	public void testPropertyProductionFoodFromOneMountain() {
		int energyAmount = getAmountAfterProduction(MuleType.FOOD, "oneMountain", "Humanoid", 0, true);
		Assert.assertEquals(1, energyAmount);
	}
	
	@Test
	public void testPropertyProductionFoodFromTwoMountains() {
		int energyAmount = getAmountAfterProduction(MuleType.FOOD, "twoMountain", "Humanoid", 0, true);
		Assert.assertEquals(1, energyAmount);
	}

	@Test
	public void testPropertyProductionFoodFromThreeMountains() {
		int energyAmount = getAmountAfterProduction(MuleType.FOOD, "threeMountain", "Humanoid", 0, true);
		Assert.assertEquals(1, energyAmount);
	}
	
	// Smithore Tests
	
	@Test
	public void testPropertyProductionSmithoreWhenNoEnergy() {
		int energyAmount = getAmountAfterProduction(MuleType.SMITHORE, "River", "Humanoid", 0, false);
		Assert.assertEquals(0, energyAmount);		
	}
	
	@Test
	public void testPropertyProductionSmithoreFromRiver() {
		int energyAmount = getAmountAfterProduction(MuleType.SMITHORE, "River", "Humanoid", 0, true);
		Assert.assertEquals(1, energyAmount);
	}
	
	@Test
	public void testPropertyProductionSmithoreFromPlains() {
		int energyAmount = getAmountAfterProduction(MuleType.SMITHORE, "Plain", "Humanoid", 0, true);
		Assert.assertEquals(0, energyAmount);
	}
	
	@Test
	public void testPropertyProductionSmithoreFromOneMountain() {
		int energyAmount = getAmountAfterProduction(MuleType.SMITHORE, "oneMountain", "Humanoid", 0, true);
		Assert.assertEquals(2, energyAmount);
	}
	
	@Test
	public void testPropertyProductionSmithoreFromTwoMountains() {
		int energyAmount = getAmountAfterProduction(MuleType.SMITHORE, "twoMountain", "Humanoid", 0, true);
		Assert.assertEquals(3, energyAmount);
	}

	@Test
	public void testPropertyProductionSmithoreFromThreeMountains() {
		int energyAmount = getAmountAfterProduction(MuleType.SMITHORE, "threeMountain", "Humanoid", 0, true);
		Assert.assertEquals(4, energyAmount);
	}
	
}
