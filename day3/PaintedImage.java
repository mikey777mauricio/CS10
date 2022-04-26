import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Custom rendering of an image, by animated blobs.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 */
public class PaintedImage extends DrawingGUI { // extends DrawingGUi
	private static final int numBlobs = 20000;			// setup: how many blobs
	private static final int numToMove = 5000;			// setup: how many blobs to animate each frame

	private BufferedImage original;						// the picture to paint
	private BufferedImage result;						// the picture being painted
	private ArrayList<Blob> blobs;						// the blobs representing the picture
	
	public PaintedImage(BufferedImage original) {
		super("Animated Picture", original.getWidth(), original.getHeight());
		//set up GUI
		
		this.original = original;
		result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// save original image
		//Create new image called result (initially blank)
		//result will image will fade in original image
		// Create a bunch of random blobs.
		blobs = new ArrayList<Blob>();
		for (int i=0; i<numBlobs; i++) {
			int x = (int)(original.getWidth()*Math.random());
			int y = (int)(original.getHeight()*Math.random());
			blobs.add(new Wanderer(x, y, 1)); // creates 20,000 wanderer blobs at random x,y  locations
		}

		// Timer drives the animation.
		startTimer(); // starts timer
	}

	/**
	 * DrawingGUI method, here just drawing all the blobs
	 */
	@Override
	public void draw(Graphics g) { // show result image (was initially blank but 5,000 pixels colored in every timer tick)
		// blobs show up as black dots
		g.drawImage(result, 0, 0, null);
		for (Blob blob : blobs) {
			blob.draw(g);
		}		
	}

	/**
	 * DrawingGUI method, here moving some of the blobs
	 */
	@Override
	public void handleTimer() {
		/*
		On timer tick choose 5,000 random Blobs
		Get color from original image at Blob's x,y location
		Copy color to result image at x,y location
		step() blobs, then repaint() when done (calls draw())
		result image gets colored in overtime
		 */
		for (int b = 0; b < numToMove; b++) {
			// Pick a random blob, leave a trail where it is, and ask it to move.
			Blob blob = blobs.get((int)(Math.random()*blobs.size()));
			int x = (int)blob.getX(), y = (int)blob.getY();
			// Careful to stay within the image
			if (x>=0 && x<width && y>=0 && y<height) {
				result.setRGB(x, y, original.getRGB(x, y));				
			}
			blob.step();
		}
		// Now update the drawing
		repaint();
	}

	public static void main(String[] args) {
		/*
		main loads image passes to constructor
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PaintedImage(loadImage("pictures/baker.jpg"));
			}
		});
	}
}
