package project3;

import java.util.Date;

/**
 * Record class is used to represent a user's login or logout.
 * It stores the user's username, the terminal they logged in/ logged out on, if they are logged in,
 * and the time and date they logged in/ logged out.
 * 
 * @author Jasmine Fan (jf4654)
 * 
 */

public class Record implements Comparable<Record> {
	
	private int terminal;
	private boolean login;
	private boolean logout;
	private String username;
	private Date time;
	
	/**
	 * Constructs a new Record object with specified terminal, login, username, and time components.
	 * @param terminal a positive integer indicating what terminal the user logged in/ logged out on
	 * @param login indicates if user is logged in or out (true for login, false for logout)
	 * @param username the identifier of the user (from the input file)
	 * @param time the date and time the user logged in/ logged out
	 * @throws IllegalArgumentException if the terminal is a non-positive or it is empty/ null
	 */
	public Record(int terminal, boolean login, String username, Date time) throws IllegalArgumentException {
		// Check if terminal is a positive integer or null
		if ((terminal < 1) || (String.valueOf(terminal).equals(""))){
			throw new IllegalArgumentException("Invalid terminal value,"
					+ " value must be a positive integer.");
		} else {
			this.terminal = terminal;
		}
		
		// Login is bool value that indicates if given Record obj represents
		// a login or logout record (true is login, false is logout)
		if (login) { 
			this.login = login;   
		} else {
			this.logout = login;   
		}
		
		// Name of user
		this.username = username;
		// Time is Date obj, represents the time and date when user login or logout 
		this.time = time;
	}

	/**
	 * Returns the terminal of the Record object.
	 * @return the terminal of the Record object.
	 */
	public int getTerminal() {
		return terminal;
	}

	/**
	 * Returns if the user is logged in or not (true when is logged in, false when logged out).
	 * @return if the user is logged in or not.
	 */
	public boolean isLogin() { 
		return login;
	}

	/**
	 * Returns if the user is logged out or not (true when is logged out, false when logged in).
	 * @return if the user is logged out or not.
	 */
	public boolean isLogout() {
		// if login is true, that means hasn't logged out (false)
		if (this.login) {
			this.logout = false;
		} else if (!this.login){
			this.logout = true;
		}
		return logout;
	}

	/**
	 * Returns the username of the Record object.
	 * @return the username of the Record object.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the date/time of the Record object.
	 * @return the date/time of the Record object.
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * Compares two Record objects by the time associated with the login/ logout
	 * @param record Record object to be compared to
	 * @return negative integer (less than), zero (equal to), 
	 * 		   or positive integer (greater than) specified object
	 */
	@Override
	public int compareTo(Record record) {
		// check time
		if (this.getTime().getTime() > record.getTime().getTime()) {
			return 1;
		} else if (this.getTime().getTime() < record.getTime().getTime()) {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Checks two Record objects login OR logouts to see if they are equal.
	 * @param record Record object to be compared to
	 * @return true if both Records are equal, returns false if not equal
	 */
	@Override
	public boolean equals(Object record) {
		// This class overrides the equals method from the Object class. 
		
		if (this == record) {
			return true;
		}
		
		if ((record == null) || !(record instanceof Record)) {
			return false;
		}
		
		// Two Record objects are equal if they are the same kind of record (either login or logout), 
		// and if they have the same terminal, username and time associated with them
		if (((this.login && ((Record) record).login) || (this.logout && ((Record) record).logout))){
			boolean terminalCheck = (this.terminal == ((Record) record).getTerminal())
									? true : false;
			boolean usernameCheck = (this.username.equals(((Record) record).getUsername())) 
									? true : false;
			
			if ((terminalCheck && usernameCheck) && 
					(this.getTime().getTime() == ((Record) record).getTime().getTime())) {
				return true;	
			}
		}
		
		return false;
		
	}
}

	