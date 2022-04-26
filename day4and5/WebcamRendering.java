import java.awt.Color;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

/**
 * Fun with the webcam, built on JavaCV
 * Uses one of our de-novo rendering methods to display webcam image
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for simplified Webcam class
 * @author CBK, Spring 2016, direct rendering, size control
 */
public class WebcamRendering extends Webcam {  // inherits from webcame
    private int pixelSize = 10;        // size of the objects representing the image
	// instance variable pixelSize
    private char style = 'i';
    
    /**
	 * Renders the image as a set of rectangles tiling the window.
	 */
	public void mosaic(Graphics g) {
		// Usual loops, but step by "pixel" size and draw a rectangle of the appropriate color.
		// Also note <=, to get that last rectangle.
		// Nested loop over every pixel
		for (int y = 0; y <= image.getHeight() - pixelSize; y += pixelSize) // steps by pixel size
			 {
			for (int x = 0; x <= image.getWidth() - pixelSize; x += pixelSize) {
				g.setColor(new Color(image.getRGB(x,y)));
				// get color
				g.fillRect(x, y, pixelSize, pixelSize);
				// fill a rectangle using color
				g.setColor(Color.black);
				// fills a black border around
				g.drawRect(x, y, pixelSize, pixelSize);
			}
		}
	}

	/**
	 * Renders the image as a set of ellipses at random positions.
	 */
	public void pointillism(Graphics g) {
		// Draw some random number of points determined by the image and "pixel" sizes.
		int numPoints = image.getWidth() * image.getHeight() * 5 / pixelSize;
		for (int p=0; p<numPoints; p++) {
			// Pick a random position and size
			int x = (int) (Math.random() * image.getWidth());
			int y = (int) (Math.random() * image.getHeight());
			int s = (int) (Math.random() * pixelSize) + 1;

			// Draw an ellipse there, colored by the pixel's color
			g.setColor(new Color(image.getRGB(x,y)));
			g.fillOval(x, y, s, s);										
		}
	}

	/*
	 * DrawingGUI method, here just remembering the style for rendering
	 */
	@Override
	public void handleKeyPress(char key) {
		if (key=='+') {
			pixelSize++; // increases pixel size
		}
		else if (key=='-') {
			if (pixelSize>0) pixelSize--;
		}
		else {
			style = key;			
		}
	}
	
	@Override
	public void draw(Graphics g) { //if image was altered do not do anything
		if (image!=null) {
			if (style=='m') mosaic(g); // if last key was m, mosiac
			else if (style=='p') pointillism(g); // p, pointillism
			else super.draw(g); // else, jsut draw image
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WebcamRendering();
			}
		});
	}
}
