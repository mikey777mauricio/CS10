import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * Load an image and display it
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 * @Co-Author Mikey Mauricio, CS10, Fall 2021
 */
public class ImageProcessingGUI0 extends DrawingGUI {
	private ImageProcessor0 proc;		// handles the image processing
// instead of holding imag,holds imageProcessor
	// ImageProcessor responsible for manipulating image
	/**
	 * Creates the GUI for the image processor, with the window scaled to the to-process image's size
	 */

	private boolean brushDown = false;
	private char c; //instace variable for color change


	public ImageProcessingGUI0(ImageProcessor0 proc) {
		super("Image processing", proc.getImage().getWidth(), proc.getImage().getHeight());
		this.proc = proc;

	}

	/**
	 * DrawingGUI method, here showing the current image
	 */
	@Override
	public void draw(Graphics g) {
		proc.greenUp();
		g.drawImage(proc.getImage(), 0, 0, null);
		//same draw() method as SmileGUI.java but now image comes from ImageProcessor


	}

	/**
	 * DrawingGUI method, here dispatching on image processing operations
	 */
	@Override
	//can save image by pressing 's' on keyboard
	// wont show file until program stops running
	public void handleKeyPress(char op) {
		System.out.println("Handling key '"+op+"'");
		if (op=='s') { // save a snapshot
			saveImage(proc.getImage(), "pictures/snapshot.png", "png");
		}
		if (op=='b') { // user presses b, g, or r
			if (brushDown == false) { // if brush is not down yet
				brushDown = true; // set brush to down
				c = 'b';  // color user wants to dull

			} else {
				brushDown = false; // if brush is already down, pick brush up
			}
		}
		if(op =='g') {
			if (brushDown == false) {
				brushDown = true;
				c = 'g';
			} else {
				brushDown = false;
			}
		}

				if(op =='r'){
				if (brushDown==false) {
					brushDown = true;
					c = 'r';
				}
				else
				{
					brushDown = false;
				}
		}




		else {
			System.out.println("Unknown operation");
		}


		repaint(); // Re-draw, since image has changed
	}



	@Override
	public void handleMouseMotion(int x, int y) {
		if(brushDown == true) // if brush is down
		{
			proc.changeColor(x, y, 1, c); //call changeColor method with c
		}

		repaint();


	}


	// loading in picture of baker
// creats a new ImageProcessor0 and provides image
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Load the image to process
				BufferedImage baker = loadImage("pictures/baker.jpg");
				// Create a new processor, and a GUI to handle it
				new ImageProcessingGUI0(new ImageProcessor0(baker));
			}
		});
	}
}
