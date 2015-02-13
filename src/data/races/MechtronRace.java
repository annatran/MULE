package data.races;

import data.Race;

/**
 * Represnts Mechtron Race
 *
 */
public class MechtronRace extends Race {
	
	public MechtronRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Mechtron";
	}
}
