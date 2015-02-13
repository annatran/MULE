package data.races;

import data.Race;

/**
 * Represents Gollumer Race
 *
 */
public class GollumerRace extends Race {
	
	public GollumerRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Gollumer";
	}
}