package controllers;

//import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import data.Controller;
import data.Map;
import data.Player;
import data.Property;
import database.DatabaseController;
import driver.GameController;
import driver.GameUI;
import enums.MuleType;

public class MapController extends Controller implements Initializable {
	public final static int MAP_SIZE = 700;
	public final static int TILE_WIDTH = 78;
	public final static int TILE_HEIGHT = 120;
	private final GameController gameControllerInstance = GameController
			.getInstance();
	private final Map map = gameControllerInstance.getMap();
	private String[] colorStrings = { "Red", "Green", "Blue", "Yellow" };

	@FXML
	ProgressBar progressBar;

	@FXML
	Label pLabel;

	@FXML
	Button passButton;

	@FXML
	AnchorPane anchorPane;

	/**
	 * saves the game to DB
	 */
	public void saveGame() {
		DatabaseController controller = new DatabaseController();
		controller.save();
	}

	/**
	 * loads the game from DB
	 */
	public void openGame() {
		DatabaseController controller = new DatabaseController();
		controller.load();
	}

	/**
	 * quits the current game (returns you to main menu)
	 */
	public void quitGame() {
		GameController.getInstance().getCurrentTimerTask().terminate();
		GameController.getInstance().clearInstance();
		GameUI.getInstance().loadMainMenu();
	}

	/**
	 * Creates map and initializes all neccessary components
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameControllerInstance.setCurrentController(this);
		passButton.setVisible(gameControllerInstance.getRound()
				.getCurrentRound() > 2);
		generateScreen();
		drawPropertyColors();
		drawMuleTypeForAllProperties();
		gameControllerInstance.setIsMapGenerated(true);
		if (GameController.getInstance().getCurrentPlayerInitialTime() > 0) {
			double progress = (double) GameController.getInstance()
					.getCurrentPlayerRemainingTime()
					/ (double) GameController.getInstance()
							.getCurrentPlayerInitialTime();
			progressBar.setProgress(progress);
		}
	}

	private void generateScreen() {
		Group root = new Group();
		VBox vbox = new VBox();
		addMap(vbox);
		addPromptLabel(vbox);
		addDescriptionLabel(vbox);
		addPlayerLabels(vbox);
		root.getChildren().add(vbox);
		anchorPane.getChildren().add(root);
		root.setFocusTraversable(true);
		root.setOnKeyPressed(new KeyPressedHandler());
		passButton.toFront();
	}

	/*
	 * Adds a VBox element to the anchorPane of map.fxml. VBox element is made
	 * up of HBox elements which hold ImageViews to represent the map chosen.
	 */
	private void addMap(VBox vbox) {
		String[][] tileFileNames = map.getTileFiles();
		for (int i = 0; i < tileFileNames.length; i++) {
			HBox box = new HBox();
			for (int j = 0; j < tileFileNames[i].length; j++) {
				String loc = GameUI.class.getResource(
						"/res/" + tileFileNames[i][j]).toString();
				Image image = new Image(loc);
				ImageView view = new ImageView();
				view.setImage(image);
				view.setFitWidth(TILE_WIDTH);
				view.setFitHeight(TILE_HEIGHT);
				view.setSmooth(true);
				view.setCache(true);
				view.setOnMousePressed(new TileOnClickHandler());
				box.getChildren().add(view);
			}
			vbox.getChildren().add(box);
		}
	}

	private void addPromptLabel(VBox vbox) {
		HBox box = new HBox();
		Label promptLabel = new Label("You have selected: Nothing!");
		box.getChildren().add(promptLabel);
		vbox.getChildren().add(box);
	}

	private void addDescriptionLabel(VBox vbox) {
		HBox box = new HBox();
		box.getChildren().add(new Label());
		vbox.getChildren().add(box);
	}

