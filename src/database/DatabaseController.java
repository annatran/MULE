package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import data.Map;
import data.Pair;
import data.Player;
import data.Resource;
import data.Terrain;
import data.Tile;
import driver.GameController;
import driver.GameUI;
import enums.MuleTypeFactory;

public class DatabaseController {

	final private String connectString = "jdbc:sqlserver://localhost";
	final private String userId = "muleUser";
	final private String password = "password";

	public void databaseConnect(String database_connect_string,
			String database_userid, String database_password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager
					.getConnection(database_connect_string, database_userid,
							database_password);
			System.out.println("connected");
			Statement statement = conn.createStatement();
			String queryString = "select top 1000 Hag from MuleStateServer.dbo.TestTable";
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the current game state into the database.
	 */
	public void save() {
		GameController gameInstance = GameController.getInstance();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(connectString,
					userId, password);
			Statement statement = conn.createStatement();

			// Clear tables
			statement
					.executeUpdate("delete from MuleStateServer.dbo.GameController");
			statement.executeUpdate("delete from MuleStateServer.dbo.Players");
			statement
					.executeUpdate("delete from MuleStateServer.dbo.Resources");
			statement.executeUpdate("delete from MuleStateServer.dbo.Tiles");
			statement
					.executeUpdate("delete from MuleStateServer.dbo.PlayerOrder");

			// Save variables from GameController
			String updateString = "insert into MuleStateServer.dbo.GameController values ("
					+ gameInstance.getDifficulty()
					+ ","
					+ gameInstance.getCurrentPlayerRemainingTime()
					+ ","
					+ gameInstance.getCurrentPlayerInitialTime()
					+ ","
					+ gameInstance.getRound().getCurrentPlayer().getPlayerId()
					+ "," + gameInstance.getRound().getCurrentRound() + ")";
			statement.executeUpdate(updateString);

			// Save variables for all Players
			for (int i = 0; i < gameInstance.getPlayers().size(); i++) {
				Player player = gameInstance.getPlayers().get(i);
				updateString = "insert into MuleStateServer.dbo.Players values ("
						+ player.getPlayerId()
						+ ",'"
						+ player.getPlayerName()
						+ "',"
						+ player.getPlayerColor()
						+ ",'"
						+ player.getPlayerRace()
						+ "',"
						+ player.getScore()
						+ ","
						+ player.getTurnTime()
						+ ",'"
						+ player.getMuleType() + "')";
				statement.executeUpdate(updateString);
				// Store resources
				for (int j = 0; j < player.getResources().size(); j++) {
					Resource resource = player.getResources().get(j);
					updateString = "insert into MuleStateServer.dbo.Resources values ('"
							+ resource.getType()
							+ "',"
							+ resource.getAmount()
							+ "," + player.getPlayerId() + ")";
					statement.executeUpdate(updateString);
				}
			}

			// Save tiles
			for (int i = 0; i < gameInstance.getMap().getTiles().length; i++) {
				for (int j = 0; j < gameInstance.getMap().getTiles()[i].length; j++) {
					Tile tile = gameInstance.getMap().getTiles()[i][j];
					updateString = "insert into MuleStateServer.dbo.Tiles values ("
							+ tile.getTileLocation().x
							+ ","
							+ tile.getTileLocation().y
							+ ",'"
							+ tile.getTerrain()
							+ "',"
							+ tile.getProperty().getPlayerId()
							+ ",'"
							+ tile.getProperty().getPropertyType() + "')";
					statement.executeUpdate(updateString);
				}
			}

			// Save Player Order
			LinkedList<Player> playerQueue = gameInstance.getRound()
					.getPlayerOrder();
			for (int i = 0; i < playerQueue.size(); i++) {
				updateString = "insert into MuleStateServer.dbo.PlayerOrder values ("
						+ playerQueue.get(i).getPlayerId() + ")";
				statement.executeUpdate(updateString);
			}
			System.out.println("Saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the game from the database and instantiates a new game instance
	 * with the loaded information.
	 */
	public void load() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(connectString,
					userId, password);
			System.out.println("connected");
			Statement statement = conn.createStatement();

			// Terminate the current timer task if one's running.
			if (GameController.getInstance().getCurrentTimerTask() != null)
				GameController.getInstance().getCurrentTimerTask().terminate();

			// Get GameController information
			String queryString = "select * from MuleStateServer.dbo.GameController";
			ResultSet rs = statement.executeQuery(queryString);
			rs.next();
			int difficulty = rs.getInt(1);
			int currentPlayerRemainingTime = rs.getInt(2);
			int currentPlayerId = rs.getInt(4);
			int currentRound = rs.getInt(5);

			// Get Players information
			queryString = "select * from MuleStateServer.dbo.Players";
			rs = statement.executeQuery(queryString);
			ArrayList<Player> players = new ArrayList<Player>();
			while (rs.next()) {
				int playerId = rs.getInt(1);
				String playerName = rs.getString(2);
				int playerColor = rs.getInt(3);
				String raceName = rs.getString(4);
				int score = rs.getInt(5);
				int time = rs.getInt(6);
				String muleTypeName = rs.getString(7);
				Player player = new Player(playerId, playerName, playerColor,
						raceName);
				player.setMuleType(MuleTypeFactory
						.getMuleTypeEnumFromString(muleTypeName));
				player.setScore(score);
				player.setTurnTime(time);
				players.add(player);
			}
			new GameController(difficulty, players);
			GameUI.getInstance().setNumPlayers(players.size());
			GameController gameInstance = GameController.getInstance();

			// Get Resources information for each player
			queryString = "select * from MuleStateServer.dbo.Resources";
			rs = statement.executeQuery(queryString);
			while (rs.next()) {
				String resourceName = rs.getString(1);
				int resourceId = Resource
						.getIndexFromResourceName(resourceName);
				int playerId = rs.getInt(3);
				int amount = rs.getInt(2);
				gameInstance.getPlayers().get(playerId).getResources()
						.get(resourceId).setAmount(amount);
			}

			// Load map.
			Map map = new Map(-1);
			Tile[][] tiles = new Tile[5][9];
			queryString = "select * from MuleStateServer.dbo.Tiles";
			rs = statement.executeQuery(queryString);
			while (rs.next()) {
				int xPos = rs.getInt(1);
				int yPos = rs.getInt(2);
				String terrainName = rs.getString(3);
				int propertyOwnerId = rs.getInt(4);
				String propertyMuleTypeName = rs.getString(5);
				Tile tile = new Tile(new Terrain(terrainName), new Pair(xPos,
						yPos));
				tile.getProperty().setPlayerId(propertyOwnerId);
				tile.getProperty()
						.setClassification(
								MuleTypeFactory
										.getMuleTypeEnumFromString(propertyMuleTypeName));
				tiles[xPos][yPos] = tile;
			}
			map.setTiles(tiles);
			gameInstance.setMap(map);

			// Load Player order
			// In the database, it's always stored in the order of player turns.
			// (First player to go next is the first row in the result set).
			queryString = "select * from MuleStateServer.dbo.PlayerOrder";
			rs = statement.executeQuery(queryString);
			LinkedList<Player> playerOrder = new LinkedList<Player>();
			while (rs.next()) {
				int playerId = rs.getInt(1);
				Player player = gameInstance.getPlayers().get(playerId);
				playerOrder.addLast(player);
			}
			// Also add the current player to the queue since he is already off
			// the queue on save.
			Player currentPlayer = gameInstance.getPlayers().get(
					currentPlayerId);
			currentPlayer.setTurnTime(currentPlayerRemainingTime);
			playerOrder.addFirst(currentPlayer);
			gameInstance.getRound().setPlayerOrder(playerOrder);

			// Start the game.
			gameInstance.getRound().setCurrentRound(currentRound);
			gameInstance.getRound().resumeGame();
			GameUI.getInstance().loadMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
