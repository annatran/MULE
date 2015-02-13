package data.races;

import data.Race;

/**
 * represntes Leggite Race
 *
 */
public class LeggiteRace extends Race {
	
	public LeggiteRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Leggite";
	}
}