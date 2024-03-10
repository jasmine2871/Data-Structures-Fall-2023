package project4;

import java.io.IOException;
import java.util.Comparator;

/**
 * This class implements comparison between FileOnDisk objects 
 * that is different than the comparison provided by the File class.
 * 
 * @author Jasmine Fan (jf4654)
 */

public class FileOnDiskComparatorBySize implements Comparator<FileOnDisk> {
	
	/**
	 * Compares the two FileOnDisk objects by their size 
	 * (number of bytes), and, if the sizes are equal by 
	 * their path names (using lexicographic ordering)
	 * (by A-Z)
	 * 
	 * @param file1 the first object to be compared
	 * @param file2 the second object to be compared
	 * @return a negative integer, zero, or a positive integer as 
	 *         the first argument is less than, equal to, or greater 
	 *         than the second
	 */
	public int compare(FileOnDisk o1, FileOnDisk o2) {
		// Comupte difference of o1 and o2 size
		long byteDifference = o1.length() - o2.length();
		
		// if byteDifference is pos, o1 bigger than o2, return 1
		// if byteDifference is neg, o1 smaller than o1, return -1
		
		// Compare by size
		if (byteDifference != 0) {
			if (byteDifference > 0) {
				return -1;
			} else if (byteDifference < 0) {
				return 1;
			}
		} else { // If size is same, compare by lexigeograph
			try {
				return o1.getCanonicalPath().compareTo(o2.getCanonicalPath());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return 0;
			
	}

}
