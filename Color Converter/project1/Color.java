package project1;

/**
 * This class represents a color. 
 * It uses two equivalent representations: 
 * - RGB(red, green, blue) in which each component is represented using a decimal integer
 * in the range 0-255
 * - hexadecimal #RRGGBB in which the hexadecimal symbols and combined into a single string 
 * preceded by a pound sign.
 * 
 * Optionally, the Color object can store the CSS name of the color (or any other English language
 * name). 
 * 
 * @author Joanna Klukowska
 *
 */
public class Color implements Comparable<Color> {

	
	private String hexValue;
	private String colorName; 
	
	/**
	 * Constructs a new Color object with specified hex value. 
	 * @param hexValue hexadecimal value to be used for this Color; should be in the format #RRGGBB
	 * @throws IllegalArgumentException  if hexValue parameter is invalid 
	 */
	public Color (String hexValue)  {
		this (hexValue, null ); 
	}
	
	/**
	 * Constructs a new Color object with specified hex value and color name. 
	 * @param hexValue hexadecimal value to be used for this Color; should be in the format #RRGGBB
	 * @param colorName color name to be used for this Color 
	 * @throws IllegalArgumentException if hexValue parameter is invalid 
	 */
	public Color ( String hexValue, String colorName )  throws IllegalArgumentException {
		setHexValue( hexValue ); 
		this.colorName = colorName; 		
	} 
	
	/**
	 * Constructs a new Color object with specified red, green and blue components.  
	 * @param red red component of this Color object; should be in the range of 0 to 255 
	 * @param green green component of this Color object; should be in the range of 0 to 255 
	 * @param blue blue component of this Color object; should be in the range of 0 to 255  
	 * @throws IllegalArgumentException  if red, gree, or blue parameters are invalid 
	 */
	public Color (int red, int green, int blue )  throws IllegalArgumentException {
		//validate ranges of the color components
		if (red < 0 || red > 255 )
			throw new IllegalArgumentException("Invalid value for red component. "
					+ "Valid range is 0-255.");
		if (green < 0 || green > 255 )
			throw new IllegalArgumentException("Invalid value for green component. "
					+ "Valid range is 0-255.");
		if (blue < 0 || blue > 255 )
			throw new IllegalArgumentException("Invalid value for blue component. "
					+ "Valid range is 0-255.");
		
		hexValue = String.format("#%02X%02X%02X" ,red, green, blue ); 
		colorName = null; 
		
	}
	
	/**
	 * Returns the hexadecimal value representing this Color object. 
	 * @return the hex value of this Color object 
	 */
	public String getHexValue () {
		return hexValue;
	}

	/**
	 * Returns the English name of this Color object. 
	 * @return the English name of this Color object 
	 */
	public String getName () {
		return colorName;
	}

	/**
	 * Returns the red component of this Color object. 
	 * @return the red component of this Color object 
	 */
	public int getRed( ) {
		return getComponent(1,2);
	}

	/**
	 * Returns the green component of this Color object. 
	 * @return the green compontent of this Color object 
	 */
	public int getGreen( ) {
		return getComponent(3,4);
	}


	/**
	 * Returns the blue component of this Color object. 
	 * @return the blue component of this Color object 
	 */
	public int getBlue( ) {
		return getComponent(5,6);
	}
	
	/**
	 * Returns the string representation of this Color.
	 * @returns the string representation of this Color object 
	 */
	@Override
	public String toString () {
		if (colorName != null) { 
			return String.format("%s, (%3d,%3d,%3d), %s", 
					hexValue.toUpperCase() , getRed(), getGreen(), getBlue() ,colorName); 
		}
		else {
			return String.format("%s, (%3d,%3d,%3d)", 
					hexValue.toUpperCase() , getRed(), getGreen(), getBlue()); 
		}
	}
	
	
	/**
	 * Indicates whether some object obj is "equal to" this one. 
	 * To Color objects are considered equal if their hexValues are the same 
	 * (ignoring the case of hex symbols). 
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Color))
			return false;
		Color other = (Color) obj;
		if (hexValue == null) {
			if (other.hexValue != null)
				return false;
		} else if (!hexValue.equalsIgnoreCase(other.hexValue))
			return false;
		return true;
	}
	
	/** Compares this object with the specified object for order. 
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	@Override
	public int compareTo(Color o) {
		return this.hexValue.compareToIgnoreCase(o.hexValue); 
	}
	
	
	/**
	 * Extracts the component of this Color object represented by the range of indexes 
	 * indexStart-indexEng (inclusive). 
	 * @param indexStart first index for the character representing a Color component 
	 * @param indexEnd  last index for the character representing a Color component 
	 * @return an integer representing the component specified by the two index values 
	 */
	private int getComponent (int indexStart, int indexEnd) { 
		String val = hexValue.substring(indexStart, indexEnd+1); 
		return Integer.parseInt(val, 16); 
	}
	
	
	/**
	 * Validates and sets the hex value for this Color object. 
	 * @param hexValue hexadecimal value to be examined and set. 
	 * @throws IllegalArgumentException if the hexValue is invalid 
	 */
	private void setHexValue(String hexValue)  throws IllegalArgumentException {
		
		//validate length of the string
		if (hexValue.trim().length() != 7 )
			throw new IllegalArgumentException("Invalid length. String format should be #RRGGBB.");
		
		//check the first character
		if ( hexValue.charAt(0) != '#') 
			throw new IllegalArgumentException("Invalid leading character. String format "
					+ "should be #RRGGBB.");
		
		//check symbols at positions 1-7 
		String validSymbols = "0123456789ABCDEFabcdef";
		for (int i = 1; i < 7; i++ ) {
			if (! validSymbols.contains( hexValue.charAt(i)+"" )   )
				throw new IllegalArgumentException("Invalid symbol found. String format "
						+ "should be #RRGGBB.\n"
						+ "Valid symbols are: " + validSymbols + "."); 
		}
		
		this.hexValue = hexValue; 
	}
	
		
}
