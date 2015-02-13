package data;

import driver.GameController;

public class WanderingStudentEvent extends Event {
	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Event#performEvent()
	 */
	@Override
	public String performEvent() {
		Player player = GameController.getInstance().getRound()
				.getCurrentPlayer();
		player.getResources()
				.get(Resource.getIndexFromResourceName("Smithore"))
				.addAmount(2);
		return "A WANDERING TECH STUDENT REPAID YOUR HOSPITALITY BY LEAVING TWO BARS OF ORE.";
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