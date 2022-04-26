/**
 * Animated blob, defined by a position and size, 
 * and the ability to step (move/grow), draw itself, and see if a point is inside.
 * 
 * @author Tim Pierson, Dartmouth CS 10, Spring 2019, based on Blob by CBK
 */

public class BlobWithCompareTo extends Blob implements Comparable<BlobWithCompareTo>{
	public BlobWithCompareTo() {
		super();
	}
		
	/**
	 * Compare this blob with another blob
	 * @param compareBlob blob to compare to this blob
	 * @return   0 if same, 
	 * 			 1 if this blob is higher up than compareBlob, 
	 * 			-1 otherwise
	 *
	 * 	In Class definition add "implements Comparable" so Java knows class follows
	 * 	interface (not shown)
	 *
	 * 	Compare this Blob with another Blob using whatever metric you decide
	 * 	makes one bigger.
	 * 	Return a positive integer if the blob is higher
	 * 	Only needs to be -1,1,0
	 */
	public int compareTo(BlobWithCompareTo compareBlob) {
		if (this.y < compareBlob.getY()) 
			return 1; //this Blob is higher up
		else if (this.y > compareBlob.getY()) 
			return -1; //this Blob is lower
		else return 0; //at same height
	}
}