	private void addPlayerLabels(VBox vbox) {
		VBox vboxx = new VBox();
		ArrayList<Player> players = gameControllerInstance.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			Label playerLabel = new Label("Player " + (i + 1) + ": "
					+ players.get(i).getPlayerName() + " | Race: "
					+ players.get(i).getPlayerRace() + "  | Money: "
					+ players.get(i).getResources().get(0).getAmount());
			vboxx.getChildren().add(playerLabel);
		}
		vbox.getChildren().add(vboxx);
	}

	/**
	 * Sets a particular property to the current player
	 * 
	 * @param x
	 *            position of property
	 * @param y
	 *            position of property
	 */
	public void setProperty(int x, int y) {
		int playerId = gameControllerInstance.getMap().getTiles()[y][x]
				.getProperty().getPlayerId();
		if (playerId < 0)
			return;
		int color = gameControllerInstance.getPlayers().get(playerId)
				.getPlayerColor();
		String loc = GameUI.class.getResource(
				"/res/property" + colorStrings[color] + ".png").toString();
		Image image = new Image(loc);
		ImageView view = new ImageView();
		view.setImage(image);
		view.setFitWidth(20);
		view.setFitHeight(20);
		view.setSmooth(true);
		view.setCache(true);
		view.setX(x * TILE_WIDTH + (TILE_WIDTH - 24));
		view.setY(y * TILE_HEIGHT + 2);
		anchorPane.getChildren().add(view);
	}

	private void drawMuleTypeForProperty(int x, int y) {
		MuleType propertyType = gameControllerInstance.getMap().getTiles()[y][x]
				.getProperty().getPropertyType();
		String loc = "";
		boolean hasMule = false;

		switch (propertyType) {
		case FOOD:
			loc = GameUI.class.getResource("/res/Food.png").toString();
			hasMule = true;
			break;
		case ENERGY:
			loc = GameUI.class.getResource("/res/Energy.png").toString();
			hasMule = true;
			break;
		case SMITHORE:
			loc = GameUI.class.getResource("/res/Smithore.png").toString();
			hasMule = true;
			break;
		default:
			break;
		}

		if (hasMule) {
			Image image = new Image(loc);
			ImageView view = new ImageView();
			view.setImage(image);
			view.setFitWidth(20);
			view.setFitHeight(20);
			view.setSmooth(true);
			view.setCache(true);
			view.setX(x * TILE_WIDTH + (TILE_WIDTH - 24));
			view.setY(y * TILE_HEIGHT + (TILE_HEIGHT - 20));
			anchorPane.getChildren().add(view);
		}
	}

	private Label[] getPlayerLabels() {
		Group root = (Group) anchorPane.getChildren().get(2);
		VBox vbox = (VBox) (root.getChildren().get(0));
		VBox vboxx = (VBox) (vbox.getChildren().get(7));
		Label[] playerLabels = new Label[GameUI.getInstance().getNumPlayers()];
		Iterator<Node> iterator = vboxx.getChildren().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			playerLabels[i] = (Label) iterator.next();
			i++;
		}
		return playerLabels;
	}

	private ImageView getImageViewFromCord(int x, int y) {
		return (ImageView) ((HBox) ((VBox) ((Group) anchorPane.getChildren()
				.get(2)).getChildren().get(0)).getChildren().get(y))
				.getChildren().get(x);
	}

