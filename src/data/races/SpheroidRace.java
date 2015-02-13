package data.races;

import data.Race;

/**
 * represents Spheroid Race
 *
 */
public class SpheroidRace extends Race {
	
	public SpheroidRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Spheroid";
	}
	
}