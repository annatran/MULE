package data;

/**
 * Represents a Tile - the basic building block of Map
 *
 */
public class Tile {
	/**
	 * the terrain the tile is
	 */
	private Terrain terrain;
	/**
	 * ordered pair location of tile
	 */
	private Pair tileLocation;
	/**
	 * whether or not it is currently selected
	 */
	private boolean selected;
	/**
	 * property associated with tile
	 */
	private Property property;
	
	public Tile(Terrain t, Pair tileLocation){
		terrain = t;
		selected = false;
		this.tileLocation = tileLocation;
		property = new Property(this);
	}
	
	/**
	 * @return terrain associated with tile
	 */
	public Terrain getTerrain() {
		return terrain;
	}
	
	/**
	 * @return property associated with tiles
	 */
	public Property getProperty() {
		return property;
	}
	
	/**
	 * @return ordered pair location of tile
	 */
	public Pair getTileLocation() {
		return tileLocation;
	}
	
	/**
	 * @return filename of the image representing the tile
	 */
	public String getFileName(){
		return terrain.getFileName();
	}
	
	public String toString(){
		return terrain.toString();
	}
	
	/**
	 * @param b boolean to set selected to
	 * sets selected
	 */
	public void setSelected(boolean b){
		selected = b;
	}
	
	/**
	 * @return whether or not boolean is currently selected
	 */
	public boolean getSelected() {
		return selected;
	}
}
