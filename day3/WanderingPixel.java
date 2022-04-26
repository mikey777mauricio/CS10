import java.awt.*;

/**
 * A blob that moves randomly and has a color
 * @Co-author Mikey Mauricio, CS10, Fall 2020
 */
public class WanderingPixel extends Wanderer {
	private Color color; // instance variable of type color
	
	public WanderingPixel(double x, double y, double r, Color c) {
		// constructor passes coordinates, radius, and which color
		super(x, y, r); // calls Wanderer constructor
		this.color = c; // sets instance variable
		// blob can store a color


	}


	public Color getColor()
	{
		return color;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color); // set color in draw()
		g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r)); // will draw oval in this color
	}
}
