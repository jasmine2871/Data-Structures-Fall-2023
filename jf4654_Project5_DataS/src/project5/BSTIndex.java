package project5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class...
 * 
 * @author Jasmine Fan (jf4654)
 */

public class BSTIndex implements Index{
	
	private BSTNode root;
	private int size;
	private boolean added;
	private boolean removed;
	
	public BSTIndex() {
		root = null;
		size = 0;
	} 

	@Override
	public Iterator<Word> iterator() {
		return new BSTIterator();
	}
	
	@Override
	public void add(String item) throws IllegalArgumentException{
		if (item == null) {
    		throw new IllegalArgumentException("Item cannot be null.");
    	}
		
		Word itemWord = new Word(item);
		added = true;
		
		root = addRec(itemWord, root);
		
		if (added) {
			size++;
		}
	
		return;
	}

	
	private BSTNode addRec(Word data, BSTNode node) {
		if (node == null) {
			return new BSTNode(data);
		}
		
		// natural ordering of elements
		int compareResult = data.compareTo(node.data);
		
		if(compareResult > 0) {
			node.left = addRec(data, node.left);
		} else if (compareResult < 0) {
			node.right = addRec(data, node.right);
		} else {
			// duplicate found no add
			// increment count of current word
			node.data.incrementCount();
			added = false;
		}
		
		return node;
		
	}

	@Override
	public void remove(String item) {
		if (item == null) {
			return;
		}
		removed = false;
		
		root = removeRec(new Word(item), root);
		
		if (removed) {
			size--; 
		}
		
		return;
	} 
	
	private BSTNode removeRec(Word target, BSTNode node) {
		if (node == null) {
			return node;
		}
		
		int compareResult = target.compareTo(node.data);
		
		if (compareResult > 0) {
			node.left = removeRec(target, node.left);
		} else if (compareResult < 0) {
			node.right = removeRec(target, node.right);
		} else {
			// found node to remove, remove it
			node = removeNode(node);
			removed = true;
		}
		
		
		return node;
		
	}
	
	private BSTNode removeNode(BSTNode node) {
		Word data;
		if (node.left == null) {
			return node.right;
		} else if (node.right == null) {
			return node.left;
		} else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = removeRec(data, node.left);	
		}
		
		
		return node;
		
	}
	
	private Word getPredecessor(BSTNode subtree) {
		if (subtree == null) {
			throw new NullPointerException("You have done the un-doable.");
		}
		
		BSTNode tmp = subtree;
		while (tmp.right != null) {
			tmp = tmp.right;
		}
		
		return tmp.data;
	}

	@Override
	public int get(String item) {
		
		ArrayList<String> valueStr = new BSTIterator().valueStr;
		
		if (!valueStr.contains(item)) {
			return -1;
		}
		
		ArrayList<Word> values = new BSTIterator().values;
		Word wordToGet = new Word(item);
		
		for (Word word: values) {
			if (wordToGet.getWord().equals(word.getWord())) {
				return word.getCount();
			}
		}
		
		return 0;
	}

	@Override
	public int size() {
		return this.size;
	}
	
	public boolean equals(Object obj) {
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
	
	public String toString() {
		Iterator<Word> itr = this.iterator();
		String string = "[";
		
		while(itr.hasNext()) {
			string += itr.next().toString() + ", ";
		}
		
		if (string != "[") {
			string = string.substring(0, string.length() - 2);
		}
		
		return string + "]"; 
	}
	
//	private BSTNode find(Word word) {
//		return findRec(word, root);
//		
//	}
//	
//	private BSTNode findRec(Word word, BSTNode node) {
//		if (node == null) {
//			return node;
//		}
//		
//		int compareResult = word.compareTo(node.data);
//		
//		if (compareResult < 0) {
//			node.left = findRec(word, node.left);
//		} else if (compareResult > 0) {
//			node.right = findRec(word, node.right);
//		} else {
//			return node;
//		}
//		
//		 
//		return node;
//	}
	
	private class BSTNode implements Comparable<BSTNode>  {
		Word data;
		BSTNode left;
		BSTNode right;
		
		
		public BSTNode(Word data) {
			if (data == null) throw new NullPointerException ("does not allow null");
			this.data = data;
		}
		
		
		@SuppressWarnings("unused")
		public BSTNode(Word data, BSTNode left, BSTNode right) {
			this(data);
			this.left = left;
			this.right = right;
		}

		@Override
		public int compareTo(BSTNode o) {
			return this.data.compareTo(o.data);
		}
		
	}
	
	private class BSTIterator implements Iterator<Word>{
		ArrayList<Word> values;
		ArrayList<String> valueStr;
		int current;
		
		public BSTIterator() {
			values = new ArrayList<Word>(size);
			valueStr = new ArrayList<String>(size);
			inorder(root);
			current = 0;
		}
		
		private void inorder(BSTNode node) {
			if (node == null) {
				return;
			}
			// add from left subtree
			inorder(node.right);
			// add this node
			values.add(node.data);
			valueStr.add(node.data.getWord());
			// add from right subtree
			inorder(node.left);
		}

		@Override
		public boolean hasNext() {
			if (current >= values.size() ) {
				return false;
			}
            return true; 
			
		}

		@Override
		public Word next() throws NoSuchElementException {
			 if (current >= values.size()) {
	                throw new NoSuchElementException("no more values here"); 
	            }
	            Word val = values.get(current); 
	            current++; 
	            return val; 
		}
		
		public void remove() {
			// find node to remove, remove it
//			Word wordToRemove = values.get(current-1);
//			BSTNode wordNode = find(wordToRemove);
//			
//			root = removeNode(wordNode);
			
			if (size == 0) {
				return;
			}
			
			Word wordToRemove = values.get(current - 1);
			
			BSTIndex.this.remove(wordToRemove.getWord());
		
			
		}
		
		
	}

}
