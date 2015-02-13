package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import data.Controller;
import data.Player;
//import driver.GameController;
import driver.GameUI;

/**
 * Controller for the RacesMenu view
 * 
 */
public class RacesMenuController extends Controller implements Initializable {
	@FXML
	Label raceText;
	@FXML
	Label playerText;
	@FXML
	Button nextButton;
	@FXML
	TextField nameField;

	String chosenRace;
	int chosenColor;

	ToggleButton[] colorButtons;

	int playerIndex;

	/**
	 * Initializes races menu
	 */
	@Override
	public void initialize(URL url, ResourceBundle rsrcs) {
		// GameController.getInstance().setCurrentController(this);
		playerIndex = 0;
		playerText.setText("Player " + (playerIndex + 1));
		if (GameUI.getInstance().getNumPlayers() > 1) {
			nextButton.setText("Next!");
		}
		colorButtons = new ToggleButton[GameUI.getInstance().getNumPlayers()];
		chosenRace = null;
		chosenColor = -1;
		nextButton.setDisable(true);
	}

	/**
	 * Selects clicked race for current player
	 * 
	 * @param event
	 */
	public void SelectRace(ActionEvent event) {
		String race = ((ToggleButton) event.getSource()).getId();
		raceText.setText(race);
		chosenRace = race;
		Validate();
	}

	/**
	 * Selects colors based on whats available for current player
	 * 
	 * @param event
	 */
	public void SelectColor(ActionEvent event) {
		String color = ((ToggleButton) event.getSource()).getId();
		String[] strs = color.split("_");
		chosenColor = Integer.parseInt(strs[1]);
		colorButtons[playerIndex] = (ToggleButton) event.getSource();
		Validate();
	}

	/**
	 * Regex checks the player's name
	 */
	public void Validate() {
		boolean check = false;

		check = nameField.getText().matches("([A-Za-z])([A-Za-z\\s])*");

		if (chosenRace == null || chosenColor == -1)
			check = false;

		if (check) {
			nextButton.setDisable(false);
		}
	}

	/**
	 * Starts the game
	 */
	public void StartGame() {
		String name = nameField.getText();
		Player player = new Player(playerIndex, name, chosenColor, chosenRace);
		GameUI.getInstance().setPlayer(player, playerIndex);
		colorButtons[playerIndex].setDisable(true);

		if (playerIndex + 1 == GameUI.getInstance().getNumPlayers()) {
			GameUI.getInstance().startGame();
		} else {
			playerIndex++;
			playerText.setText("Player " + (playerIndex + 1));
			nameField.setText("");
			if (GameUI.getInstance().getNumPlayers() == playerIndex + 1) {
				nextButton.setText("Start!");
			}
			chosenRace = null;
			chosenColor = -1;
			nextButton.setDisable(true);
		}
	}

	/**
	 * Go's back to allow settings to be changed for last player
	 */
	public void GoBack() {
		if (playerIndex == 0) {
			GameUI.getInstance().loadMainMenu();
		} else {
			playerIndex--;
			playerText.setText("Player " + (playerIndex + 1));
			chosenRace = GameUI.getInstance().getPlayers()[playerIndex]
					.getPlayerRace().toString();
			chosenColor = GameUI.getInstance().getPlayers()[playerIndex]
					.getPlayerColor();
			String name = GameUI.getInstance().getPlayers()[playerIndex]
					.getPlayerName();
			colorButtons[playerIndex].setDisable(false);
			nameField.setText(name);
			raceText.setText(chosenRace);
		}
	}

}
