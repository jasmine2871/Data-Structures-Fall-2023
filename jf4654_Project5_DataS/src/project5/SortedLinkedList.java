package project5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in decending order
 * based on the natural order of the elements.
 * This list does not allow null elements.
 * 
 * @author Jasmine Fan (jf4654)
 */
public class SortedLinkedList implements Index{
	
//	public static void main(String[] args) {
//		
//		//Index listThing = new SortedLinkedList();
//		Index listThing2 = new BSTIndex();
//		
//		
//		
////		listThing.add("a");
////		listThing.add("hare"); 
////		listThing.add("and");
////		listThing.add("one");
////		listThing.add("the");
////		//System.out.println(listThing.toString());
////		// listThing.remove("anotherWOrd");
////		System.out.println(listThing.toString());
////		
//		listThing2.add("a");
//		listThing2.add("hare");
//		listThing2.add("and");
//		listThing2.add("one");
//		listThing2.add("one");
//		listThing2.add("one");
//		listThing2.add("one");
//		listThing2.add("one");
//		listThing2.add("one");
//		listThing2.add("the");
//		listThing2.remove(null);
//		System.out.println(listThing2.toString());
//		System.out.println();
//		
//		// Iterator<Word> it = listThing.iterator();
////		Iterator<Word> it2 = listThing2.iterator();
////		
////		while( it2.hasNext()) {
////			//Word tmp = it.next();
////			Word tmp2 = it2.next();
////			//System.out.println(tmp.getCount());
////			System.out.println(tmp2.getCount());
////		}
//		
//		
////		System.out.println(listThing.size());
////		System.out.println(listThing2.size());
////	
////		Iterator<Word> it = listThing.iterator();
////		Iterator<Word> it2 = listThing2.iterator();
////		
////		System.out.println(listThing.equals(listThing2));
////		
////		while(it.hasNext() && it2.hasNext()) {
////			Word tmp = it.next();
////			Word tmp2 = it2.next();
////			System.out.println(tmp);
////			System.out.println(tmp2);
////			
////			if (!(tmp.getWord().equals(tmp2.getWord()))) {
////				System.out.println("*************");
////				System.out.println(tmp);
////				System.out.println(tmp2);
////				System.out.println("Not equal");
////				System.out.println("*************");
////    		}
////			
////			System.out.println("equal");
////			
////		}
////		
////		if (it.hasNext() || it2.hasNext()) {
////			System.out.println("ACTUALLY IT'S NOT EQUAL");
////		}
//		
//	}
//	
//	
	
	
	
	private Node head;
	private Node tail;
	private int size;
	private ArrayList<String> wordStorage = new ArrayList<>();
	
	/**
	 * Constructor for a empty SortedLinkedList
	 */
	public SortedLinkedList() {
		head = null;
		tail = null; 
		size = 0;
	}

	/**
	 * Returns an iterator for SortedLinkedList
	 * 
	 * @return returns the list iterator
	 */
	@Override
	public Iterator<Word> iterator() {
		return new ListIterator();
	}
	
	
	/**
     * Adds an item to the index in sorted order. If the Word object
     * with the same string as item already exists, its count should be
     * incremented by one and no new Word objects should be created.
     *
     * @param item new item to be added
     * @throws IllegalArgumentException when item is null
     */
	@Override
	public void add(String item) throws IllegalArgumentException {
	    if (item == null) {
	        throw new IllegalArgumentException("Item cannot be null.");
	    }

	    Word itemWord = new Word(item);

	    if (!wordStorage.contains(item)) {
	        wordStorage.add(item);

	        Node newElement = new Node(itemWord);

	        if (size == 0) {
	            // Handle if list is empty
	            head = newElement;
	            tail = newElement;
	        } else if (head.data.compareTo(itemWord) >= 0) {
	            // Handle add before head
	            newElement.next = head;
	            head.prev = newElement;
	            head = newElement;
	        } else if (tail.data.compareTo(itemWord) <= 0) {
	            // Handle add after tail
	            newElement.prev = tail;
	            tail.next = newElement;
	            tail = newElement;
	        } else {
	            // Handle add in the middle of the list
	            Node current = head;

	            while (current.next != null && current.next.data.compareTo(itemWord) < 0) {
	                current = current.next;
	            }

	            newElement.next = current.next;
	            newElement.prev = current;
	            current.next.prev = newElement;
	            current.next = newElement;
	        }

	        size++;
	    } else {
	        // Increment count of equal word by one (not create a new Word obj)
	        Iterator<Word> itr = this.iterator();
	        while (itr.hasNext()) {
	            Word currentWord = itr.next();
	            if (item.equalsIgnoreCase(currentWord.getWord())) {
	                currentWord.incrementCount();
	                break;
	            }
	        }
	    }
	}


