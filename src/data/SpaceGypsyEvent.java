package data;

import driver.GameController;

public class SpaceGypsyEvent extends Event {
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#performEvent()
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		int toAdd = 6 * getRoundFactor();
		player.getResources().get(Resource.getIndexFromResourceName("Money"))
				.addAmount(-1 * toAdd);
		return "YOUR SPACE GYPSY INLAWS MADE A MESS OF THE TOWN. IT COST YOU $"
				+ toAdd + " TO CLEAN IT UP";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#isBadEvent()
	 */
	@Override
	public boolean isBadEvent() {
		return true;
	}

}
