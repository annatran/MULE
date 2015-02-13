package controllers;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import data.Controller;
import data.GambleDelayRunnableTask;
import data.Player;
import driver.GameController;
import driver.GameUI;
import enums.MuleType;

/**
 * Controller for Town view
 * 
 */
public class TownController extends Controller implements Initializable {
	@FXML
	Button leaveButton;

	@FXML
	Button pubButton;
	GameController gameControllerInstance = GameController.getInstance();
	@FXML
	Label playerLabel;

	@FXML
	Label eventLabel;

	@FXML
	ProgressBar progressBar;

	@FXML
	Button storeButton;

	@FXML
	Button outfitMuleWithFoodButton;

	@FXML
	Button outfitMuleWithEnergyButton;

	@FXML
	Button outfitMuleWithSmithoreButton;

	/**
	 * Intiailizes Town fxml
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameControllerInstance.setCurrentController(this);
		playerLabel.setText(GameController.getInstance().getRound()
				.getCurrentPlayer().toString()
				+ "'s turn.");
		eventLabel.setText("Click on a location to do something!");
		if (GameController.getInstance().getCurrentPlayerInitialTime() > 0) {
			double progress = (double) GameController.getInstance()
					.getCurrentPlayerRemainingTime()
					/ (double) GameController.getInstance()
							.getCurrentPlayerInitialTime();
			progressBar.setProgress(progress);
		}
	}

	/**
	 * @param event
	 *            when leaveButton is pressed, the GameUI's singleton instance
	 *            calls startGame() to proceed the view back to the map
	 */
	public void leaveTown(ActionEvent event) {
		GameUI.getInstance().startGame();
	}

	/**
	 * Outfits current player's mule with food
	 */
	public void outfitMuleWithFood() {
		Player currentPlayer = gameControllerInstance.getRound()
				.getCurrentPlayer();
		if (currentPlayer.getMuleType() == MuleType.BLANK) {
			currentPlayer.setMuleType(MuleType.FOOD);
			eventLabel.setText("Your mule has been outfitted with Food!");
		} else {
			eventLabel.setText("You need a brand new mule to outfit.");
		}

	}

	/**
	 * Outfits current player's mule with Energy
	 */
	public void outfitMuleWithEnergy() {
		Player currentPlayer = gameControllerInstance.getRound()
				.getCurrentPlayer();
		if (currentPlayer.getMuleType() == MuleType.BLANK) {
			currentPlayer.setMuleType(MuleType.ENERGY);
			eventLabel.setText("Your mule has been outfitted with Energy!");
		} else {
			eventLabel.setText("You need a brand new mule to outfit.");
		}
	}

	/**
	 * Outfits current player's mule with smithore
	 */
	public void outfitMuleWithSmithore() {
		Player currentPlayer = gameControllerInstance.getRound()
				.getCurrentPlayer();
		if (currentPlayer.getMuleType() == MuleType.BLANK) {
			currentPlayer.setMuleType(MuleType.SMITHORE);
			eventLabel.setText("Your mule has been outfitted with Smithore!");
		} else {
			eventLabel.setText("You need a brand new mule to outfit.");
		}
	}

	/**
	 * Performs a gamble at the pub for current player
	 */
	public void gamble() {
		Player currentPlayer = gameControllerInstance.getRound()
				.getCurrentPlayer();
		Random rand = new Random();
		int currentRound = gameControllerInstance.getRound().getCurrentRound();
		int moneyBonus = 0;
		if (currentRound < 4)
			moneyBonus = 50;
		else if (currentRound < 8)
			moneyBonus = 100;
		else if (currentRound < 12)
			moneyBonus = 150;
		else
			moneyBonus = 200;
		int toadd = rand.nextInt(1000) - 500 + moneyBonus;
		currentPlayer.addResources(toadd);
		String labelstr = new String();
		if (toadd < 0) {
			labelstr = "Sorry, you lost $" + (-1 * toadd) + " by gambling!";
		} else {
			labelstr = "Congrats! you won $" + toadd + " by gambling!";
		}
		System.out.println("hihihi" + labelstr);
		eventLabel.setText(labelstr);
	}

	/**
	 * Loads store when store button is pressed
	 */
	public void storeButtonClicked() {
		GameUI.getInstance().loadStore();
	}

	/**
	 * Gambles if possible for current player
	 * 
	 * @param event
	 */
	public void pubPressed(ActionEvent event) {
		int money = gameControllerInstance.getRound().getCurrentPlayer()
				.getResources().get(0).getAmount();
		String labelstr = new String();
		if (money > 500) {
			gamble();
			gameControllerInstance.getCurrentTimerTask().terminate();
			Thread delayThread = new Thread(new GambleDelayRunnableTask());
			delayThread.start();
		} else {
			labelstr = "You do not have enough money to gamble ($500).";
			eventLabel.setText(labelstr);
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
		GameUI.getInstance().startGame();
	}
}
