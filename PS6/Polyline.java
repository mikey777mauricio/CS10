import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @co-author Mikey Mauricio, CS10, Fall 2021
 */
public class Polyline implements Shape {
	private ArrayList<Point> points; // list of points
	private Color color; // color

	public Polyline(Point point, Color color){
		this.points = new ArrayList<>();
		this.points.add(point);
		this.color = color;
	}



	/**
	 * moveBy
	 * method moves shape by given dx and dy
	 * @param dx movement of x
	 * @param dy movement of y
	 */
	@Override
	public void moveBy(int dx, int dy) {
		// change each x and y by given values
		for(int k = 0; k < points.size()-1; k++){
			points.get(k).x += dx; // change x
			points.get(k).y += dy; // change y
		}
	}

	/**
	 * addPoint
	 * adds point to polyline
	 * @param p point to add
	 */
	public void addPoint(Point p){
		points.add(p); // add point
	}

	/**
	 * contains
	 * checks if a given point is contained within polyline
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return true or false
	 */
	public boolean contains(int x, int y){
		// iterate through each point
		for(int k = 0; k < points.size()-1; k++){
			// if returns less than or equal to 2, return true
			if(Segment.pointToSegmentDistance(x, y, points.get(k).x, points.get(k).y,
			points.get(k+1).x, points.get(k+1).y) <= 2){
				return true;
			}
		}
		return false; // return false if not
	}

	/**
	 * getColor
	 * returns color
	 * @return color
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/**
	 * setColor
	 * sets color for the polyline
	 * @param color The shape's color
	 */
	@Override
	public void setColor(Color color) {
		this.color = color;
	}


	/**
	 * draw
	 * method sets color and then draws each point connected by a line
	 * @param g graphics
	 */
	@Override
	public void draw(Graphics g) {
		// set color
		g.setColor(color);
		// draw each point connected by lines
		for(int k = 0; k < points.size()-2; k++){
			g.drawLine(points.get(k).x, points.get(k).y, points.get(k+1).x, points.get(k+1).y);
		}
	}

	/**
	 * toString
	 * method returns a string representation of the polyline
	 * @return string of polyline
	 */
	@Override
	public String toString() {
		String res = "freehand "; // initialize string
		for(Point p : points){ // add each point
			res += p.x + " " + p.y + " ";
		}
		return res += color.getRGB(); // add color at end
	}
}
