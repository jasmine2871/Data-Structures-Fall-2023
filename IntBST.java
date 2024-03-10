import java.util.Scanner;
import java.util.Arrays;
import java.util.Stack;

// GROUP: JASMINE FAN, JESSICA WANG, LUCAS CACERES, SAMANTHA LIN, ZAID ASIF, ANNA YE

public class IntBST {
    
    public static void main(String [] args) {

        //read in the size of the tree as first arg
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();

        //create an empty tree
        IntBST bst = new IntBST();

        //read in the values for the tree from standard input
        //(no error checking here, this program will crash if
        // a non-integer value is entered)
        for (int i = 0; i < size; i++ ) {
            bst.add( in.nextInt() );
        }

        //read in which function should be called
        int which = in.nextInt();
        switch(which) {
        case 1:
            System.out.println( bst.size() );
            break;
        case 2:
            System.out.println( bst.sumOfAll() );
            break;
        case 3:
            System.out.println( bst.sumOfLeaves() );
            break;
        case 4:
            //get the level value
            int level = in.nextInt();
            System.out.println( bst.sumOfLevel( level) );
            break;
        }

    }
    
    // THIS IS WHERE THE CLASS STARTS
    private Node root;   //reference to the root node of the tree
    // UwU

    /**
     * Constructs a new, empty tree, sorted according to the natural ordering of its elements.
     */
    public IntBST () {
        root = null;
    }

    /**
     * Adds the specified element to this tree if it is not already present.
     * If this tree already contains the element, the call leaves the
     * tree unchanged and returns false.
     * @param data element to be added to this tree
     * @return true if this tree did not already contain the specified element
     */
    public boolean add ( int data ) {

        if (root == null ) {// create the first node
            root = new Node (data);
            return true;
        }
        Node current = root;
        while (current != null ) {
            if (current.data > data) { //add in the left subtree
                if (current.left == null ) {
                    current.left = new Node (data);
                    return true;
                }
                else {
                    current = current.left;
                }
            }
            else if (current.data < data ) {//add in the right subtree
                if (current.right == null ) {
                    current.right = new Node (data);
                    return true;
                }
                else {
                    current = current.right;
                }
            }
            else { //duplicate
                return false;
            }
        }
        //we should never get to this line
        return false;
    }

    /**
     * Returns the number of elements in this tree.
     * @return the number of elements in this tree
     */
    public int size() {
        //TO DO: implement this method
        int counter = 0;
        if (root != null){
            Stack<Node> bstStack = new Stack<Node>();
            bstStack.push(this.root);
            while (bstStack.size() != 0){
                Node current = bstStack.pop();
                counter += 1;
                if (current.left != null){
                    bstStack.push(current.left);
                }
                if(current.right != null){
                    bstStack.push(current.right);
                }
            }
        }
        
        return counter;
        
    }

    /**
     * Returns the sum of values stored in this tree.
     * @return the sum of values stored in this tree
     */
    public int sumOfAll() {
        //TO DO: implement this method
        int sum = 0;
        
        if (root == null) {
            return 0;
        }

        // this just iterating through the tree and then summing as you go
        if (root != null){
            Stack<Node> bstStack = new Stack<Node>();
            bstStack.push(this.root);
            while (bstStack.size() != 0){
                Node current = bstStack.pop();
                sum += current.data;
                if (current.left != null){
                    bstStack.push(current.left);
                }
                if(current.right != null){
                    bstStack.push(current.right);
                }
            }
        }
        
        return sum;
    }

    /**
     * Returns the sum of values stored in the
     * leaf-nodes in this tree.
     * @return the sum of values stored in the
     * leaf-nodes in this tree
     */
    public int sumOfLeaves() {
        //TO DO: implement this method
        int sumOfLeaves = 0;
        int size = 0;
        
        Node current = root;
        /*
        size++;
        sumOfLeaves += current.data;
        if(current.right != null){
            sumOfLeaves();
        }
        if(current.left != null){
            sumOfLeaves();
        }
        if(size == size()){
            return sumOfLeaves;
        } */
        // Debugging return statement
        return onlyLeaves(root);
    }
    
    private int onlyLeaves(Node current) {
        int value = 0;
        if (current.right != null) {
            value += onlyLeaves(current.right);
            // if (current.left != null) {
            //     value += onlyLeaves(current.left);
            // }
        } else if (current.left != null) {
            value += onlyLeaves(current.left);
        } else {
            value += current.data;
        }
        return value;

    }
    

    /**
     * Returns the sum of values stored in the
     * nodes at the specified level in this tree.
     * If a given level does not exist, the
     * function should return 0.
     * @return the sum of values stored in the
     * nodes at the specified level in this tree
     */
    public int sumOfLevel(int level) {
        int sumOfLevel = 0;
        Node current = root;
        int currLevel = 0;

        if (root != null){
            if (level == 0)
                return root.data;
            Stack<Node> bstStack = new Stack<Node>();
            while (bstStack.size() != 0){
                current = bstStack.pop();
                if (current.left != null){
                    bstStack.push(current.left);
                }
                if(current.right != null){
                    bstStack.push(current.right);
                }
            }
        }
        
        
        return 0;
    }

    public String toStringTree( ) {
        StringBuffer sb = new StringBuffer();
        toStringTree(sb, root, 0);
        return sb.toString();
    }

    //uses preorder traversal to display the tree
    //WARNING: will not work if the data.toString returns more than one line
    private void toStringTree( StringBuffer sb, Node node, int level ) {
        //display the node
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n");
            return;
        }
        else {
            sb.append( node.data + "\n");
        }

        //display the left subtree
        toStringTree(sb, node.left, level+1);
        //display the right subtree
        toStringTree(sb, node.right, level+1);
    }

    /*
     * Node class for this BST
     */
    private class Node implements Comparable < Node > {

        int data;
        Node  left;
        Node  right;

        public Node ( int data ) {
            this.data = data;
        }

        public int compareTo ( Node other ) {
            return this.data - other.data;
        }
    }

}
