/**
 * A singly-linked list implementation of the SimpleList interface
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012, 
 * based on a more feature-ful version by Tom Cormen and Scot Drysdale
 * @author CBK, Spring 2016, cleaned up inner class
 * @author Tim Pierson, Spring 2019, fixed possible null pointer issue with advance method
 */
public class SinglyLinked<T> implements SimpleList<T> {
	// "implements" is a promise to implement all required methods
	// specified by interface SimpleList: size, add, remove, get, set
	// Type of data is generic T
	// do not care what kind of data the list holds, could be Strings, Integers, Blob, Objects
	// do not have to make others for others types

	private Element head;	// front of the linked list
	// creates head Element and size counter
	private int size;		// # elements in the list
	// everytime you remove, decrement
	// everytime you add, increment

	/**
	 * The linked elements in the list: each has a piece of data and a next pointer
	 *
	 * Define a private class called Element to implement data and next pointers
	 *
	 */
	private class Element {
		private T data; // holds data, any type
		private Element next; // creates pointer to next


		// constructor to tell what data to hold, and pointer to the next one
		private Element(T data, Element next) {
			this.data = data;
			this.next = next;
		}
	}

	public SinglyLinked() {
		head = null; // initially null
		size = 0; // initially 0;
	}

	public int size() {
		return size;
	}

	/**
	 * Helper function, advancing to the nth Element in the list and returning it
	 * (exception if not that many elements)
	 *
	 * advance() helper method
	 * will go down n item and returns whatever it finds
	 */
	private Element advance(int n) throws Exception {
		// start at the head
		Element e = head;
		if (e == null) {
			throw new Exception("list is null");
		}
		while (n > 0) {
			// Just follow the next pointers
			e = e.next;
			// traverse down the line
			if (e == null) throw new Exception("invalid index");
			// throws a message saying index does not exist
			n--; //decrement n
			// keep going e == next
			// working way down the chain
		}
		return e;
		// if do not reach end, return element at specified index
		// linked list, always start at head and work down
	}

	public void add(int idx, T item) throws Exception {

		// safety check, no items less than 0
		//ex, add index to -1 throws an exception
		if (idx < 0) {
			throw new Exception("invalid index");			
		}
		else if (idx == 0) {
			// Insert at head
			head = new Element(item, head); //new item gets next pointer set to head
		}
		else {
			// It's the next thing after element # idx-1
			Element e = advance(idx-1);
			// Splice it in
			e.next = new Element(item, e.next);	//create new element with next pointing at prior element's next 
												//and prior element's next updated to point to this item
		}
		size++; //increment size
	}

	public void remove(int idx) throws Exception {
		if (idx < 0) {
			throw new Exception("invalid index");			
		}
		else if (idx == 0) {
			// Just pop off the head
			// see if list is empty
			if (head == null) throw new Exception("invalid index");
			head = head.next; // head now points to next
		}
		else {
			// It's the next thing after element # idx-1
			Element e = advance(idx-1);
			// advance down to prior one
			if (e.next == null) throw new Exception("invalid index"); // make sure in range
			// Splice it out
			e.next = e.next.next;  //nice!
			// sets next to the next of the one removing
		}
		size--;
	}
	// get / set also use advance() to march down the list, this time to index idx
	public T get(int idx) throws Exception {
		if (idx < 0) {
			throw new Exception("invalid index");			
		}
		Element e = advance(idx);
		// advances down to the item
		return e.data; // returns the data
	}

	public void set(int idx, T item) throws Exception {
		if (idx < 0) {
			throw new Exception("invalid index");			
		}
		Element e = advance(idx);
		e.data = item; // sets data to the item
	}


	// returns a string representation of the object
	// if not used, it prints memory address
	// now toString can return the string rep
	public String toString() {
		String result = ""; // result is empty at first
		// DO NOT PRINT HERE
		// RETURNS A STRING
		// for loop, initializes var at head, stays in the loop, increments x=x.next
		// will hit null at the end
		for (Element x = head; x != null; x = x.next) 
			result += x.data + "->";  // adds data and an arrow
		result += "[/]"; // at the end adds the symbol for the end of the list

		return result;
	}

	public SinglyLinked<T> palindrome() {
		// make a new list
		SinglyLinked<T> res = new SinglyLinked<>();

		res.head = new Element(head.data, new Element(head.data, null));
		res.size = size*2;
		Element e = head.next;
		Element resE = res.head;
		while(e != null){
			resE.next = new Element(e.data, new Element(e.data, resE.next));
			e = e.next;
			resE = resE.next;
		}

		// return res
		return res;
	}

	public Integer findMinIndex(SimpleList<Integer> list) throws Exception {

		if (list.size() == 0) throw new Exception("Empty List");
		int idx = 0;

		int min = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) < min) idx = i;
		}
		return idx;
	}

	public void moveToFront(int index) throws Exception{
		if(index < 0 || size < index -1) throw new Exception("Invalid Index");
		Element prevIndex = advance(index-1);
		Element e = advance(index);
		prevIndex.next = prevIndex.next.next;
		e.next = head;
		head = e;


	}
}




