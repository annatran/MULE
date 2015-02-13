package data.races;

import data.Race;

/**
 * Represents Flapper Race
 *
 */
public class FlapperRace extends Race {
	
	public FlapperRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Flapper";
	}
	
}