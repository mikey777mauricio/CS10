/**
 * A blob that sits still and pulsates (radius increases/decreases).
 */
public class Pulsar extends Blob{
	public Pulsar(double x, double y) {
		super(x, y);
		dr = 1 + Math.random(); // random growth
	}
	
	@Override
	public void step() {
		r += dr;
		// Make sure radius is within bounds, switching between positive growing & negative growing (shrinking)
		if (r < 1 || r > 10) {
			dr = -dr; // if gets too big, then make dr negative and start shrinking
			r += dr; // if gets too small, change its sign and make it bigger and bigger
			// pulsar bascially strinks and grows
		}
	}
}
