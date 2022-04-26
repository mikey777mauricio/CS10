import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * A blob that moves randomly and draws itself as an image
 */
public class WanderingImage extends Wanderer { // extends wanderer
	private BufferedImage img; // instance variable
	
	public WanderingImage(double x, double y, BufferedImage img) {
		// constructor with parameters of coordinates and what image to draw
		super(x, y, Math.max(img.getHeight()/2, img.getWidth()/2));
		// gets max height and width /2 to center image
		this.img = img; // sets instance variable
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)(x-r), (int)(y-r), null);
	}
	// centers at x, y
}
