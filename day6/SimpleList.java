/**
 * A basic interface for a generic list ADT
 */
public interface SimpleList<T> { // Interface key word tells java this is an interface
	// Methods defined to include parameters and return types (called a signature)
	// If you are going to implement SimpleList, then you must implement these methods
	// How you implement is your business
	// You can use any type of list

	/**
	 * Returns # elements in the list (they are indexed 0..size-1)
	 */
	public int size();


	/**
	 * Adds the item at the index, which must be between 0 and size
	 * (since the current elements are 0..size-1, idx = size grows the list)
	 */
	public void add(int idx, T item) throws Exception;

	/**
	 * Removes the item at the index, which must be between 0 and size-1
	 */
	public void remove(int idx) throws Exception;

	/**
	 * Returns the item at the index, which must be between 0 and size-1
	 */
	public T get(int idx) throws Exception;

	/**
	 * Replaces the item at the index, which must be between 0 and size-1
	 */
	public void set(int idx, T item) throws Exception;





}


