package data;

import driver.GameController;

public class ReceivedPackageEvent extends Event {
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#performEvent()
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		player.getResources().get(Resource.getIndexFromResourceName("Food"))
				.addAmount(3);
		player.getResources().get(Resource.getIndexFromResourceName("Energy"))
				.addAmount(2);
		return "YOU JUST RECEIVED A PACKAGE FROM THE GT ALUMNI CONTAINING 3 FOOD AND 2 ENERGY UNITS.";
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
