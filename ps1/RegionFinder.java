import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Template for PS-1, Dartmouth CS 10, Spring 2016
 *
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 * @ Co-author Mikey Mauricio, Fall 2021
 */
public class RegionFinder {
    private static final int maxColorDiff = 50;                // how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 30;                // how many points in a region to be worth considering

    private BufferedImage image;                            // the image in which to find regions
    private BufferedImage recoloredImage;                   // the image with identified regions recolored

    private ArrayList<ArrayList<Point>> regions = new ArrayList<>();            // a region is a list of points
    // so the identified regions are in a list of lists of points

    public RegionFinder() {
        this.image = null;
    }

    public RegionFinder(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    private Stack<Point> toVisit = new Stack();

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ArrayList<ArrayList<Point>> getRegions() {
        return regions;
    }

    public BufferedImage getRecoloredImage() {
        return recoloredImage;
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     */
    public void findRegions(Color targetColor) {
        regions.clear(); //clear regions
        ArrayList<Point> newRegion = new ArrayList<>(); // new region list
        //keep track of visited
        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //loop over each point
        for (int y = 1; y < image.getHeight(); y++) {
            for (int x = 1; x < image.getWidth(); x++) {

                // if pixel unvisited and of correct color
                if (visited.getRGB(x, y) == 0 && colorMatch(new Color(image.getRGB(x, y)), targetColor)) {
                    newRegion = new ArrayList<Point>();
                    Point newPoint = new Point(x, y);
                    toVisit.push(newPoint);

                    // check neighbors until stack empty
                    while (!toVisit.isEmpty()) {
                        Point current = new Point((toVisit.pop())); // pop point
                        newRegion.add(current); // add it to region
                        // set coordinates of point to white in visited image
                        visited.setRGB((int)current.getX(), (int)current.getY(), 1);
                        // make sure neighbors are in bound of coordinates
                        if (current.getY() < image.getHeight() - 2 && current.getY() - 2 > 0) {
                            if(current.getX() < image.getWidth() -2 && current.getX() -2 > 0 ){
                                // iterate through each neighbor
                                for (int k = ((int)current.getY() - 1); k < (int)current.getY() + 2; k++) {
                                    for (int l = ((int)current.getX() - 1); l < (int)current.getX() + 2; l++) {
                                        if (visited.getRGB(l, k) == 0 && colorMatch(targetColor, new Color(image.getRGB(l, k)))) {
                                            toVisit.push(new Point(l, k));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // add region to regions
                    if (newRegion.size() >= minRegion) {
                        regions.add(newRegion);
                    }
                }
            }
        }
    }

    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the static threshold).
     */
    private static boolean colorMatch(Color c1, Color c2) {
        // compare colors by subtracting each color code and adding the absolute values together
        int d = Math.abs(c1.getRed() - c2.getRed())
                + Math.abs(c1.getGreen() - c2.getGreen())
                + Math.abs(c1.getBlue() - c2.getBlue());
        return d <= maxColorDiff;
    }

    /**
     * Returns the largest region detected (if any region has been detected)
     */
    public ArrayList<Point> findLargestRegion() {
        // initialize new largest return list to return
        ArrayList<Point> largest = new ArrayList<>();
        if(regions.size() != 0) {
            largest = regions.get(0); // set region to first index of regions
            for (ArrayList<Point> region : regions)
            {
                if(region.size() > largest.size())
                {
                    largest = region;
                }
            }
            return largest;
        }
        return null;
    }



    /**
     * Sets recoloredImage to be a copy of image,
     * but with each region a random uniform color,
     * so we can see where they are
     */
    public void recolorImage() {

        // First copy the original
        recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
        // Now recolor the regions
        for (ArrayList<Point> region : regions) // for each region
        {
            Color randColor = new Color((int)(Math.random() * 16777216)); // new random color
            for(Point pixel: region) // iterate through each point
            {
                // recolor image at each point
                recoloredImage.setRGB((int)pixel.getX(), (int)pixel.getY(), randColor.getRGB());
            }
        }
    }
}
