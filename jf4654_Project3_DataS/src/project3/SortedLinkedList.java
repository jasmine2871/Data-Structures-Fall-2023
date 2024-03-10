package project3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow null elements.
 *	
 * Template from Prof. Joanna Klukowska, implemented by Jasmine Fan
 * @author Joanna Klukowska
 * @author Jasmine Fan (jf4654)
 *
 * @param <E> the type of elements held in this list
 */
public class SortedLinkedList<E extends Comparable<E>> implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return true if the element was added successfully,
     * 		   false otherwise (if element == null)
     */
    public boolean add(E element) {
    	if (element == null) {
    		return false;
    	}
    	
    	Node newElement = new Node(element);
    	
    	// Handle if list is empty
    	if (size == 0) {
    		head = newElement;
    		tail = newElement;
    		size++;
    		return true;
    	}
    	
    	// Handle if list has 1 element
    	if (size == 1) {
    		// If head is greater than element, element goes before head and becomes head
    		if (head.data.compareTo(element) > 0) { // head is greater than element
    			newElement.next = head;
    			head.prev = newElement;
    			head = newElement; 
    		} else {
    			// Add element behind head and set element to tail
    			head.next = newElement;
    			newElement.prev = head;
    			tail = newElement;
    		}
    		size++;
			return true;

    	}
    	
    	// Handle when head is greater than element, element goes before head
    	// Set head to element if above is true
    	if (head.data.compareTo(element) > 0) { // returns pos if head.data is greater than argument
    		newElement.next = head;
    		head.prev = newElement;
    		head = newElement;
    		size++;
    		return true;
    	}
    	
    	// Handle when tail is smaller than element, element goes after tail
    	// Set tail to element if above is true
    	if (tail.data.compareTo(element) < 0) { // returns neg if tail.data is less than argument
    		newElement.prev = tail;
    		tail.next = newElement;
    		tail = newElement;
    		size++;
    		return true;
    		
    	}
    	
    	// Handle adding in the middle of the list
    	Node current = head;
    	
    	while ((current.next != null) && (current.data.compareTo(element) < 0)) {
    		current = current.next;
    	}
    	
    	newElement.next = current;
    	newElement.prev = current.prev;
    	current.prev.next = newElement;
    	current.prev = newElement;
    	size++;
    	
        return true;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
    	head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns true if the list contains the specified element,
     * false otherwise.
     *
     * @param o the element to search for
     * @return true if the element is in the list,
     * 		   false otherwise
     */
    public boolean contains(Object o) {
        Node current = head;
        
        while (current != null) {
        	if (current.data.equals(o)) {
        		return true;
        	}
        	current = current.next;
        }
        
        return false;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throw IndexOutOfBoundsException  if the index is out of
     * 		  range (index < 0 || index >= size())
     */
    public E get(int index) throws IndexOutOfBoundsException {
    	
    	if (index < 0 || index >= size()) {
    		throw new IndexOutOfBoundsException("Index is out of bounds, cannot retrieve at specified index");
    	}
    	
    	Iterator<E> itr = this.iterator();
    	int counter = 0;
    	E nodeGet = null;
    	
    	// Iterate to index + 1 to get correct element/ node at index
    	while(itr.hasNext() && (counter < index + 1)) {
    		nodeGet = itr.next();
    		counter++;
    	}

    	return nodeGet;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     *         or -1 if the element is not in the list
     */
    public int indexOf(Object o) {
        
    	// Iterate over list
    	Iterator<E> itr = this.iterator();
    	int index = 0;
    	E current = null;
    	while (itr.hasNext()) {
    		current = itr.next();
    		// check if o is equal to current
    		// if not, continue
    		// if yes, return index
    		if (current.equals(o)) {
    			return index;
    		}
    		index++;
    	}
    	
    	// if not find object in list, return -1
        return -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * starting at the specified index, i.e., in the range of indexes
     * index <= i < size(), or -1 if the element is not in the list
     * in the range of indexes index <= i < size().
     *
     * @param o the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the specified index,
     *         or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index) {
        
    	Iterator<E> itr = this.iterator();
    	int currentIndex = 0;
    	E current = null;
    	
    	while (itr.hasNext()) {
    		current = itr.next();
    		// start checking if current equals o when currentIndex is greater than or equal to index
    		if ((currentIndex >= index) && (current.equals(o))) {
    			return currentIndex; 
    		}
    		currentIndex++;
    	}
    	
        return -1;
    }

    /**
     * Removes the first occurence of the specified element from the list.
     * @param o the element to remove
     * @return true if the element was removed successfully,
     * 		   false otherwise
     */
    public boolean remove(Object o) {
        // Iterate over list, find first occurence, remove and return true, else false
    	if ((size() == 0) || (o == null)) {
    		// Nothing to remove if size is 0
    		return false;
    	}
    	
    	
    	if ((size() == 1) && (head.data.equals(o))) {
    		// Handle if size is 1 and the element in is equals o
			head = null;
			tail = null;
			size--;
			return true;
    	} else if (head.data.equals(o)) { 
    		// Handle if head equals o, set head to the next node
    		head = head.next;
    		size--;
    		return true;
    	} else if (!(head.data.equals(o)) && !(tail.data.equals(o))) { 
    		// Handle removing an element from the middle of the list
    		Node current = head;
	    	Node beforeCurrent = null;
	    	Node afterCurrent = null;
	    	
	    	while ((current.next.next != null) && (!current.data.equals(o))) {
	    		current = current.next;
	    	}
	    	
			beforeCurrent = current.prev;
			afterCurrent = current.next;
			
			beforeCurrent.next = afterCurrent;
			afterCurrent.prev = beforeCurrent;
			
			size--;
    	} else if (tail.data.equals(o)) { // Handle removing from the tail
    		tail = tail.prev;
    		tail.next = null;
    		size--;
    		return true;
    	}
    	
		return false;
    }

    /**
     * Returns the size of the list.
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     * @param o the object to compare with
     * @return true if the specified object is equal to this list,
     * 		   false otherwise
     */
    public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
    	if (!(o instanceof SortedLinkedList<?>)) {
    		return false;
    	}
		
    	SortedLinkedList<?> objectList = (SortedLinkedList<?>) o;
    	
    	Iterator<E> thisItr = this.iterator();
    	Iterator<?> objItr = objectList.iterator();
    	E thisElement = null;
    	Object objElement = null;
    	
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
     *  Returns a string representation of the list.
     *  The string representation consists of a list of the lists's elements in
     *  ascending order, enclosed in square brackets ("[]").
     *  Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */
    public String toString() {
    	Node current = head;
    	String output = "";
    	
    	while (current != null) {
    		output = output + String.format("%s", current.data + ", ");
    		current = current.next;
    	}
        
    	if (output.equals("")) {
    		return "[]";
    	} 
    	
        return String.format("[%s", output.substring(0, output.length() - 2) + "]");
    }

    /* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> {
        E data;
        Node next;
        Node prev;
        Node(E data) {
            if (data == null) throw new NullPointerException ("does not allow null");
            this.data = data;
        }
        @SuppressWarnings("unused")
		Node (E data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        public int compareTo( Node n ) {
            return this.data.compareTo(n.data);
        }
    }

    /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<E> {

        Node nextToReturn = head;
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null )
                throw new NoSuchElementException("the end of the list reached");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

    }
}
