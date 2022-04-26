/**
 * A blob that moves randomly.
 */
public class Wanderer extends Blob{  //inherits instance variables the blob has
	public Wanderer(double x, double y) {
		super(x, y);  // constructor 1
	} // constructor call 'super', sending parameters and initalizing it
		
	public Wanderer(double x, double y, double r) {
		super(x, y, r);
	}

	// no need to rewrite the constructor code if we are not going to do anything different,
	// just reuse the existing constructor code by calling super

	// works on methods other than just constructors
	// could call setX() and it would call base class setX()
	// dynamic dispatch - research later

		
	@Override
	public void step() { // Wanderer overrides the base class step() method
		// Choose a new step between -1 and +1 in each of x and y
		// when step() is called on a wanderer object this method is used instead of base class
		// if step is not defined here, would use the base class's step () method (dynamic dispatch; hunts upward)
		// @override is not required but can help prevent errors

		dx = 2 * (Math.random()-0.5); // wanderer will choose a new step to move
		// math.random will return a value between 0 and 1 and then -0.5 will subtract a half
		// multiplier makes range -1 to 1
		dy = 2 * (Math.random()-0.5);
		x += dx;
		y += dy;
	}
}
