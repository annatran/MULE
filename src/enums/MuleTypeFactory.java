package enums;

public class MuleTypeFactory {

	/**
	 * Gets the Mule Type Enum given the string.
	 * @param enumName
	 * @return
	 */
	public static MuleType getMuleTypeEnumFromString(String enumName) {
		switch (enumName) {
		case "NONE":
			return MuleType.NONE;
		case "BLANK":
			return MuleType.BLANK;
		case "ENERGY":
			return MuleType.ENERGY;
		case "FOOD":
			return MuleType.FOOD;
		case "SMITHORE":
			return MuleType.SMITHORE;
		default:
			return null;
		}
	}
	
}
