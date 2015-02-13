package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import data.Player;
import data.Round;

/**
 * 
 * @author Emilio Mendoza
 *
 */
public class PlayerScoreTest {
	
	static ArrayList<Player> players;
	static Player player1;
	static Player player2;
	static Player player3;
	static Player player4;
	static Round testRound;
	
	/**
	 * Checks to see if the initial scores are correct. Since all the players start out
	 * with 1000 gold, they all have the same score (1000).
	 * 
	 */
	@Before
	public void initialScoreTest() {
		players = new ArrayList<Player>();
		player1 = new Player(1,"first",1,"Humanoid");
		player2 = new Player(2,"second",2,"Humanoid");
		player3 = new Player(3,"third",3,"Humanoid");
		player4 = new Player(4,"fourth",4,"Humanoid");
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		testRound = new Round(players);
		assertEquals("The initial score is 1000",testRound.setPlayerScore(player1),1000);
		assertEquals("The initial score is 1000",testRound.setPlayerScore(player2),1000);
		assertEquals("The initial score is 1000",testRound.setPlayerScore(player3),1000);
		assertEquals("The initial score is 1000",testRound.setPlayerScore(player4),1000);
	}
	
	/**
	 * Check to see if adding and removingmoney increases the player's score, without
	 * affecting other players.
	 */
	@Test
	public void MoneyScoreTest(){
		player1 = new Player(1,"first",1,"Humanoid");
		player1.getResources().get(0).addAmount(500);
		player2 = new Player(2,"second",2,"Humanoid");
		testRound.setPlayerScore(player1); //the setPlayerScore() method should be changed to updatePlayerScore();
		testRound.setPlayerScore(player2);
		assertEquals("The score after adding 100 gold should be 1100",player1.getScore(),1500);
		assertEquals("Other players' scores should not change", player2.getScore(),1000);
		player2.getResources().get(0).addAmount(-1000);
		testRound.setPlayerScore(player2);
		assertEquals("The score after removing 1000 gold should be 0",player2.getScore(),0);
	}
	
	/**
	 * Check to see if adding food affects the player's score.
	 * The total resouces(which are right now money and food) should be added
	 * to evaluate the score.
	 */
	@Test
	public void addFoodScoreTest(){
		player1 = new Player(1,"first",1,"Humanoid");
		player1.getResources().get(1).addAmount(500);
		testRound.setPlayerScore(player1);
		assertEquals("Score should be increased by 500 when 500 food is added",player1.getScore(),1500);
		player1.getResources().get(1).addAmount(-500);
		testRound.setPlayerScore(player1);
		assertEquals("Score should decrease by the same amount that food decreases",player1.getScore(),1000);
	}
	/**
	 * Check to see if adding energy increases the score appropriately.
	 */
	@Test
	public void addEnergyScoreTest(){
		player1 = new Player(1,"first",1,"Humanoid");
		player1.getResources().get(2).addAmount(600);
		testRound.setPlayerScore(player1);
		assertEquals("Score should be increased by the same amount that energy increases",player1.getScore(),1600);
		player1.getResources().get(2).addAmount(-600);
		testRound.setPlayerScore(player1);
		assertEquals("Score should decrease by the same amount that energy decreases",player1.getScore(),1000);
	}
	
	/**
	 * Check to see if adding Smithore affects score normally.
	 */
	@Test
	public void addSmithoreScoreTest(){
		player1 = new Player(1,"first",1,"Humanoid");
		player1.getResources().get(3).addAmount(500);
		testRound.setPlayerScore(player1);
		assertEquals("Smithore affects score normally",player1.getScore(),1500);
		player1.getResources().get(3).addAmount(-250);
		testRound.setPlayerScore(player1);
		assertEquals("Score should decrease by the same amount that smithore decreases",player1.getScore(),1250);
	}
	/**
	 * Check to see if adding Crystite affects the score normally.
	 */
	@Test
	public void addCrystiteScoreTest(){
		player1 = new Player(1,"first",1,"Humanoid");
		player1.getResources().get(4).addAmount(10);
		testRound.setPlayerScore(player1);
		assertEquals("Crystite affects score normally",player1.getScore(),1010);
		player1.getResources().get(4).addAmount(-5);
		testRound.setPlayerScore(player1);
		assertEquals("Score should decrease by the same amount that crystite decreases",player1.getScore(),1005);
	}
	
	/**
	 * The score should never go below zero, even if there are no more resources.
	 */
	@Test
	public void scoreFloorTest1(){
		player3 = new Player(3,"third",3,"Humanoid");
		player3.getResources().get(0).addAmount(-1000000);
		testRound.setPlayerScore(player3);
		assertEquals("Score cannot go below 0",player3.getScore(),0);
	}
	
	/**
	 * A specific resource can not go below zero, thus the score should stop decreasing
	 * when the resouce to be changed does.
	 */
	@Test
	public void scoreFloorTest2(){
		player1 = new Player(1,"first",1,"Humanoid");
		player1.getResources().get(1).addAmount(-9999999);
		testRound.setPlayerScore(player1);
		assertEquals("Score can only decrease as much as the resource can",player1.getScore(),1000);
	}
	
}