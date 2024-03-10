package project3;

import java.util.Date;

/**
 * Session class represents a complete session of one specified user in one specified terminal.
 * It stores two Record objects and checks if they are a pair. It returns the username, login/logout time, 
 * duration, and terminal of the specific session. Overrides the
 * toString() method to match the format of project specifications.
 * 
 * @author Jasmine Fan (jf4654)
 */

public class Session implements Comparable<Session>{ 
	
	private Record login;
	private Record logout;
	
	/**
	 * Constructs a new Session by using two Record objects, one as login and the other as logout.
	 * @param login the login record of a specified user, cannot be null
	 * @param logout the logout record of a specified user, can be null
	 * @throws IllegalArgumentException when login is 
	 * 		   null/ empty and when the two parameters are not a pair	
	 */
	public Session (Record login, Record logout) throws IllegalArgumentException{
		// Login cannot be null, if it is null throw exception
		if (login == null) {
			throw new IllegalArgumentException("Login cannot be null");
		} 
		
		this.login = login;
		this.logout = logout;
		
		// If both login and logout records are both login records, then they should not be a pair
		if (!((this.login.isLogin()) || ((this.logout != null) && (this.logout.isLogout())))) {
			throw new IllegalArgumentException("Records are not a single session."
					  + " The records contain either two logins or two logouts.");
		}
		
		// Check if matching:
		if (logout == null) { 
			// Still logged in
		} else if (((logout != null) && 
					(login.getUsername().equals(logout.getUsername()))) && 
				   ((login.getTerminal() == logout.getTerminal()) && 
				    (login.getTime().getTime() < logout.getTime().getTime()))){
			// Pair, continue
		} else {
			// Not a pair, throw exception
			throw new IllegalArgumentException("Records are not a single session.");	
		}
	}
	
	/**
	 * Returns the terminal of the session if both records have the same terminal.
	 * @return the terminal of the session.
	 */
	public int getTerminal(){
		return this.login.getTerminal();
	}
	
	/**
	 * Returns the login time of the login record.
	 * @return the login time of the login record.
	 */
	public Date getLoginTime() {
		return this.login.getTime();
	}

	/**
	 * Returns the logout time of the logout record. If the user is still logged in, return null.
	 * @return the logout time of the logout record.
	 * @throws IllegalArgumentException if records of session are not a pair.
	 */
	public Date getLogoutTime() throws IllegalArgumentException{
		// Cannot get logout time if session still active (logout is null)
		if (this.logout == null) {
			return null;
		}
		
		if ((this.login.getUsername().equals(this.logout.getUsername())) &&
			(this.login.getTerminal() == this.logout.getTerminal())) {
			return this.logout.getTime();
		}
		
		throw new IllegalArgumentException("Cannot return logout time of session"
				  + " because records are not a session.");
	}
	
	/**
	 * Returns the username of the user of the session.
	 * @return the username of the user of the session.
	 */
	public String getUsername(){	
		return this.login.getUsername();
	}
	
	/**
	 * Returns the duration of the session in milliseconds.
	 * @return the duration of the session in milliseconds.
	 */
	public long getDuration() {
		// Get start time and end time and solve for time 
		// between login and logout, return milisec of duration
		long duration;
		
		if (this.logout == null) {
			return -1;
		} else { 
			duration = this.logout.getTime().getTime() - this.login.getTime().getTime();
		}

		return duration;
	}
	
	
	/**
	 * Prints out information of the session. 
	 * @return empty string for visual space, but prints out session information.
	 */
	@Override
	public String toString() {
		// Check if still active
		if (this.logout == null) {
			// If logout is null, then that means still logged in, there is no duration or logout time
			return String.format("\n%s, terminal %d, duration active session\n"
					+ "\t logged in: %s\n"
					+ "\t logged out: still logged in.\n",
					this.getUsername(), this.getTerminal(), this.getLoginTime().toString());
		} else {
	
			return String.format("\n%s, terminal %d, " + 
					Helper.durationToString(this.getDuration(), 0) + "\n" + 
					"\t logged in: %s\n" +
					"\t logged out: %s\n", 
					this.getUsername(), this.getTerminal(), 
					this.getLoginTime().toString(), this.getLogoutTime().toString());
		}
		
	}

	/**
	 * Compares two Session objects by their login 
	 * records (the time associated with the login).
	 * 
	 * @param session Session object to compare to.
	 * @return negative integer (less than), zero (equal to),
	 * 		   or positive integer (greater than) specified object.
	 */
	@Override
	public int compareTo(Session session) {
		// The Session objects should be compared by their login records
		// (i.e., the time associated with their login records).
		// The Session object A is considered smaller than
		// the Session object B, if the login Record for A is smaller than 
		// the login record for B.
		
		// This is object A, session is object B
		if (this.getLoginTime().getTime() > session.getLoginTime().getTime()) {
			return 1;
		} else if (this.getLoginTime().getTime() < session.getLoginTime().getTime()) {
			return -1;
		}

		return 0;
	}
	
	/**
	 * Checks two Session objects login AND logouts to see if they are equal.
	 * 
	 * @param session Session object to compare to.
	 * @return true if both Sessions are equal, returns false if not equal.
	 */
	@Override
	public boolean equals(Object session) {
		// This class should override the equals method from the Object class.
		// Two Session objects are equal if they have the same login and logout records.
		if (this == session) {
			return true;
		}
		
		if ((session == null) || !(session instanceof Session)){
			return false;
		}
		
		if ((this.login.equals(((Session) session).login)) && ((this.logout == null) &&
				(((Session) session).logout == null))){
			return true;
		} else if ((this.login.equals(((Session) session).login)) &&
				this.logout.equals(((Session) session).logout)) {
			return true;
		}
		
		return false;
		
	}

}