//	private AnchorPane getAnchorPane() {
//		return anchorPane;
//	}

	/**
	 * exits the game
	 */
	public void exit() {
		Platform.exit();
	}

	/**
	 * sets the description label with the neccessary player informatinon.
	 */
	public void setPLabel() {
		Label[] labels = getPlayerLabels();
		int index = gameControllerInstance.getRound().getCurrentPlayer()
				.getPlayerId();
		ArrayList<Player> players = gameControllerInstance.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			String toAdd = players.get(i).getPlayerName() + " | "
					+ players.get(i).getPlayerRace() + "  | $: "
					+ players.get(i).getResources().get(0).getAmount()
					+ "  | F: "
					+ players.get(i).getResources().get(1).getAmount()
					+ "  | E: "
					+ players.get(i).getResources().get(2).getAmount()
					+ "  | S: "
					+ players.get(i).getResources().get(3).getAmount()
					+ "  | C: "
					+ players.get(i).getResources().get(4).getAmount()
					+ "  | M: "
					+ players.get(i).getResources().get(5).getAmount();
			if (i == index) {
				toAdd += " --- Your Turn!";
			}
			labels[i].setText(toAdd);
		}
	}

	/**
	 * Updates the labels
	 */
	public void setLabel() {
		setPLabel();
	}

	/**
	 * Updates the labels and draws colors on players' properties
	 */
	private void drawPropertyColors() {
		setPLabel();
		String[][] tileFileNames = map.getTileFiles();
		for (int i = 0; i < tileFileNames.length; i++) {
			for (int j = 0; j < tileFileNames[i].length; j++) {
				this.setProperty(j, i);
			}
		}
	}

	private void drawMuleTypeForAllProperties() {
		String[][] tileFileNames = map.getTileFiles();
		for (int i = 0; i < tileFileNames.length; i++) {
			for (int j = 0; j < tileFileNames[i].length; j++) {
				this.drawMuleTypeForProperty(j, i);
			}
		}
	}

	/**
	 * Pass button is pressed, advances to next player turn and updates label
	 */
	public void buttonPressed() {
		gameControllerInstance.getRound().getCurrentPlayer().setIsPassed(true);
		gameControllerInstance.getRound().nextTurn();
		setPLabel();
	}

	/**
	 * Event handler for mouse clicks
	 * 
	 * @author Keagan
	 * 
	 */
	private class TileOnClickHandler implements EventHandler<MouseEvent> {
		/**
		 * Handles when a mouse is clicked on the map
		 */
		public void handle(MouseEvent me) {
			ImageView toEdit = getImageViewFromCord(map.getSelectedXIndex(),
					map.getSelectedYIndex());
			if (toEdit.getEffect() != null) {
				ColorAdjust adjuster = (ColorAdjust) toEdit.getEffect();
				adjuster.setBrightness(0);
			}
			map.setSelected(me.getSceneX(), me.getSceneY());
			int xIndex = (int) (me.getSceneX() / TILE_WIDTH);
			int yIndex = (int) (me.getSceneY() / TILE_HEIGHT);
			toEdit = getImageViewFromCord(xIndex, yIndex);
			if (toEdit.getEffect() != null) {
				ColorAdjust adjuster = (ColorAdjust) toEdit.getEffect();
				adjuster.setBrightness(0.6);
			} else {
				ColorAdjust selectionEffect = new ColorAdjust();
				selectionEffect.setBrightness(.6);
				toEdit.setEffect(selectionEffect);
			}
			Group root = (Group) anchorPane.getChildren().get(2);
			VBox vbox = (VBox) (root.getChildren().get(0));
			HBox hbox = (HBox) (vbox.getChildren().get(5));
			Label pLabel = (Label) (hbox.getChildren().get(0));
			pLabel.setText("You have selected: "
					+ map.getTypeFromCoord(me.getSceneX(), me.getSceneY()));
		}
	}

	/**
	 * Event handler for key presses
	 * 
	 * @author Keagan
	 * 
	 */
	private class KeyPressedHandler implements EventHandler<KeyEvent> {
		/**
		 * Handles when space bar is pressed, performs neccessary actions
		 */
		public void handle(KeyEvent ke) {
			if (ke.getText().equals(" ")) {
				MuleType muleType = gameControllerInstance.getRound()
						.getCurrentPlayer().getMuleType();
				boolean isSelectPhase = gameControllerInstance.getRound()
						.getCurrentRound() <= 2;
				Group root = (Group) anchorPane.getChildren().get(2);
				VBox vbox = (VBox) (root.getChildren().get(0));
				HBox hbox = (HBox) (vbox.getChildren().get(6));
				Label pLabel = (Label) (hbox.getChildren().get(0));
				String type = map.getSelectedType();
//				ArrayList<Player> playersList = gameControllerInstance
//						.getPlayers();

				if (type.equals("Town")) {
					if (isSelectPhase) {
						pLabel.setText("Land selection phase. You cannot enter the town!");
					} else {
						pLabel.setText("Entering town..");
						GameUI.getInstance().enterTown();
					}
				} else {
					int x = map.getSelectedXIndex();
					int y = map.getSelectedYIndex();
					int color = gameControllerInstance.selectProperty(x, y);
					if (muleType != MuleType.NONE) {
						Property currentProperty = map.getTiles()[y][x]
								.getProperty();
						if (currentProperty.getPropertyType() != MuleType.NONE
								|| currentProperty.getPlayerId() != gameControllerInstance
										.getRound().getCurrentPlayer()
										.getPlayerId()) {
							pLabel.setText("You do not own that property. You can't put a mule here! Mule lost!");
						} else {
							currentProperty.setClassification(muleType);
							switch (muleType) {
							case ENERGY: // change imageview to energy property
								pLabel.setText("Your property will now produce Energy!");
								break;
							case FOOD: // " " " food "
								pLabel.setText("Your property will now produce Food!");
								break;
							case SMITHORE: // " " " smithore "
								pLabel.setText("Your property will now produce Smithore!");
								break;
							default:
								break;
							}
							drawMuleTypeForProperty(x, y);
							setPLabel();
						}
						// Reset mule for player once he puts it down.
						gameControllerInstance.getRound().getCurrentPlayer()
								.setMuleType(MuleType.NONE);
						gameControllerInstance.getRound().getCurrentPlayer()
								.getResources().get(5).addAmount(-1);
					}
					if (color >= 0) {
						setProperty(x, y);
						Label[] playerLabels = getPlayerLabels();
						ArrayList<Player> players = gameControllerInstance
								.getPlayers();
						for (int i = 0; i < playerLabels.length; i++) {
							playerLabels[i].setText("Player "
									+ (i + 1)
									+ ": "
									+ players.get(i).getPlayerName()
									+ " | Race: "
									+ players.get(i).getPlayerRace()
									+ "  | Money: "
									+ players.get(i).getResources().get(0)
											.getAmount());
						}
					}
					passButton.setVisible(gameControllerInstance.getRound()
							.getCurrentRound() > 2);
				}
			}
			setPLabel();
		}
	}

	/**
	 * Updates time on the UI
	 * 
	 * @param remainingTime
	 * @param initialTime
	 */
	@Override
	public void updateTime() {
		double progress = (double) GameController.getInstance()
				.getCurrentPlayerRemainingTime()
				/ (double) GameController.getInstance()
						.getCurrentPlayerInitialTime();
		progressBar.setProgress(progress);
	}

	/**
	 * Passes the current player's turn
	 */
	@Override
	public void passTurn() {
		super.passTurn();
		setPLabel();
	}
}
