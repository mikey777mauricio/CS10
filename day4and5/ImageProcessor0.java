import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 * @Co-Author Mikey Mauricio, CS 10, Fall 2021
 */
public class ImageProcessor0 {
	private BufferedImage image; // the current image being processed
	public Color colorChange;

	/**
	 * @param image the original
	 */
	public ImageProcessor0(BufferedImage image) {
		this.image = image;
	}
	// setting instance variable

	public BufferedImage getImage() {
		return image;
	}
	//returns image

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	// sets image

	/**
	 * @param cx center x of square
	 * @param cy center y of square
	 * @param r  radius of square
	 */


	public void changeColor(int cx, int cy, int r, char c) {    //Nested loop over nearby pixels
		//Loop from r4 rows above to 4 r rows below mouse
		// draw a square around the mouse location using a random color

		// user picks a color to turn off in the image using the brush
		for (int y = Math.max(0, cy - r); y < Math.min(image.getHeight(), cy + 8 * r); y++) {
			for (int x = Math.max(0, cx - r); x < Math.min(image.getWidth(), cx + 8 * r); x++) {

				Color color = new Color(image.getRGB(x, y)); //new color
				int green = color.getGreen(); // get rgb values
				int red = color.getRed();
				int blue = color.getBlue();

				if (c == 'b') { // if user selected b,g,r turn off value
					colorChange = new Color(red, green, 0);
				}
				//Color invertColor = new Color(red, green, blue);
				if (c == 'g') {
					colorChange = new Color(red, 0, blue);

				}
				if (c == 'r') {
					colorChange = new Color(0, green, blue);
				}
				image.setRGB(x, y, colorChange.getRGB()); // set x and y points to that value

			}
		}
	}

	public void flip() {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

		// iterate through x values
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int x2 = image.getWidth() - x - 1;
				result.setRGB(x, y, image.getRGB(x2, y));
			}
		}
		image = result;
	}

	public void greenUp() {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		// iterate through each pixel
		for (int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				//get green
				Color c = new Color(image.getRGB(x,y));
				int cmax = c.getGreen();
				// iterate through neighbors
				for(int ny = Math.max(0, y-1); ny < Math.min(y+1, image.getHeight()-1); ny++)
					for(int nx = Math.max(0, x-1); nx < Math.min(x+1, image.getWidth()-1); nx++){
						cmax = Math.max(cmax, (new Color(image.getRGB(nx, ny))).getGreen());
					}
					//green scale
				int newGreen = (int)(Math.random() * (cmax-c.getGreen()) + c.getGreen());
				result.setRGB(x,y, (new Color(c.getRed(), Math.min(255, newGreen), c.getBlue())).getRGB());
			}
		}


		image = result;
	}


	public BufferedImage shift(BufferedImage img, int maxShift) {
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		// loop through all points
		for (int y = 0; y < img.getHeight(); y++) {
			int rot = (int) (Math.random() * 2 * maxShift - 1);

			for (int x = 0; x < img.getWidth(); x++) {
				int newX = (x + rot) % img.getWidth(); // wrap around
				if(newX < 0){newX = (img.getWidth() - (x+rot) - 1);} // negative wrap
				result.setRGB(newX, y, img.getRGB(x, y));

			}
		}
		return result;
	}


}