	/**
     * Removes an item from the index if it exists, otherwise the index remains
     * unchanged. This operation should remove the Word object matching the
     * item regardless of what the count is.
     *
     * @param item item to be removed
     */
	@Override
	public void remove(String item) {
    	if ((size() == 0) || (item == null)) {
    		// Nothing to remove if size is 0
    		return;
    	}
    	
    	// if word storage contains itemWord, remove it
    	// 		^ should remove the word obj regardless of count!!
    	if (wordStorage.contains(item)) {
    		// Should have no duplicates in wordStorage, can comforatbly remove
    		wordStorage.remove(item);
    		
    		if ((size() == 1) && (head.data.getWord().equals(item))) {
	    		// Handle if size is 1 and the element in is equals item
				head = null;
				tail = null;
				size--;
				return;
				
	    	} else if (head.data.getWord().equals(item)) { 
	    		// Handle if head equals item, set head to the next node
	    		head = head.next;
	    		size--;
	    		return;
	    	} else if (!(head.data.getWord().equals(item)) && !(tail.data.getWord().equals(item))) { 
	    		// Handle removing an element from the middle of the list
	    		Node current = head;
		    	Node beforeCurrent = null;
		    	Node afterCurrent = null;
		    	
		    	while ((current.next.next != null) && (!(current.data.getWord().equals(item)))) {
		    		current = current.next;
		    	}
		    	
				beforeCurrent = current.prev;
				afterCurrent = current.next;
				
				beforeCurrent.next = afterCurrent;
				afterCurrent.prev = beforeCurrent;
				
				size--;
				return;
	    	} else if (tail.data.getWord().equals(item)) { // Handle removing from the tail
	    		tail = tail.prev;
	    		tail.next = null;
	    		size--;
	    		return;
	    	}
    	}
    	// if item not in wordStorage, no need to remove
    	
	}

	
	/**
     * Returns the count of the Word object associated with the item, or -1
     * if such a Word object does not exits.
     *
     * @param item item whose count should be returned
     * @return the count associated with the item, or -1 if the item does not exist
     */
	@Override
	public int get(String item){
		
		// if item not in wordStorage then shouldn't be in linked list
		if (!(wordStorage.contains(item))) {
			return -1;
		}
		
		// iterate through list, find word, get the count
		Iterator<Word> itr = this.iterator();
    	Word getWord = null;
    	
    	
    	while(itr.hasNext()) {
    		getWord = itr.next();
    		
    		if (item.equals(getWord.getWord())) {
    			return getWord.getCount();
    		}
    		
    	}		
		
		return -1;
	}
	
	
	/**
     * Returns number of unique words stored in the index.
     * NOTE: this counts each word only once even it the count associated
     * with a word is larger than one.
     *
     * @return number of items stored in the index.
     */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * In addition they both should provide implementation of the equals 
	 * method and the toString methods inherited from the Object class.
	 * Two Index objects are considered equal if they contain the same 
	 * number of elements, and those elements are pairwise equal (when 
	 * retrieved from the Index object according to the natural ordering 
	 * of the Word objects).
	 * 
	 * @param obj
	 * @return boolean true if equal, false if not equal
	 */
	@Override
	public boolean equals(Object obj) {
		// NOTE that this implies that two different implementations 
		// of the Index interface should be able to be compared using 
		// this equals method.
		
		
		if (obj == this) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Index)) { return false; }
		
		if (!(this.size == ((Index) (obj)).size())) {
			return false;
		} 
		
    	Index objectList = (Index) obj;
    	
    	Iterator<Word> thisItr = this.iterator();
    	Iterator<Word> objItr = objectList.iterator();
    	Word thisElement = null;
    	Word objElement = null;
    	
    	// check if each element in list is same
    	while (thisItr.hasNext() && objItr.hasNext()) {
    		thisElement = thisItr.next();
    		objElement = objItr.next();
    		
    		if (!(thisElement.equals(objElement))) {
    			return false;
    		}
    	}
    	
    	// check if both iterators have reached the end of the lists
    	if (!thisItr.hasNext() && !objItr.hasNext()) {
    		return true;
    	} 
    	
    	return false;
		
	}
	
	/**
	 * The string representation of an Index object consists of a list 
	 * of all the Word objects sorted by their natural ordering, enclosed 
	 * in square brackets ("[]"). Adjacent elements are separated by the 
	 * characters ", " (comma and space).
	 */
	@Override
	public String toString() {
		Node current = head;
		String string = "[";
		
		
		while (current != null) {
			string += current.data.toString() + ", ";
			current = current.next;
		}
		
		return string.substring(0, string.length() - 2) + "]";
		
	}
	
	
	/* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> {
        Word data;
        Node next;
        Node prev;
        
        /**
         * Constructor for a node with a single argument of Word
         * 
         * @param data the word in the node
         */
        Node(Word data) {
            if (data == null) throw new NullPointerException ("Does not allow null");
            this.data = data;
        }
        
        /**
         * Constructor for a node with arguments of Word, 
         * the next node, and the previous node before the
         * node containing this specific Word
         * 
         * @param data the word in the node
         * @param next the node after the node of specific Word node
         * @param prev the node before the node of specific Word node
         */
        @SuppressWarnings("unused")
		Node (Word data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        
        /**
         * Compares the data of two nodes
         * 
         * @param n the node to compare to
         * @return neg int if n is less than data, 
		 *         pos int if n is greater than data,
		 *         zero if n and data are equal
         */
        public int compareTo( Node n ) {
            return this.data.compareTo(n.data);
        }
    }
    

    /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<Word> {

        Node nextToReturn = head;
        Node previousReturn = null;
        
        /**
         * Checks if the iterator has reached the end of the list
         * @return returns a boolean true if iterator has next,
         * 		   false if there are no more nodes to iterate to
         */
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }
        
        /**
         * Moves the iterator to the next node and returns the node
         * @return the next node
         */
        @Override
        public Word next() throws NoSuchElementException {
            if (nextToReturn == null) {
            	throw new NoSuchElementException("The end of the list reached");
            }
            previousReturn = nextToReturn;
            Word tmp = nextToReturn.data; 
            nextToReturn = nextToReturn.next;
            
            return tmp;
        }
        
        /**
         * Removes an element from the sorted linked list if it exists.
         */
        public void remove() {
        	if (previousReturn == null) {
        		// list should not have null in it
        		return;
        	}
        	
        	
        	if (previousReturn.prev == null) { // remove head
        		head = previousReturn.next;
        		if (head != null) { head.prev = null;}
        		size--;
        		return;
        	} else if (nextToReturn == null) { // remove tail
        		tail = previousReturn.prev;
        		if (tail != null) { tail.next = null;}
        		size--;
        		return;
        	} else { // remove middle
        		(previousReturn.prev).next = previousReturn.next;
        		(previousReturn.next).prev = previousReturn.prev;
        		
        	}
        	
        	size--;
        	previousReturn = null;
        	return;
    	}

    }
    

}
