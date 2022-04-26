import javax.swing.SwingUtilities;

/**
 * Fun with the webcam, built on JavaCV
 * Just applies one of our image processing methods to the webcam image
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for simplified Webcam class
 * @author CBK, Spring 2015, refactored for ImageProcessor
 */
public class WebcamProcessing extends Webcam { // inherit from webCam
	private ImageProcessor proc;	// handles image processing
	//add imageProcessor from last lecture to be able to manipulate images
	// we dont have to rewrite all that code
	
	public WebcamProcessing() {
		proc = new ImageProcessor(null);
	}
	
	/**
	 * Use proc to change colors (try something more fun yourself)
	 * Called when image captured by amera
	 * Last camera frame stored in image
	 */
	@Override
	public void processImage() {
		proc.setImage(image);
		//set image in ImageProcessor proc
		proc.scaleColor(0.5, 0.5, 1.5);
		//use proc to scale color
		// emphasize blue color
		image = proc.getImage();
		// do not forget to set WebCam instance variable image to the processed image
		// do not need "this" because there is no local variable called "image"
		// webCam makes it so everytime camera picks up a new frame it displays it
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WebcamProcessing();
			}
		});
	}
}
