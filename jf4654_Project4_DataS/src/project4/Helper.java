package project4;

/**
 * This class contains the helper methods for project 4.
 * 
 * @author Jasmine Fan (jf4654)
 */

public class Helper {
	
	/**
	 * Converts a long of bytes into a string of proper byte format
	 * and its type (bytes, KB, MB, GB) up to gigabytes
	 * 
	 * @param bytes
	 * @return string of the converted bytes to proper format and its type
	 */
	public static String byteConverter(long bytes) {
		// 1024 bytes to 1 KB
		// 1024 KB/ 1024^2 bytes to 1 MB
		// 1024 MB/ 1024^3 bytes to 1 GB
		
		// if fileSize is greater than 1024^3  GB
			// divide by 1024^3
		// if fileSize is greater than 1024^2  MB
			// divide by 1024^2 
		// if fileSize is greater than 1024    KB
			// divide by 1024
		// else                                bytes
			// don't do anything
		
		if (bytes >= Math.pow(1024, 3)) {
			return String.format("%8.2f GB     ", (double) (bytes / Math.pow(1024, 3)));
		} else if (bytes >= Math.pow(1024, 2)) {
			return String.format("%8.2f MB     ", (double) (bytes / Math.pow(1024, 2)));
		} else if (bytes >= 1024) {
			return String.format("%8.2f KB     ", (double) (bytes / 1024D));
		}
		
		return String.format("%8.2f bytes  ", (double) (bytes)); 
		 
	}
}
