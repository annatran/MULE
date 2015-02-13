package data;

import driver.GameController;
import enums.MuleType;

/**
 * Control class serves as the parent class for all the view controllers used to handle messages sent between
 * multiple threads, especially those of time-critical nature.
 * @author PaulPa
 *
 */
public class Controller {
	
	/**
	 * Signals to the controller to update the time.
	 * 
	 * @param remainingTime
	 * @param initialTime
	 */
	public void updateTime() {
		
	}
	
	/**
	 * Signals the controller to pass turn. 
	 */
	public void passTurn() {
		// Remove the mule following the current player if he has one.
		GameController.getInstance().getRound().getCurrentPlayer().setMuleType(MuleType.NONE);
		GameController.getInstance().getRound().getCurrentPlayer().setIsPassed(true);
		GameController.getInstance().getRound().nextTurn();
		
	}
	/**
	 * sets current views description label (updates it)
	 */
	public void setLabel(){
		
	}
	
}
