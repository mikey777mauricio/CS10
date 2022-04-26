/**
 * A blob that moves randomly and pulsates (radius increases/decreases).
 */
public class WanderingPulsar extends Wanderer { // wanderer around like a wanderer while it grows and shrinkes
	public WanderingPulsar(double x, double y) {
		super(x, y);
		dr = 1 + Math.random();
	}
	
	@Override
	public void step() {
		// Do the wanderer step.
		super.step();
		// Now increment radius and make sure it is within bounds.
		r += dr;
		if (r < 1 || r > 10) {
			dr = -dr;
			r += dr;
		}
	}
}
