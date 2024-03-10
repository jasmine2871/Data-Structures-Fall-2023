package project5;

/**
 * This class represents a word as a string, and the count of 
 * how many occurences of that specific string appear in a
 * text file.
 * 
 * @author Jasmine Fan jf4654
 */

public class Word implements Comparable<Word>{
	
	private String wordStr;
	private int count;
	
	/**
	 * Creates a new Word object with the given string and count of 1
	 * 
	 * @param word String of the word
	 */
	public Word(String word) {
		this.wordStr = word;
		this.count = 1;
	}
	
	/**
	 * A method that increments the count associated with this Word 
	 * object by 1 and returns the updated value of the count
	 * 
	 * @return count incremented up by 1
	 */
	public int incrementCount() {
		this.count = this.count + 1;
		return this.count;
	}
	
	/**
	 * A method that returns the word associated with this Word object
	 * 
	 * @return the word associated with the word obj
	 */
	public String getWord() {
		return this.wordStr;
	}
	
	/**
	 * A method that returns the count associated with this Word object
	 * 
	 * @return the count associated with the Word obj
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * A method that returns the string representation of this Word 
	 * object. The representation should consist of the count that is 
	 * right aligned within a field of 5 characters, followed by two 
	 * spaces, followed by the word itself
	 * 
	 * @return the count of the word and the word itself
	 */
	public String toString() {
		return String.format("%5d  %s", this.getCount(), this.getWord()); 
	}
	
	/**
	 * Compares two word objects by natural order
	 * 
	 * @param o word to compare to
	 * @return neg int if o is less than word, 
	 *         pos int if o is greater than word,
	 *         zero if o and word are equal
	 */
	@Override
	public int compareTo(Word o) {
//		if (!(o instanceof Word)) { return -1; }
//		
//		if (o.getWord().compareTo(this.getWord()) < 0) {
//			return -1;
//		} else if (o.getWord().compareTo(this.getWord()) > 0) {
//			return 1;
//		} else if (o.getWord().compareTo(this.getWord()) == 0) {
//			return 0;
//		}
//		
		return (this.getWord().compareToIgnoreCase(o.getWord()));
	}
	
	/** 
	 * Two Word objects are equal if their words and counts are equal.
	 * 
	 * @param o word to compare tos
	 * @return true if equal, false if not equal
	 */
	public boolean equals(Word o) {
		if (!(o instanceof Word)) { return false; }
		
		if ((o.getWord().equals(this.getWord())) && (o.getCount() == this.getCount())){
			return true;
		}
		
		return false;
	}

}
