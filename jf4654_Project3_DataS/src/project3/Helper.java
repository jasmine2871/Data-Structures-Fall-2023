package project3;

/**
 * Contains the helper methods for project 3.
 * 
 * @author Jasmine Fan (jf4654)
 */
public class Helper {
	
	private static final int NUM_OF_MILLISEC_IN_SEC = 1000;
	private static final int NUM_OF_SEC_IN_MIN = 60;
	private static final int NUM_OF_MIN_IN_HOUR = 60;
	private static final int NUM_OF_HOUR_IN_DAY = 24;
	
	/**
	 * Converts a long (duration in milliseconds) to a string format
	 * @param duration the duration in milliseconds
	 * @param type the type of string to be returned, 
	 * 		  only type 2 is a shortened version of the string
	 * 		  otherwise, return the normal unabbreviated version of the string
	 * @return string conversion of the long duration
	 */
	public static String durationToString(long duration, int type) {
		if (duration == 0) {
			return String.format("duration sessions of specified user are all active"); 
		}
		
		long seconds = duration / NUM_OF_MILLISEC_IN_SEC;
		long minutes = seconds / NUM_OF_SEC_IN_MIN;
		long hours = minutes / NUM_OF_MIN_IN_HOUR;
		long days = hours / NUM_OF_HOUR_IN_DAY;
		
		// Get remainders of time
		hours = hours % NUM_OF_HOUR_IN_DAY; 
		minutes = minutes % NUM_OF_MIN_IN_HOUR;
		seconds = seconds % NUM_OF_SEC_IN_MIN;
		
		if (type == 2) {
			return String.format("duration "  +  days + "d " 
				+ hours + "h "+ minutes + "m " + seconds + "s");
		}
		
		return String.format("duration "  +  days + " days, " 
			+ hours + " hours, "+ minutes + " minutes, " + seconds + " seconds");
		
	}
}
