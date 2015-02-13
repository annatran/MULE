package data;

import driver.GameController;

public class MuseumBoughtYourComputerEvent extends Event {
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#performEvent()
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		int toAdd = 8 * getRoundFactor();
		player.getResources().get(Resource.getIndexFromResourceName("Money"))
				.addAmount(toAdd);
		return "THE MUSEUM BOUGHT YOUR ANTIQUE PERSONAL COMPUTER FOR $" + toAdd;
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
