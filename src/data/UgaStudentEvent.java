package data;

import driver.GameController;

public class UgaStudentEvent extends Event {
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#performEvent()
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		int toAdd = -1
				* (player.getResources()
						.get(Resource.getIndexFromResourceName("Food"))
						.getAmount() / 2);
		player.getResources().get(Resource.getIndexFromResourceName("Money"))
				.addAmount(toAdd);
		return "MISCHIEVOUS UGA STUDENTS BROKE INTO YOUR STORAGE SHED AND STOLE HALF YOUR FOOD";
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
