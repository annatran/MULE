package data;

import enums.MuleType;

/**
 * Representes the type of terrain a particular tile could be
 *
 */
public class Terrain {
	/**
	 * string rep of type
	 */
	String type;
	/**
	 * name of image file to rep terrain 
	 */
	String picFile;
	
	public Terrain(String type){
		this.type = type;
		picFile = type+".png";
	}
	
	/**
	 * @return string image file name to represent terrain
	 */
	public String getFileName(){
		return picFile;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return type;
	}
	/*
	 * @param MuleType muleType
	 * @param Terrain terrain
	 * 
	 * @return int the production rate given the mule and terrain type of that tile
	 */
	public static int getProductionRate(MuleType muleType, Terrain terrain){
		switch(muleType){
		case NONE:
			return 0;
		case BLANK:
			return 0;
		case ENERGY:
			switch(terrain.type){
			case "River":
				return 2;
			case "Plain":
				return 3;
			case "oneMountain":
				return 1;
			case "twoMountain":
				return 1;
			case "threeMountain":
				return 1;
			}
		case FOOD:
			switch(terrain.type){
			case "River":
				return 4;
			case "Plain":
				return 2;
			case "oneMountain":
				return 1;
			case "twoMountain":
				return 1;
			case "threeMountain":
				return 1;
			}
		case SMITHORE:
			switch(terrain.type){
			case "Plain":
				return 0;
			case "River":
				return 1;
			case "oneMountain":
				return 2;
			case "twoMountain":
				return 3;
			case "threeMountain":
				return 4;
			}
		}
		return 0;
	}
}
