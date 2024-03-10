package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This is the class that gives the login stats.
 * The program is interactive by using the command line.
 * When executed the program takes the user's input of the name of a file from the
 * command line argument. The data in this file contains all the login and logout information
 * of different users including: the terminal, login/ logout, username, and time in miliseconds.
 * The user interacts with the program by using a series of commands (USERNAME replaced by the user):
 * 		first USERNAME  -  finds and prints the first session of the specified USERNAME
 * 		last USERNAME   -  finds and prints the last session of the specified USERNAME
 * 		quit            -  exits program
 * 
 * @author Jasmine Fan (jf4654)
 * 
 */

public class LoginStats {
	
		/**
		 * This is main() method of the program
		 * @param args array of Strings provided on the command line when the program is started
		 * The first string of the args array is the name of the input file containing the data for the login/ logouts
		 */
	public static void main(String[] args) {
		
		/*
		 * THIS PART OF THE CODE IS FROM PROJECT 1: COLOR CONVERTER BY PROF. JOANNA KLUKOWSKA
		 * edited to fit the context of the current program
		 * https://edstem.org/us/courses/45688/workspaces/piJDcuQXB4eR1svDbLUdFOY3opm8P3PS
		 * 
		 * File opening, error handling, and receiving input from command line: From lines 38-60 in ColorConverter.java
		 * User Interaction section inspired by ColorConverter.java
		 */
		
		// Verify that the command line argument exists 
		if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		} 
				
		// Verify that command line argument contains a name of an existing file 
		File inputFile = new File(args[0]); 
		if (!inputFile.exists()){
			System.err.println("Error: the file " + inputFile.getAbsolutePath() + " does not exist.\n");
			System.exit(1);
		}
		if (!inputFile.canRead()){
			System.err.println("Error: the file " + inputFile.getAbsolutePath() + 
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		// Open the file for reading string 
		Scanner inputScanner = null;
				
		try {
			inputScanner = new Scanner (inputFile) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file " + inputFile.getAbsolutePath() +
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		String line = null;
		int terminal = 0;
		long timeMiliSec;
		Date time = null;;
		String username = null;
		Scanner parseLine = null;
		boolean login = true;
		RecordList list = new RecordList();
		
		// Read lines of file:
		while(inputScanner.hasNextLine()) {
			try {
			line = inputScanner.nextLine();
			parseLine = new Scanner(line);
			terminal = parseLine.nextInt();
			timeMiliSec = parseLine.nextLong();
			time = new Date(timeMiliSec);
			username = parseLine.next();
			} catch (NoSuchElementException ex){
				System.err.println("Error on line: " + line);
			}
			
			try {
				// Change login boolean if terminal is a negative number (means logout)
				if (terminal < 0) {
					login = false;
					terminal *= -1;
				} else {
					login = true;
				}
				Record newRecord = new Record(terminal, login, username, time);
				// Save to record list
				list.add(newRecord);
			} catch (IllegalArgumentException ex) {
				// Ignore
			}
			
		}
		
		// User Interaction:
		Scanner userInput = new Scanner(System.in);
		String userValue = "";
		Scanner parseUserValue = null;
		String firstOrLast = "";
		String userValueUsername = "";
		
		System.out.println("Welcome to Login Stats!\n");
			System.out.println("Available Commands:");
			System.out.println("\t first USERNAME   -   retrieves first login session for the USER");
			System.out.println("\t last USERNAME    -   retrieves last login session for the USER");
			System.out.println("\t quit             -   terminates this program");
		
		while (!userValue.equalsIgnoreCase("quit")) {
			userValue = userInput.nextLine();
			
			if (!userValue.equalsIgnoreCase("quit")) {
				parseUserValue = new Scanner(userValue);
				firstOrLast = parseUserValue.next();
				userValueUsername = parseUserValue.next();
				Session s = null;
			
				if (firstOrLast.equalsIgnoreCase("first")) {
					s = list.getFirstSession(userValueUsername);
				} else if (firstOrLast.equalsIgnoreCase("last")){
					s = list.getLastSession(userValueUsername);
				}
					System.out.print(s.toString());
			}
		}
			userInput.close();	
	}
		
	
	/*
	 *  END CITATION
	 */
			
}
		
		




