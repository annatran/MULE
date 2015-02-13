package data.races;

import data.Race;

/**
 * represntes Leggite Race
 *
 */
public class UgaiteRace extends Race {
	
	public UgaiteRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Ugaite";
	}
}