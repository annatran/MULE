package tests;

import static org.junit.Assert.*;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.Test;

import data.Player;
import data.Resource;
import data.Store;
import enums.MuleType;

/**
 * 
 * @author Keagan Long
 *
 */
public class StoreBuyTest {
	String ore = "Smithore";
	String food = "Food";
	String energy = "Energy";
	String crystite = "Crystite";
	String mule = "Mule";
	String money = "Money";

	@Test
	public void NoMoneyTests() {
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		player.getResources().get(Resource.getIndexFromResourceName(money)).addAmount(-1000);
		assertEquals(store.buy(ore,player),"You don't have enough money");
		assertEquals(store.buy(food,player),"You don't have enough money");
		assertEquals(store.buy(energy,player),"You don't have enough money");
		assertEquals(store.buy(crystite,player),"You don't have enough money");
		assertEquals(store.buy(mule,player),"You don't have enough money");
	}
	
	@Test
	public void NoFoodTest(){
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		for(int i = 0;i<Store.FOOD_STARTING_QUANTITY;i++){
			assertEquals(store.buy(food, player),"You bought "+food+"!");
		}
		assertEquals(store.buy(food, player),"Store is out of "+food);
	}
	
	@Test
	public void NoEnergyTest(){
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		for(int i = 0;i<Store.ENERGY_STARTING_QUANTITY;i++){
			assertEquals(store.buy(energy, player),"You bought "+energy+"!");
		}
		assertEquals(store.buy(energy, player),"Store is out of "+energy);
	}

	@Test
	public void NoOreTest(){
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		for(int i = 0;i<Store.SMITHORE_STARTING_QUANTITY;i++){
			assertEquals(store.buy(ore, player),"You bought "+ore+"!");
		}
		assertEquals(store.buy(ore, player),"Store is out of "+ore);
	}
	
	@Test
	public void NoCrystiteTest(){
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		for(int i = 0;i<Store.CRYSTITE_STARTING_QUANTITY;i++){
			assertEquals(store.buy(crystite, player),"You bought "+crystite+"!");
		}
		assertEquals(store.buy(crystite, player),"Store is out of "+crystite);
	}
	
	@Test
	public void OnlyOneMuleAtATimeTest(){
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		player.getResources().get(Resource.getIndexFromResourceName(mule)).addAmount(1);
		assertEquals(store.buy(mule, player),"You can only have one Mule at a time");
	}
	
	@Test
	public void SetsMuleTypeTest(){
		Store store = new Store();
		Player player = new Player(0,"Player",0,"Leggite");
		assertEquals(player.getMuleType(),MuleType.NONE);
		store.buy(mule, player);
		assertEquals(player.getMuleType(),MuleType.BLANK);
	}
}
