package data;

import java.util.ArrayList;

import controllers.MapController;

/**
 * Represents MULE Map
 *
 */
/**
 * @author Keagan
 */
public class Map {
	private Tile[][] tiles;
	private int selectedXIndex;
	private int selectedYIndex;
	
	/**
	 * Create a default (Standard) map
	 */
	public Map(){
		tiles = getDefaultMap();
		selectedXIndex = 0;
		selectedYIndex = 0;
	}
	
	/**
	 * Creates a map with the specified map type.
	 * Pass in -1 (or any negative value) for a blank map.
	 * @param mapType
	 */
	public Map(int mapType){
		if (mapType >= 0) {
			tiles = getDefaultMap();
			selectedXIndex = 0;
			selectedYIndex = 0;
		}
	}

	/**
	 * @return 2-d array of tiles representing default map
	 */
	private Tile[][] getDefaultMap(){
		Tile[][] output = new Tile[5][9];
		char[] standardTemplate = "P,P,M1,P,R,P,M3,P,P,P,M1,P,P,R,P,P,P,M3,M3,P,P,P,Town,P,P,P,M1,P,M2,P,P,R,P,M2,P,P,P,P,M2,P,R,P,P,P,M2".toCharArray();//from Wiki
		int cursor = 0;
		for(int i = 0; i<output.length;i++){
			for(int j =0; j<output[i].length;j++){
				char current = standardTemplate[cursor];
				while(current == ','){
					cursor++;
					current = standardTemplate[cursor];
				}
				if(current=='P'){
					output[i][j] = new Tile(new Terrain("Plain"), new Pair(i, j));
				}
				else if(current=='R'){
					output[i][j] = new Tile(new Terrain("River"), new Pair(i, j));
				}
				else if(current=='T'){
					output[i][j] = new Tile(new Terrain("Town"), new Pair(i, j));
					cursor += 3;
				}
				else if(current =='M' && standardTemplate[cursor+1]=='1'){
					output[i][j] = new Tile(new Terrain("oneMountain"), new Pair(i, j));
					cursor++;
				}
				else if(current =='M' && standardTemplate[cursor+1]=='2'){
					output[i][j] = new Tile(new Terrain("twoMountain"), new Pair(i, j));
					cursor++;
				}
				else if(current =='M' && standardTemplate[cursor+1]=='3'){
					output[i][j] = new Tile(new Terrain("threeMountain"), new Pair(i, j));
					cursor++;
				}
				cursor++;
			}
		}
		return output;
	}
	
	/**
	 * @return 2-d array of tiles representing map
	 */
	public Tile[][] getTiles() {
		return tiles;
	}
	
	/**
	 * Returns tiles as an ArrayList.
	 * @return tiles as ArrayList
	 */
	public ArrayList<Tile> getTilesAsArrayList() {
		ArrayList<Tile> list = new ArrayList<Tile>();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				list.add(tiles[i][j]);
			}
		}
		return list;
	}
	
	/**
	 * Sets the tiles of this map to be the specified tiles.
	 * @param tiles
	 */
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	/**
	 * @return 2-d array of Strings representing the filename of the image to represent each tile of the Map
	 */
	public String[][] getTileFiles(){
		String[][] output = new String[tiles.length][tiles[0].length];
		for(int i = 0;i<tiles.length;i++){
			for(int j = 0;j<tiles[i].length;j++){
				output[i][j] = tiles[i][j].getFileName();
			}
		}
		return output;
	}
	
	/**
	 * @param x scene (pixel) x location
	 * @param y scene (pixel) y location
	 * @return string representing the type the property at that position
	 */
	public String getTypeFromCoord(double x, double y){
		int xIndex = (int)(x/MapController.TILE_WIDTH);
		int yIndex = (int)(y/MapController.TILE_HEIGHT);
		return tiles[yIndex][xIndex].toString();
	}
		
	/**
	 * @param x scene (pixel) x location
	 * @param y scene (pixel) y location
	 * 
	 * sets the selected tile of the Map given an x,y coordinate pair
	 */
	public void setSelected(double x, double y){
		tiles[selectedYIndex][selectedXIndex].setSelected(false);
		int xIndex = (int)(x/MapController.TILE_WIDTH);
		int yIndex = (int)(y/MapController.TILE_HEIGHT);
		tiles[yIndex][xIndex].setSelected(true);
		selectedYIndex = yIndex;
		selectedXIndex = xIndex;
	}
	
	/**
	 * @return x index of selected tile
	 */
	public int getSelectedXIndex(){
		return selectedXIndex;
	}
	
	/**
	 * @return y index of selected tile
	 */
	public int getSelectedYIndex(){
		return selectedYIndex;
	}
	
	
	/**
	 * @return string representing property type of the selected tile
	 */
	public String getSelectedType(){
		return tiles[selectedYIndex][selectedXIndex].toString();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = "[ ";
		for(int i = 0; i<tiles.length;i++){
			output += "[";
			for(int j = 0;j<tiles[i].length;j++){
				if(tiles[i][j] == null){
					output += " null";
				}
				else{
					output += " "+tiles[i][j].getFileName();
				}
			}
			output +=" ]";
		}
		return output+" ]";
	}
}
