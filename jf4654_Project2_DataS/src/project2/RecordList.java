package project2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * RecordList stores a collection of Record objects.
 * It inherits all of its properties from the ArrayList<Record> class.
 * It adds user-specific functions that allow search by username.
 * 
 * @author Jasmine Fan (jf4654)
 */

@SuppressWarnings("serial")
public class RecordList extends ArrayList<Record>{
	
	/**
	 * Constructs an empty RecordList object.
	 */
	public RecordList() {
	}
	
	/**
	 * Returns the first session of the specified user. The first session is the earliest login time which may still be active (logout is null).
	 * @param user the specified user's username.
	 * @return the first session of the specified user.
	 * @throws IllegalArgumentException if user is null or empty.
	 * @throws NoSuchElementException if the specified user is not in the file.
	 */
	public Session getFirstSession(String user) throws IllegalArgumentException, NoSuchElementException{
		// Handle if the user string is null or empty, throws exception
		if (user == null || user.equals("")) {
			throw new IllegalArgumentException("The specified user cannot be null/ empty.");
		}

		Record login = null;
		Record logout = null;
		int terminalNum = Integer.MAX_VALUE;
		
		for (Record record: this) {  
			// Find and create login record for matching user
			if ((user.equalsIgnoreCase(record.getUsername()) && (record.getTerminal() > 0)) && (record.isLogin() == true)) {
				// get the earliest terminal (smallest num/ terminal)
				if (record.getTerminal() < terminalNum) {
					terminalNum = record.getTerminal();
					login = new Record(terminalNum, record.isLogin(), record.getUsername(), record.getTime());
				}
			} 
			
			// Find and create logout record for matching user
			if (((user.equalsIgnoreCase(record.getUsername()) && (record.isLogin() == false))) && ((record.getTerminal() == terminalNum))){
				logout = new Record(record.getTerminal(), record.isLogin(), record.getUsername(), record.getTime());
				return new Session(login, logout);
			} 
		}
		
		// If did not find logout session in file, but found login session, then that means user is still logged in
		// Returns a non-null login and null logout (still active)
		if ((logout == null) && (login != null)) {
			return new Session(login, logout);
		}
		
		// If user is not found in RecordList, throw exception, user not found
		throw new NoSuchElementException("There is no specified user of the name: " + user + " within the input file.");
	}
	
	/**
	 * Returns the last session of the specified user. The last session is the latest login time.
	 * @param user the specified user's username.
	 * @return the last session of the specified user.
	 * @throws IllegalArgumentException if user is null or empty.
	 * @throws NoSuchElementException if the specified user is not in the file.
	 */
	public Session getLastSession(String user) throws IllegalArgumentException, NoSuchElementException{
		// Handle if the user string is null or empty, throws exception
		if (user == null || user.equals("")) {
			throw new IllegalArgumentException("The specified user cannot be null/ empty.");
		}
		
		Record login = null;
		Record logout = null;
		int terminalNum = 0;
		
		// Loops through whole list to find last session login/ logout
		for (Record record: this) {  
			// Check if record is not null (otherwise cannot get last session because session still active)
			if (record != null) {
				// Find last session login
				if ((user.equalsIgnoreCase(record.getUsername()) && (record.getTerminal() > 0)) && (record.isLogin() == true)) {
					login = new Record(record.getTerminal(), record.isLogin(), record.getUsername(), record.getTime());
					terminalNum = record.getTerminal();
				} 
				
				// Find last logout
				if (((user.equalsIgnoreCase(record.getUsername()) && (record.isLogin() == false))) && (record.getTerminal() == terminalNum)){
					logout = new Record(record.getTerminal(), record.isLogin(), record.getUsername(), record.getTime());	
				}
					
			}
			
		}
		
		// If specified user doesn't match any of records in list, then THROW instance of NoSuchElementException
		// If login equals null then that means there was no user of the specified username in the list
		if (login == null) {
			throw new NoSuchElementException("There is no specified user of the name: " + user + " within the input file.");
		}
		
		return new Session(login, logout);
		
		
	} 

}
