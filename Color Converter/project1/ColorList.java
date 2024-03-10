package project1;

import java.util.ArrayList;

/**
 * ColorList class is used to store a collection of Color objects. 
 * This class inherits all of its properties from an ArrayList<Color>. It 
 * adds Color-specific functions that allow search by color name
 * and by hexadecimal color representation. 
 * 
 * This class does not store Color objects in any particular order. 
 * 
 * @author Joanna Klukowska
 *
 */
@SuppressWarnings("serial")
public class ColorList extends ArrayList<Color> {

	/**
	 * Search through the list of colors for an object matching 
	 * the given colorName. 
	 * @param colorName the name of the color for which to search 
	 * @return the reference to a matching Color object in the list, or
	 * null if the matching color is not found  
	 */
	public Color getColorByName( String colorName ) {
		
		for (Color c : this ) {
			String color = c.getName();
			if (color == null) 
				continue; 
			if (color.equalsIgnoreCase( colorName ) ) 
				return c; 
		}
		return null; 
	}
	 
	
	/**
	 * Search through the list of colors for an object matching 
	 * the given hexValue. 
	 * @param hexValue the hexadecimal value of the color for which to search
	 * @return the reference to a matching Color object in the list, or
	 * null if the matching color is not found  
	 */
	public Color getColorByHexValue( String hexValue ) {
		
		for (Color c : this ) {
			String hex = c.getHexValue();
			if (hex != null && hex.equalsIgnoreCase( hexValue ) ) 
				return c; 
		}
		return null; 
	}
}
