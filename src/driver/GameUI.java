package driver;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import data.Player;

/**
 * Start of Application. Establishes thread, creates an instance of itself
 * (singleton design) and provides a liason between controllers and the
 * application stage and thread
 * 
 */
public class GameUI extends Application {
	/**
	 * Applicatio's Stage
	 */
	private Stage stage;
	/**
	 * instance of itself. GameUI is a singleton class
	 */
	private static GameUI instance;

	// Game Setting Variables
	/**
	 * number of players playing the game (as dictated by settings)
	 */
	private int numPlayers;
	/**
	 * integer representing the game difficulty 0 - Beginner 1 - intermediate 2
	 * - Advanced
	 */
	private int difficulty; // 0 - Beginner, 1 - Intermediate, and so on...
	/**
	 * integer representing map type 0 - River 1 - Plain 2 - Mountain 3 - Valley
	 */
	private int mapType; // 0 - River, 1 - Plain, 2 - Mountain, 3 - Valley
	// 0 - Red, 1 - Green, 2 - Blue, 3 - Yellow
	// 0 - Mechtron, 2 - Gollumer, Etc;
	/**
	 * array that holds the game's players
	 */
	private Player[] players;

	/**
	 * Constructs a GameUI in a Singleton Pattern.
	 */
	public GameUI() {
		instance = this;

		// Default Settings
		numPlayers = 1;
		difficulty = 0;
		mapType = 1;
		players = new Player[numPlayers];
	}

	/**
	 * Gets the Singleton instance of this class.
	 * 
	 * @return GameUI instance
	 */
	public static GameUI getInstance() {
		return instance;
	}

	/**
	 * Sets the number of players
	 * 
	 * @param number
	 *            of players
	 */
	public void setNumPlayers(int num) {
		numPlayers = num;
		players = new Player[numPlayers];
	}

	/**
	 * Gets the Players array
	 * 
	 * @return players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Sets the player at the specified index to be the specified player
	 * 
	 * @param player
	 *            Player Data
	 * @param playerIndex
	 *            Index of the player
	 */
	public void setPlayer(Player player, int playerIndex) {
		players[playerIndex] = player;
	}

	/**
	 * Sets the difficulty level of the game.
	 * 
	 * @param diff
	 */
	public void setDifficulty(int diff) {
		difficulty = diff;
	}

	/**
	 * Sets the type of the map the game will be played on
	 * 
	 * @param type
	 */
	public void setMapType(int type) {
		mapType = type;
	}

	/**
	 * Returns the number of players
	 * 
	 * @return number of players
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * Returns the difficulty of the game
	 * 
	 * @return difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * Returns the type of the map the game will be played on
	 * 
	 * @return map type
	 */
	public int getMapType() {
		return mapType;
	}

	public static void main(String[] args) {
		Application.launch(GameUI.class, (java.lang.String[]) null);
	}

	/**
	 * Loads the main menu screen.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			String source = GameUI.class.getResource("/res/MenuSong.mp3")
					.toString();
			Media media = new Media(source);
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			stage = primaryStage;
			AnchorPane page = (AnchorPane) FXMLLoader.load(GameUI.class
					.getResource("/controllers/MainMenu.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("M.U.L.E");
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(GameUI.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Replaces the current application's scene with the specified fxml scene.
	 * 
	 * @param the
	 *            path to an fxml file
	 * @return
	 * @throws Exception
	 */
	private Parent replaceSceneContent(String fxml) throws Exception {
		Parent page = (Parent) FXMLLoader.load(GameUI.class.getResource(fxml),
				null, new JavaFXBuilderFactory());
		Scene scene = stage.getScene();
		scene.setRoot(page);
		stage.sizeToScene();
		return page;
	}

	/**
	 * Controller Invocation method for loading the settings menu.
	 */
	public void loadOptions() {
		try {
			replaceSceneContent("/controllers/SettingsMenu.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Controller Invocation method for loading the races menu.
	 */
	public void playGame() {
		try {
			replaceSceneContent("/controllers/RacesMenu.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Controller Invocation method for loading the main menu.
	 */
	public void loadMainMenu() {
		try {
			replaceSceneContent("/controllers/MainMenu.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Controller Invocation method for starting the game.
	 */
	public void startGame() {
		try {
			if (GameController.getInstance() == null
					|| !GameController.getInstance().getIsMapGenerated()) {
				ArrayList<Player> players = new ArrayList<Player>(
						Arrays.asList(GameUI.getInstance().getPlayers()));
				new GameController(difficulty, players, mapType);
				GameController.getInstance().beginGame();
			}
			replaceSceneContent("/controllers/Map.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Controller Invocation method for loading the map screen without the game
	 * start logic.
	 */
	public void loadMap() {
		try {
			replaceSceneContent("/controllers/Map.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Controller Invocation method for entering the town.
	 */
	public void enterTown() {
		try {
			replaceSceneContent("/controllers/Town.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Controller Invocation method for entering the store
	 */
	public void loadStore() {
		try {
			replaceSceneContent("/controllers/Store.fxml");
		} catch (Exception e) {
			Logger.getLogger(GameUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * 
	 * @return Stage current stage
	 */
	public Stage getStage() {
		return stage;
	}
}
