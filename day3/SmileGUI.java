import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Simple illustration of GUI to display an image
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author Tim Pierson, Dartmouth CS10, Fall 2019, added explicit repaint to constructor
 */
public class SmileGUI extends DrawingGUI { // extends DrawingGUI
	private BufferedImage img; // new instance variable called BufferedImage
	// graphics file that we read in and store in memory
	
	public SmileGUI(BufferedImage img) {
		super("Smile!", img.getWidth(), img.getHeight());
		this.img = img; //store image in instance variable
		repaint();
	}

	/**
	 * DrawingGUI method, here showing the image
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, null);
	} // uses instance variable
	// passed image wanted to draw, coordinates, null

	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			/* sets up graphics calls your constructor and sets things up
			last line calls constructor
			 */

			public void run() {
				// Load image into memory from disk
				BufferedImage img = loadImage("pictures/smiley.png");
				//load image from disk with loadImage() method
				// filename is going to look over project name
				// make a directory called picture, and save files from the course webpage


				// Create a GUI to display it
				new SmileGUI(img); // call constructor, passing newly loaded image
			}
		});
	}
}
