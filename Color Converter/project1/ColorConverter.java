package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;



/**
 * This class is the program performing color conversion.
 * The program is interactive. 
 * When the program is executed the name of the input file containing the list of all the named 
 * CSS colors is provided as the program's single command line argument. The data in this file 
 * serves as a database of all the named colors. 
 * In the interactive part, the user enters a hexadecimal representation of a color. The program 
 * responds by printing the RGB description and the color name (if one exists in the list of 
 * named colors).  
 * 
 * @author Joanna Klukowska
 *
 */
public class ColorConverter {

	/**
	 * The main() method of this program. 
	 * @param args array of Strings provided on the command line when the program is started; 
	 * the first string should be the name of the input file containing the list of named colors. 
	 */
	public static void main(String[] args) {
		
		
		//Color cNew = new Color ("#ffffff");
		//System.out.println(cNew);
		
//		ArrayList<Color> newList = new  ArrayList<Color>(); 
//		newList.add( new Color ("#FF0000", "Red") ); 
//		newList.add( new Color ("#00FF00", "Green") ); 
//		newList.add( new Color ("#0000FF", "Blue") ); 
//		
//		System.out.println(newList.indexOf( new Color("#ffffff", "black") ) ); 
		
		
//		Color c12 = new Color (null);
//		c12.equals(new Color(null));
		
//		ColorList newList = new ColorList();
//		try{
//			newList.add(new Color("#00ff00"));
//			newList.add(new Color("#ff0000"));
//			newList.add(new Color("#xxxxxx"));   
//			newList.add(new Color("#0000ff"));
//		}
//		catch ( IllegalArgumentException ex) { 
//		  //do nothing
//		}
//		System.out.println( newList.size() );  
		
//		ColorList newList = new ColorList();
//		try {
//			newList.add(new Color("#00ff00"));
//			newList.add(new Color("#ff0000"));
//			newList.add(new Color("#0000ff"));
//			newList.add(new Color("#xxxxxx"));
//			
//			
//		} catch (IllegalArgumentException ex) {
//			// do nothing
//		}
//		System.out.println(newList.size());
		
		
		
		//Color newColor1 = new Color("#0AG123");
		
//		Color k1 = new Color("#00ff00");
//		Color k2 = new Color(0, 256, 0);
//		Color k3 = k1;
		
//		Color test = new Color("#A123FF7");
//		
//		Color newColor = new Color ("#FFE4C4", "Bisque");
//		Color newColor2 = new Color ("#FFE4C6", "Bisque");
//		int comp = newColor.compareTo(newColor2);
//		System.out.println(comp);
		
		//verify that the command line argument exists 
		if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		} 
		
		//verify that command line argument contains a name of an existing file 
		File colorFile = new File(args[0]); 
		if (!colorFile.exists()){
			System.err.println("Error: the file "+colorFile.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		if (!colorFile.canRead()){
			System.err.println("Error: the file "+colorFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for readistringng 
		Scanner inColors = null;
		
		
		try {
			inColors = new Scanner (colorFile ) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+colorFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//read the content of the file and save the data in a list of named colors
		ColorList list = new ColorList(); 
		String line = null; 
		Scanner parseLine = null; 
		String colorName = null;
		String hexValue = null; 
		Color current = null;
		while (inColors.hasNextLine()) {
			try { 
				line = inColors.nextLine(); 
				parseLine = new Scanner(line);
				parseLine.useDelimiter(", "); 
				colorName = parseLine.next();
				hexValue = parseLine.next();
			}
			catch (NoSuchElementException ex ) {
				//caused by an incomplete or miss-formatted line in the input file
				System.err.println(line);
				continue; 	
			}
			try {
				current = new Color (hexValue.trim(), colorName.trim());
				list.add(  current  ); 
			}
			catch (IllegalArgumentException ex ) {
				//ignore this exception and skip to the next line 
			}
		}	
		
		
		//interactive mode: 
		
		Scanner userInput  = new Scanner (System.in ); 
		String userValue = "";
		
		
		do {
			System.out.println("Enter the color in HEX format (#RRGGBB) or \"quit\" to stop:" );
			//get value of from the user 
			userValue = userInput.nextLine();
			if (!userValue.equalsIgnoreCase("quit")) { 
				Color c = list.getColorByHexValue(  userValue  ) ;
				if ( c == null ) {
					try {
						c = new Color (userValue);
					}
					catch (IllegalArgumentException ex ) {
						System.out.println("Error: This is not a valid color specification."); 
						continue;
					}
				}
				System.out.println(c + "\n"); 
			}
			
		} while (!userValue.equalsIgnoreCase("quit"));     
		
		userInput.close();
		
	}

}
