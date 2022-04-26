import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Fun with the webcam, built on JavaCV
 * Computes image difference
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for Webcam class
 */


// subtract the previous frame from the current frame

public class WebcamDiff extends Webcam {
	private BufferedImage prev;		// a copy of the image from the previous frame
	
	/**
	 * Webcam method, here doing image differences.
	 *
	 */
	@Override
	public void processImage() {
		BufferedImage curr = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// creates a copy of the image instance variable
		// saves as a local varriable called curr

		if (prev != null) { // skip first frame
			// Nested loop over every pixel
			for (int y = 0; y < image.getHeight(); y++) { // loop over x, y ,locations
				for (int x = 0; x < image.getWidth(); x++) {
					// tet pixxel color from this image (c1) and previous image(c2)
					Color c1 = new Color(image.getRGB(x,y));
					Color c2 = new Color(prev.getRGB(x,y));
					Color c = new Color(Math.abs(c1.getRed()-c2.getRed()),
										Math.abs(c1.getGreen()-c2.getGreen()),
										Math.abs(c1.getBlue()-c2.getBlue()));
					// compares image and subtracts color
					image.setRGB(x,y,c.getRGB());
					//updates image
				}
			}
		}
		prev = curr;
		// previous gets set to current
	}

	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WebcamDiff();
			}
		});
	}
}
