package data;

import driver.GameController;

public class FlyingCatBugsEvent extends Event {

	/**
	 * Performs this event
	 * 
	 * @return String representing what occured in the event
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		int toAdd = 4 * getRoundFactor();
		player.getResources().get(Resource.getIndexFromResourceName("Money"))
				.addAmount(-1 * toAdd);
		return "FLYING CAT-BUGS ATE THE ROOF OFF YOUR HOUSE. REPAIRS COST $"
				+ toAdd;
	}

	/*
	 * @see data.Event#isBadEvent()
	 */
	@Override
	public boolean isBadEvent() {
		return true;
	}

}
