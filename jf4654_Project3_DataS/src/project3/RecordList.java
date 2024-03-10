package project3;

import java.util.NoSuchElementException;


/**
 * RecordList stores a collection of Record objects.
 * It inherits all of its properties from the SortedLinkedList<Record> class.
 * It adds user-specific functions that allow search by username.
 * 
 * @author Jasmine Fan (jf4654)
 */

public class RecordList extends SortedLinkedList<Record>{
	
	/**
	 * Constructs an empty RecordList object.
	 */
	public RecordList() {
	}
	
	/**
	 * Returns the first session of the specified user. The first session is the 
	 * earliest login time which may still be active (logout is null).
	 * 
	 * @param user the specified user's username.
	 * @return the first session of the specified user.
	 * @throws IllegalArgumentException if user is null or empty.
	 * @throws NoSuchElementException if the specified user is not in the file.
	 */
	public Session getFirstSession(String user) 
			throws IllegalArgumentException, NoSuchElementException{
		// Handle if the user string is null or empty, throws exception
		if (user == null || user.equals("")) {
			throw new IllegalArgumentException("The specified user cannot be null/ empty.");
		}

		Record login = null;
		Record logout = null;
		int terminalNum = Integer.MAX_VALUE;
		
		for (Record record: this) {  
			// Find and create login record for matching user
			if ((user.equalsIgnoreCase(record.getUsername()) && 
				(record.getTerminal() > 0)) && (record.isLogin() == true)) {
				// get the earliest terminal (smallest num/ terminal)
				if (record.getTerminal() < terminalNum) {
					terminalNum = record.getTerminal();
					login = new Record(terminalNum, record.isLogin(),
							record.getUsername(), record.getTime());
				}
			} 
			
			// Find and create logout record for matching user
			if (((user.equalsIgnoreCase(record.getUsername()) && 
				(record.isLogin() == false))) && ((record.getTerminal() == terminalNum))){
				logout = new Record(record.getTerminal(), record.isLogin(), 
						 record.getUsername(), record.getTime()); 
				return new Session(login, logout);
			} 
		}
		
		// If did not find logout session in file, but found login session,
		// then that means user is still logged in
		// Returns a non-null login and null logout (still active)
		if ((logout == null) && (login != null)) {
			return new Session(login, logout);
		}
		
		// If user is not found in RecordList, throw exception, user not found
		throw new NoSuchElementException("There is no specified user of the name: "
				+ user + " within the input file.");
	}
	
	/**
	 * Returns the last session of the specified user. The last session is the latest login time.
	 * @param user the specified user's username.
	 * @return the last session of the specified user.
	 * @throws IllegalArgumentException if user is null or empty.
	 * @throws NoSuchElementException if the specified user is not in the file.
	 */
	public Session getLastSession(String user) 
			throws IllegalArgumentException, NoSuchElementException{
		
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
				if ((user.equalsIgnoreCase(record.getUsername()) &&
					(record.getTerminal() > 0)) && (record.isLogin() == true)) {
					login = new Record(record.getTerminal(), record.isLogin(),
							record.getUsername(), record.getTime());
					terminalNum = record.getTerminal();
				} 
				
				// Find last logout
				if (((user.equalsIgnoreCase(record.getUsername()) &&
					(record.isLogin() == false))) && 
					(record.getTerminal() == terminalNum)){
					logout = new Record(record.getTerminal(), record.isLogin(),
							 record.getUsername(), record.getTime());	
				}	
			}
		}
		
		// If specified user doesn't match any of records in list,
		// then THROW instance of NoSuchElementException
		// If login equals null then that means there was 
		// no user of the specified username in the list
		if (login == null) {
			throw new NoSuchElementException("There is no specified user of the name: "
					  + user + " within the input file.");
		}
		
		return new Session(login, logout);
	} 
	
	/**
	 * Returns the total time in milliseconds.
	 * 
	 * @param user username of the specified user.
	 * @throws NoSuchElementException if the specified user is not found.
	 * @throws IllegalArgumentException if the specified user is null or empty.
	 * @return return the total amount of time in milliseconds
	 * 		   that the user was logged in on the system.
	 */
	public long getTotalTime(String user) throws NoSuchElementException, IllegalArgumentException {
		// Valid argument to function is non-null, non-empty string; throw IllArgExcep if null or ""
		if ((user == null || user.equals(""))){
			throw new IllegalArgumentException("Username cannot be null.");
		}
		
		SortedLinkedList<Session> totalUserSessions = null;
		
		// Throw NoSuchEleExcep from getAllSessions (user not match)
		try { 
			totalUserSessions = this.getAllSessions(user);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("User not found.");
		}
		
		long totalTime = 0;
		// Check if has logout AND login, if still active, cannot determine value, do not add those
		for (Session session: totalUserSessions) {
			if (session.getLogoutTime() != null) {
				totalTime += session.getDuration();
			}
		}
		
		return totalTime;
	}
	
	/**
	 * Returns the list of all sessions associated with the specified user
	 * from earliest login time to latest login time.
	 * 
	 * @param user username of the specified user.
	 * @throws NoSuchElementException if the specified user is not found.
	 * @throws IllegalArgumentException if the specified user is null or empty.
	 * @return sessionList containing all sessions of specified user.
	 */
	public SortedLinkedList<Session> getAllSessions(String user) 
			throws NoSuchElementException, IllegalArgumentException{
		// Valid argument to function is non-null, non-empty string; throw IllArgExcep if null or ""
		if ((user == null || user.equals(""))){
			throw new IllegalArgumentException("Username cannot be null.");
		}
		
		SortedLinkedList<Record> recordLogin = new SortedLinkedList<>();
		
		// Iterate through list, find all login records of specified user, add to recordLogin
		for (Record record: this) {
			if (user.equals(record.getUsername()) && (record.isLogin())) {
				recordLogin.add(record);
			}
		}
		
		// Check if user exists in data file
		if (recordLogin.size() == 0) {
			throw new NoSuchElementException("User not found.");
		}
		
		SortedLinkedList<Session> sessionList = new SortedLinkedList<>();
		Session session = null;
		// Iterate through list again and find all logouts of specified user
		// Match logins and logouts of specified user
		for (Record logoutRecord: this) {
			// If user is specified user and the recordLogin linked list DOES NOT contain logoutRecord 
			// ^ aka returns !false if logout, !true if login  (don't want to pair login and login)
			if (user.equals(logoutRecord.getUsername()) && !(recordLogin.contains(logoutRecord))) {
				for (Record loginRecord: recordLogin) {
					if (logoutRecord.getTerminal() == loginRecord.getTerminal()) {
						session = new Session(loginRecord, logoutRecord);
						sessionList.add(session);
						// Remove matched logins
						recordLogin.remove(loginRecord);
					}
					
				}
			}
		}

		// Add the rest of the active logins to session list by making them active sessions
		for(Record activeLogin: recordLogin) {
			session = new Session (activeLogin, null);
			sessionList.add(session);
		}
		
		return sessionList;
		 
	}

}