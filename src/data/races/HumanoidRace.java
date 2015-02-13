package data.races;

import data.Race;

/**
 * represents Humanoid Race
 *
 */
public class HumanoidRace extends Race {
	
	public HumanoidRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Humanoid";
	}
}