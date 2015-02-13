package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import data.Controller;
import data.Player;
import driver.GameController;
import driver.GameUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class StoreController extends Controller implements Initializable {
	@FXML
	Button leaveStoreButton;

	@FXML
	ProgressBar progressBar;

	@FXML
	Button buyFoodButton;
	@FXML
	Button buyEnergyButton;
	@FXML
	Button buySmithoreButton;
	@FXML
	Button buyCrystiteButton;
	@FXML
	Button buyMuleButton;

	@FXML
	Button sellFoodButton;
	@FXML
	Button sellEnergyButton;
	@FXML
	Button sellSmithoreButton;
	@FXML
	Button sellCrystiteButton;
	@FXML
	Button sellMuleButton;

	@FXML
	Label playerLabel;
	@FXML
	Label eventLabel;

	@FXML
	Label foodLabel;
	@FXML
	Label energyLabel;
	@FXML
	Label smithoreLabel;
	@FXML
	Label crystiteLabel;
	@FXML
	Label muleLabel;

	/**
	 * Initializes Store and sets it as current Controller
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GameController.getInstance().setCurrentController(this);
		if (GameController.getInstance().getCurrentPlayerInitialTime() > 0) {
			double progress = (double) GameController.getInstance()
					.getCurrentPlayerRemainingTime()
					/ (double) GameController.getInstance()
							.getCurrentPlayerInitialTime();
			progressBar.setProgress(progress);
		}
		setPlayerLabel();
		setResourceLabels();
	}

	private void setPlayerLabel() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		String toAdd = player.getPlayerName() + " | " + player.getPlayerRace()
				+ "  | $: " + player.getResources().get(0).getAmount()
				+ "  | F: " + player.getResources().get(1).getAmount()
				+ "  | E: " + player.getResources().get(2).getAmount()
				+ "  | S: " + player.getResources().get(3).getAmount()
				+ "  | C: " + player.getResources().get(4).getAmount()
				+ "  | M: " + player.getResources().get(5).getAmount();
		playerLabel.setText(toAdd);
	}

	private void setResourceLabels() {
		foodLabel
				.setText(""
						+ GameController.getInstance().getStore().getFood()
								.getAmount());
		energyLabel.setText(""
				+ GameController.getInstance().getStore().getEnergy()
						.getAmount());
		smithoreLabel.setText(""
				+ GameController.getInstance().getStore().getSmithore()
						.getAmount());
		crystiteLabel.setText(""
				+ GameController.getInstance().getStore().getCrystite()
						.getAmount());
		muleLabel
				.setText(""
						+ GameController.getInstance().getStore().getMule()
								.getAmount());
	}

	/**
	 * Attempts to buy food
	 */
	public void buyFoodButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.buy("Food",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Attempts to buy energy
	 */
	public void buyEnergyButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.buy("Energy",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Attemps to buy Smithore
	 */
	public void buySmithoreButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.buy("Smithore",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Attemps to buy Crystite
	 */
	public void buyCrystiteButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.buy("Crystite",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Attempts to buy Mule
	 */
	public void buyMuleButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.buy("Mule",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Returns user to town
	 */
	public void leaveStoreButtonPressed() {
		GameUI.getInstance().enterTown();
	}

	/**
	 * Attempts to sell food
	 */
	public void sellFoodButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.sell("Food",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Attemps to sell energy
	 */
	public void sellEnergyButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.sell("Energy",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * Attempts to sell smithore
	 */
	public void sellSmithoreButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.sell("Smithore",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * attempts to sell crystite
	 */
	public void sellCrystiteButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.sell("Crystite",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
	}

	/**
	 * attempts to sell mule
	 */
	public void sellMuleButtonClicked() {
		eventLabel.setText(GameController
				.getInstance()
				.getStore()
				.sell("Mule",
						GameController.getInstance().getRound()
								.getCurrentPlayer()));
		setPlayerLabel();
		setResourceLabels();
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
		GameUI.getInstance().startGame();
	}
}
