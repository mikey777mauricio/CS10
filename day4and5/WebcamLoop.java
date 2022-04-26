import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Fun with the webcam, built on JavaCV
 * Records and plays back (in reverse) a loop of images
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Spring 2015, rewritten for Webcam class
 * @author CBK, Spring 2016, for Java CV 1.1
 */
public class WebcamLoop extends Webcam {
	private ArrayList<BufferedImage> frames; //create a buffer image and adds it to the list
	//when recording, add each image captured to end of buffer
	//when playing back, get each image in buffer and display in order
	private boolean recording = true;	// start off recording
	private int frame = 0;				// where in the loop we are

	public WebcamLoop() {
		frames = new ArrayList<BufferedImage>();
		System.out.println("recording:"+recording);
	}

	/**
	 * Override to start/stop recording
	 * @param event
	 */
	@Override
	public void handleMousePress(int x, int y) {
		// Toggle record/play and go back to frame 0 (with cleared frames if recording)
		recording = !recording;
		if (recording) {
			frames = new ArrayList<BufferedImage>();
		}
		else {
			frame = frames.size()-1;		
		}
		System.out.println("recording:"+recording);
	}

	/**
	 * Override to show either live or playback
	 */
	@Override
	public void draw(Graphics g) {
		if (recording) {
			g.drawImage(image, 0, 0, null);
			// if recording, just show latest image
			// if not recording, playback in reverese
			// get last image from bugger
			//show that image
			// get previous image
			// show that image
			// repeat
		}
		else {
			g.drawImage(frames.get(frame), 0, 0, null);
			frame--;
			if (frame < 0) frame = frames.size()-1;
			System.out.println("played:"+frame);
		}
	}

	/**
	 * Webcam method, here storing frames.
	 */
	@Override
	public void processImage() {
		if (recording) {
			BufferedImage copy = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
			frames.add(copy);
			// if recording, add each new frame to the ArrayList buffer
			// we did we copy image?
			// because image will change
			System.out.println("recorded:"+frames.size());
		}
	}

	/**
	 * Main method for the application
	 * @param args		command-line arguments (ignored)
	 */
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WebcamLoop();
			}
		});
	}
}
