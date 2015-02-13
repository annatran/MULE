package data.races;

import data.Race;

/**
 * Represents PackerRace
 *
 */
public class PackerRace extends Race {
	
	public PackerRace(int playerId) {
		super(playerId);
	}
	
	@Override
	public void applyRaceModifier() {
		// TODO: Implement modifier
	}
	
	@Override
	public String toString() {
		return "Packer";
	}
}