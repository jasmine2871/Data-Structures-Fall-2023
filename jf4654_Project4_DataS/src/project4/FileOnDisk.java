package project4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a file or directory in the program.
 * It gets the total size of all files in a directory and its subdirectories, 
 * or the size of a single file. It inherits from the File class. 
 * Also gets the list of the largest files in a directory.
 * 
 * @author Jasmine Fan (jf4654)
 */

public class FileOnDisk extends File {

	private static final long serialVersionUID = 1L;
	private List<FileOnDisk> exploredDirectoryList = new ArrayList<>();;
	private long totalSize;
	private boolean alreadyCalculated = false;
	public String cannonicalPath;
	private File file;
	
	/**
	 * Constructor for FileOnDisk
	 * 
	 * @throws NullPointerException if pathName is null
	 * @throws IOExceptiion if cannot get cannonical path of file
	 * @param pathName the path of the file/ directory
	 */
	public FileOnDisk(String pathName) throws NullPointerException, IOException{
		super(pathName);  // Super constructor throws NullPointer

		this.file = new File(pathName);
		
		// Check if directory or file
		// Save cannonical path
		if (this.file.isDirectory()) {
			try {
				this.cannonicalPath = this.file.getCanonicalPath();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else if (this.file.isFile()) {
			try {
				this.cannonicalPath = this.file.getCanonicalPath();
				this.totalSize = this.file.length();
				this.alreadyCalculated = true;  // No need to call recusive method to get size
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	/**
	 * Gets total size of:
	 * 		(for a directory) the total size of all the files and subdirectories stored in it
	 * 		(for a file) the size of the actual file
	 * 
	 * @throws IOException from exploreDir, if cannot get cannonical path
	 * @return total size of file or directory
	 */
	public long getTotalSize() throws IOException {
		// if the total size is already calculated, then do not calculate it again
	    if (alreadyCalculated == false) {
	    	// Recursive method exploreDir sets exploredDirectoryList
	        totalSize = exploreDir(this);
	        // Sort the exploredDirectoryList
	        Collections.sort(exploredDirectoryList, new FileOnDiskComparatorBySize());
	        alreadyCalculated = true;
	    }
	    
	    return totalSize;
	}

	/**
	 * Returns a list of the largest files of a specified number of files
	 * 
	 * @param numOfFiles the amount of files wanted
	 * @return list of the largest files of the specified numOfFiles
	 */
	public List<FileOnDisk> getLargestFiles(int numOfFiles) {		
		// Check if already calculated, if not, calculate
		// aka call wrapper of recursive function
	    if (alreadyCalculated == false) {
	        try {
	            getTotalSize();  // wrapper
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
	    }

	    List<FileOnDisk> largestFiles = new ArrayList<>();
	    
	    // Check if this FileOnDisk is a file or a directory
	    // If file, return null
	    // If directory, find largest files
	    if (this.isFile()) {
	        return null;
	    }
	    
	    if (this.isDirectory()) {
	        if (numOfFiles == 0) {
	            return null;
	        }

	        // If the numOfFiles is greater than size of exploredDirectoryList
	        // Go through exploredDirectoryList, find files, add to largest files list
	        // aka don't return the directories
	        if (numOfFiles > exploredDirectoryList.size()) {
	        	for (FileOnDisk file : exploredDirectoryList) {
		        	// Check if the file is a file
		            if (file.isFile()) {
		            	// If it is a file, add it to the largestFile list
		                largestFiles.add(file);
		                
		            }
		            
		        }
	        	
	            return largestFiles;
	        }
	        
	        int addCounter = 0;
	        // For every file in exploredDirectoryList
	        // * since exploredDirectoryList is already sorted by wrapper
	        //   no need to worry about comparing it
	        for (FileOnDisk file : exploredDirectoryList) {
	        	// Check if the file is a file
	            if (file.isFile()) {
	            	// If it is a file, add it to the largestFile list
	                largestFiles.add(file);
	                addCounter++;
	            }
	            
	            // When the addCounter is greater than or equal to numOfFiles 
	            // just return the largestFiles list
	            if (addCounter >= numOfFiles) {
	            	return largestFiles;
	            }
	        }
	    }

	    return largestFiles;
	}

	/**
	 * Converts the total bytes of the directory/ file into a proper format and
	 * adds the proper type of bytes, also adds the cannonical path to the string
	 * 
	 * @return returns the bytes of the file and the cannonical path
	 */
	@Override
	public String toString(){
		
	    if (alreadyCalculated == false) {
	        try {
	            getTotalSize();
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
	        alreadyCalculated = true;
	    }

	    String fileSize = Helper.byteConverter(totalSize);
	    
	    return fileSize + cannonicalPath;
	}

	/**
	 * Goes through the specified directory and the directory's subdirectories,
	 * summing the total bytes of all the files. Adds the explored directories
	 * and files to exploredDirectoryList.
	 * 
	 * This method is recursive.
	 * 
	 * @param directory the FileOnDisk obj to be explored
	 * @throws IOException if cannot get cannonical path
	 * @return the number of bytes of the directory/ file
	 */
	private long exploreDir(FileOnDisk directory) {
	    long totalSize = 0;

	    try {
	    	// Check if directory exists and the directory is a directory
		    if (directory.exists() && directory.isDirectory()) {
		    	// Create list of files from directory
		        File[] filesAndDir = directory.listFiles();
		        
		        if (filesAndDir != null) {
		        	// For each file/ directory in filesAndDir create new FileOnDisk obj
		            for (File fileOrDir : filesAndDir) {
		            	// Use cannonical path to avoid circling
		                FileOnDisk fileDir = new FileOnDisk(fileOrDir.getCanonicalPath());
		                
		                // Check if fileDir is directory
		                if (fileDir.isDirectory()) {
		                	// if is directory, explore the directory
							totalSize += exploreDir(fileDir);
						} else if (fileDir.isFile()) {
							// if is file, just add size of file to total
							totalSize += fileDir.length();
						}
		                
		                // If the exploredDirectoryList does not contain the fileDir, add it
		                if (!exploredDirectoryList.contains(fileDir)) {
		                    exploredDirectoryList.add(fileDir);
		                }
		            }
		        }
		    }
	    
	    } catch (IOException e) {
	    	 System.out.println(e.getMessage());
	    }

	    return totalSize;
	}
	

}
 