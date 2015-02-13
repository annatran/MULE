package data;

import driver.GameController;

public class FoundDeadMooseRatEvent extends Event {

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#performEvent()
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		int toAdd = 2 * getRoundFactor();
		player.getResources().get(Resource.getIndexFromResourceName("Money"))
				.addAmount(toAdd);
		return "YOU FOUND A DEAD MOOSE RAT AND SOLD THE HIDE FOR $" + toAdd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#isBadEvent()
	 */
	@Override
	public boolean isBadEvent() {
		return false;
	}

}
