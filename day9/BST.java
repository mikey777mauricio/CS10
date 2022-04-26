import java.util.*;

/**
 * Generic binary search tree
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Fall 2016, min
 *
 *
 * Notes: need a way to know if whether one key is larger than another.
 * BST< k extends Comparable<k>,V>
 *     Comparable requires class used as Key to implement compareTo() method
 *     Cannot use class as a key without it
 *     CompareTo() already implemented for autoboxed classes such as string/ints
 *     Must implement our own classes if we use them as Keys. ex. --> blobs
 *     Blob class would have to tell us (maybe the highest one, or the leftmost one)
 *
 */

public class BST<K extends Comparable<K>,V> {
	private K key; // each node has a key (generics)
	private V value; // each node has a value (generics)
	private BST<K,V> left, right; // user wrapper for primitive types

	/**
	 * Constructs leaf node -- left and right are null
	 */
	public BST(K key, V value) {
		this.key = key; this.value = value;
	}

	/**
	 * Constructs inner node
	 */
	public BST(K key, V value, BST<K,V> left, BST<K,V> right) {
		this.key = key; this.value = value;
		this.left = left; this.right = right;
	}

	/**
	 * Is it a leaf node?
	 */
	public boolean isLeaf() {
		return left == null && right == null;
	}

	/**
	 * Does it have a left child?
	 */
	public boolean hasLeft() {
		return left != null;
	}

	/**
	 * Does it have a right child?
	 */
	public boolean hasRight() {
		return right != null;
	}

	/**
	 * Returns the value associated with the search key, or throws an exception if not in BST
	 *
	 * Look for Key Search in BST, return value V if found (exception if not found)
	 * RECURSIVE
	 */

	public V find(K search) throws InvalidKeyException {
		System.out.println(key); // to illustrate
		int compare = search.compareTo(key); // use compare to evaluate search Key with this nodes key
		// return this nodes value if found
		if (compare == 0) return value;
		if (compare < 0 && hasLeft()) return left.find(search);
		if (compare > 0 && hasRight()) return right.find(search);
		throw new InvalidKeyException(search.toString());
	}

	/**
	 * Smallest key in the tree, recursive version
	 */
	public K min() {
		if (left != null) return left.min();
		return key;
	}

	/**
	 * Smallest key in the tree, iterative version
	 */
	public K minIter() {
		BST<K,V> curr = this;
		while (curr.left != null) curr = curr.left;
		return curr.key;
	}

	/**
	 * Inserts the key & value into the tree (replacing old key if equal)
	 *
	 *
	 * Search tree, if key exists, override value
	 */
	public void insert(K key, V value) {
		int compare = key.compareTo(this.key); // compares key with node we on
		if (compare == 0) {
			// replace
			this.value = value; // if equal it replaces
		}
		else if (compare < 0) {
			// insert on left (new leaf if no left)
			// if has left child, go left
			if (hasLeft()) left.insert(key, value);
			// if hit end of the tree, set new node
			else left = new BST<K,V>(key, value);
		}
		else if (compare > 0) {
			// insert on right (new leaf if no right)
			if (hasRight()) right.insert(key, value);
			else right = new BST<K,V>(key, value);
		}
	}

	/**
	 * Deletes the key and returns the modified tree, which might not be the same object as the original tree
	 * Thus must afterwards just use the returned one
	 *
	 * Delete will possibly return a new node
	 */
	public BST<K,V> delete(K search) throws InvalidKeyException {
		int compare = search.compareTo(key);
		if (compare == 0) { //searches for node
			// Easy cases: 0 or 1 child -- return other
			if (!hasLeft()) return right; // no left child, return right
			if (!hasRight()) return left; // no right child, return left
			// If both children are there, delete and substitute the successor (smallest on right)
			// Find it
			BST<K,V> successor = right;
			while (successor.hasLeft()) successor = successor.left;
			// Delete it
			right = right.delete(successor.key);
			// And take its key & value
			this.key = successor.key;
			this.value = successor.value;
			return this;
		}
		else if (compare < 0 && hasLeft()) {
			left = left.delete(search);
			return this; 
		}
		else if (compare > 0 && hasRight()) {
			right = right.delete(search);
			return this;
		}
		throw new InvalidKeyException(search.toString());
	}

	/**
	 * Parenthesized representation:
	 * <tree> = "(" <tree> ["," <tree>] ")" <key> ":" <value>
	 *        | <key> ":" <value>
	 */
	public String toString() {
		if (isLeaf()) return key+":"+value;
		String s = "(";
		if (hasLeft()) s += left;
		else s += "_";
		s += ",";
		if (hasRight()) s += right;
		else s += "_";
		return s + ")" + key+":"+value;
	}

	/**
	 * Very simplistic BST parser in a parenthesized representation
	 * <tree> = "(" <tree> ["," <tree>] ")" <key> ":" <value>
	 *        | <key> ":" <value>
	 * Assumes that the tree actually has the BST property!!!
	 * No effort at all to handle malformed trees
	 */
	public static BST<String,String> parseBST(String s) {
		return parseBST(new StringTokenizer(s, "(,)", true));
	}

	/**
	 * Does the real work of parsing, now given a tokenizer for the string
	 */
	public static BST<String,String> parseBST(StringTokenizer st) {
		String token = st.nextToken();
		if (token.equals("(")) {
			// Inner node
			BST<String,String> left = parseBST(st);
			BST<String,String> right = null;
			String comma = st.nextToken();
			if (comma.equals(",")) {
				right = parseBST(st);	
				String close = st.nextToken();
			}
			String label = st.nextToken();
			String[] pieces = label.split(":");
			return new BST<String,String>(pieces[0], pieces[1], left, right);
		}
		else {
			// Leaf
			String[] pieces = token.split(":");
			return new BST<String,String>(pieces[0], pieces[1]);
		}
	}

	/**
	 * midterm 2 practice question 7c
	 * Consider the following method that, given a non-null reference x to a node of a Binary Search
	 * Tree, returns the largest key in the subtree rooted at x.
	 * public int getLargestKey(BSTnode x)
	 * Implement the above method using recursion. You are not allowed to write or use any method
	 * other than the above method (so, no helper methods are allowed).
	 */

	public int getLargestKey(BST<Integer, String> x){
		if (x.right == null) return x.key;
		return getLargestKey(x.right);
	}

	/**
	 * Write a binary tree method that prints to System.out one line for each node in the tree, with each
	 * line of the form
	 * parent [, child1 [, child2]]
	 * where the brackets indicate things that may or may not be there (it may have 0, 1, or 2 children). For
	 * example:
	 */

	public void printBrackets(BST<K,V> x){
		String treeString = x.key.toString();
		if(x.isLeaf()) System.out.println(treeString);
		if(x.hasLeft()) treeString += "," + x.left.key.toString();
		if(x.hasRight()) treeString += " ," + x.right.key.toString();
		System.out.println(treeString);
		if(x.hasLeft()) printBrackets(x.left);
		if(x.hasRight()) printBrackets(x.right);

	}



	/**
	 * Some tree testing
	 */
	public static void main(String[] args) throws Exception {

		BST<Integer,String> test = new BST<>(10, "a", new BST<>(1222, "b"), new BST<>(10008, "c"));
		test.insert(14, "r");
		test.insert(34, "r");
		test.insert(4, "r");
		test.insert(1, "r");
		test.insert(124, "r");

		System.out.println(test.left.key);
		System.out.println(test);
		System.out.println(test.getLargestKey(test));
		test.printBrackets(test);


	}
}
