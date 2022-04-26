import java.util.ArrayList;
/**
 * Demonstrate ArrayList holding multiple Blobs
 * @author Chris Bailey-Kellogg
 * @author Tim Pierson, Dartmouth CS 10, Spring 2019 -- added print statements
 *
 */
public class BlobsDriver {

	public static void main(String[] args) {

		ArrayList<Blob> blobs /* name of list */ = new ArrayList<Blob>(); //holds multiple blob objects
		blobs.add(new Wanderer(10,10)); // adds new blob of type wanderer
		blobs.add(new Bouncer(20,30,800,600)); // adds new bouncer
		// each type is a blob so java lets slide
		blobs.get(0).step(); // => the wanderer, gets wanderer and steps it
		blobs.get(1).step(); // => the bouncer, gets bouncer and steps it
		System.out.println(blobs.size()); // => 2 items in the list
		System.out.println(blobs.get(0).getX()); // gets item at index 0, and can call methods on it

	}
}
