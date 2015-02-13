package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import data.Controller;
//import driver.GameController;
import driver.GameUI;

public class MainMenuController extends Controller implements Initializable {
	@FXML
	Button playGameButton;
	@FXML
	Button optionsButton;
	@FXML
	Button creditsButton;
	@FXML
	Button exitButton;

	@Override
	public void initialize(URL url, ResourceBundle rsrcs) {
		// GameController.getInstance().setCurrentController(this);
	}

	/**
	 * Loads game
	 */
	public void playGame() {
		GameUI.getInstance().playGame();
	}

	/**
	 * loads options menu
	 */
	public void options() {
		GameUI.getInstance().loadOptions();
	}

	/**
	 * loads credits
	 */
	public void credits() {
		// TODO: Add credits for later.
	}

	/**
	 * exits GUI
	 */
	public void exit() {
		Platform.exit();
	}
}
