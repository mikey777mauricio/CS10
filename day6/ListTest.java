/**
 * Simple tests of list implementations
 */
public class ListTest {
	public static void main(String[] args) throws Exception {
		// Declare singlyLinked List to hold Strings,
		// so T = String in the implementation
		// Implementation is SinglyLinked which implemented SimpleList interface
		SimpleList<Integer> list = new SinglyLinked<Integer>();
		//SimpleList<String> list = new GrowingArray<String>();
		System.out.println(list);
		list.add(0, 9);

		list.add(1, 100);

		list.add(2, 99);
		list.add(3, 3);

	}
}
