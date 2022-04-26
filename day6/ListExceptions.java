/**
 * Illustrations of handling exceptions, with lists
 */
public class ListExceptions {
	public static void main(String[] args) { // note: no "throws exception", as every method that could is wrapped
		
		SimpleList<String> list = new SinglyLinked<String>();
		System.out.println(list);
		try {
			list.add(0, "a");
			list.add(1, "c");
			list.add(1, "b");
			list.set(2, "e");
			list.add(0, "z");
			list.remove(2);
			list.remove(0);
			list.remove(1);
			System.out.println(list);	
		}

		catch (Exception e) {
			System.err.println("why did I catch it?"); // shouldn't print -- we know this code is fine
		}
		
		try {
			list.add(-1, "?");
		}
		// Try adding at index -1 is still an error, the catch block will execute
		// We can see what the exception was by printing e(it is just an object)
		// "invalid index" was the message we included when we threw an
		// error in the add method of SinglyLinked.java

		catch (Exception e) {
			System.out.println("caught it!"); // should print -- we know this is bogus
		}

		try {
			list.add(-1, "?");
		}
		catch (Exception e) {
			System.out.println("caught it again!"); // should print -- we know this is bogus
		}
		// finally always executes regardless of whether exception in try block
		// catch only exectues if exception occurs in try block

		finally {
			System.out.println("finally 1"); // executed whether or not caught an error
		}
	
		try {
			list.add(0, "?");
		}
		catch (Exception e) {
			System.out.println("why did I catch it again!"); // shouldn't print -- we know this code is fine
		}
		finally {
			System.out.println("finally 2"); // executed whether or not caught an error
		}
}
}
