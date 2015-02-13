package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import data.Controller;
//import driver.GameController;
import driver.GameUI;

/**
 * Controller for SettingsMenu view
 * 
 */
public class SettingsMenuController extends Controller implements Initializable {
	@FXML
	ChoiceBox<?> numPlayersBox;

	private int difficulty;
	private int mapType;
	private int numPlayers;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle) sets difficulty, mapType, and numPlayers as it
	 * gets this information from the singleton
	 */
	@Override
	public void initialize(URL url, ResourceBundle rsrcs) {
		// GameController.getInstance().setCurrentController(this);
		difficulty = GameUI.getInstance().getDifficulty();
		mapType = GameUI.getInstance().getMapType();
		numPlayers = GameUI.getInstance().getNumPlayers();
	}

	/**
	 * @param event
	 *            difficulty is set based on the toggle box
	 */
	public void chooseDifficulty(ActionEvent event) {
		String id = ((ToggleButton) event.getSource()).getId();
		String[] strs = id.split("_");
		difficulty = Integer.parseInt(strs[1]);
	}

	/**
	 * @param event
	 *            mapType is chosen based on the radio button's selection
	 */
	public void chooseMapType(ActionEvent event) {
		String id = ((RadioButton) event.getSource()).getId();
		String[] strs = id.split("_");
		mapType = Integer.parseInt(strs[1]);
	}

	/**
	 * numPlayers is set based on a toggle box
	 */
	public void chooseNumPlayers() {
		String num = (String) numPlayersBox.getValue();
		String[] nums = num.split("\\s");
		numPlayers = Integer.parseInt(nums[0]);
	}

	/**
	 * Takes settings inputed into view and sets the appropriate values in the
	 * GameUI singleton instance
	 */
	public void saveSettings() {
		chooseNumPlayers();
		GameUI.getInstance().setNumPlayers(numPlayers);
		GameUI.getInstance().setMapType(mapType);
		GameUI.getInstance().setDifficulty(difficulty);
		GameUI.getInstance().loadMainMenu();
	}

	/**
	 * calls the GamUI's singleton loadMainMenu(), without saving the settings
	 */
	public void discardSettings() {
		GameUI.getInstance().loadMainMenu();
	}
}
