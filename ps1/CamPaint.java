import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Webcam-based drawing 
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 * @ Co-author Mikey Mauricio, Fall 2021
 */

public class CamPaint extends Webcam {
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private RegionFinder finder;			// handles the finding
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;			// the resulting masterpiece

	private ArrayList<Point> regionToDraw = new ArrayList<>();  // list of regions to draw

	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaint() {
		finder = new RegionFinder();
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * DrawingGUI method, here drawing one of live webcam, recolored image, or painting, 
	 * depending on display variable ('w', 'r', or 'p')
	 */
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (finder.getImage() != null) { // if finder has an image to get
			if (displayMode == 'p') { // if in painting mode
				g.drawImage(painting, 0, 0, null); // graphics display painting
			}
			if (displayMode == 'r') { //if in recolor mode
				if (targetColor != null) { // if there is a target color
					g.drawImage(finder.getRecoloredImage(), 0, 0, null); // draw the recolored image
				} else {
					g.drawImage(image, 0, 0, null); // else, draw image
				}
			}
			if (displayMode == 'w') { //if in webcam mode
				g.drawImage(image, 0, 0, null); // draw image
			}



		}
	}

	/**
	 * Webcam method, here finding regions and updating the painting.
	 */
	@Override
	public void processImage() {
		if (image != null) { // while there is an image to display
			finder.setImage(image); // pass image through region finder
				if(targetColor != null) { // if there is a target color
					finder.findRegions(targetColor); // pass targetColor to find regions
					finder.recolorImage(); // recolor the image
					ArrayList<Point> largeRegion = finder.findLargestRegion(); // initialize new list of points
					if (largeRegion != null) {
						for (Point point : largeRegion) { // iterate through the points
							painting.setRGB(point.x, point.y, paintColor.getRGB()); // painting the largest region color
						}
					}
				}
			}
		}




	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		if (image != null) { // while there is an image to display
			targetColor = new Color(image.getRGB(x, y)); // set targetColor to user click
		// saves tracked color as instance variable
			System.out.println("tracking " + targetColor);
	}
}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
			displayMode = k;
		}
		else if (k == 'c') { // clear
			clearPainting();
		}
		else if (k == 'o') { // save the recolored image
			saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
		}
		else if (k == 's') { // save the painting
			saveImage(painting, "pictures/painting.png", "png");
		}
		else {
			System.out.println("unexpected key "+k);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CamPaint();
			}
		});
	}
}
