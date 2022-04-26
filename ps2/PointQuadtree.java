import org.bytedeco.libfreenect._freenect_context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position,
 * with children at the subdivided quadrants.
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015.
 * @author CBK, Spring 2016, explicit rectangle.
 * @author CBK, Fall 2016, generic with Point2D interface.
 * @co-author Mikey Mauricio, Fall 2021, PS2
 */
public class PointQuadtree<E extends Point2D> {
	private E point;							// the point anchoring this node
	private int x1, y1;							// upper-left corner of the region
	private int x2, y2;							// bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;	// children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters

	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		// check which quadrant p2 is located
		// if tree has child in quadrant, call function on child
		if(checkQ1((int)p2.getX(), (int)p2.getY())){
			if(hasChild(1)) c1.insert(p2);
			// else, create a new tree
			else c1 = new PointQuadtree<E>(p2, (int)point.getX(), (int)y1, (int)x2, (int)point.getY());
		}
		else if(checkQ2((int)p2.getX(), (int)p2.getY())){
			if(hasChild(2)) c2.insert(p2);
			else c2 = new PointQuadtree<E>(p2, (int)x1, (int)y1, (int)point.getX(), (int)point.getY());
		}
		else if(checkQ3((int)p2.getX(), (int)p2.getY())){
			if(hasChild(3)) c3.insert(p2);
			else c3 = new PointQuadtree<E>(p2, (int)x1, (int)point.getY(), (int)point.getX(), (int)y2);
		}
		else if(checkQ4((int)p2.getX(), (int)p2.getY())) {
			if (hasChild(4)) c4.insert(p2);
			else c4 = new PointQuadtree<E>(p2, (int)point.getX(), (int)point.getY(), (int)x2, (int)y2);
		}
	}

	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		int num = 1;
		// call method on each child
		if(hasChild(1)) num += c1.size();
		if(hasChild(2)) num += c2.size();
		if(hasChild(3)) num += c3.size();
		if(hasChild(4)) num += c4.size();
		return num;
	}

	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		List<E> pointsInQuad = new ArrayList<E>(); // new list
		allPointsHelper(pointsInQuad); // send to helper function
		return pointsInQuad; // return list
	}

	// all points helper to add to list
	public void allPointsHelper(List<E> list){
		// call on each child and then add to list
		if (hasChild(1)) c1.allPointsHelper(list);
		if (hasChild(2)) c2.allPointsHelper(list);
		if (hasChild(3)) c3.allPointsHelper(list);
		if (hasChild(4)) c4.allPointsHelper(list);
		list.add(point);
	}

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 * @return    	the points in the circle (and the qt's rectangle)
	 */
	public List<E> findInCircle(double cx, double cy, double cr) {
		List<E> found = new ArrayList<>(); // new list of elements
		// check if circle is in rectangle
		if(Geometry.circleIntersectsRectangle(cx, cy, cr, (double)x1, (double)y1, (double)x2, (double)y2)) {
			addToList(found, cx, cy, cr); // send to helper function
		}
		System.out.println(found);
		return found;
	}

	//find in a circle helper method to add points into list
	public void addToList(List<E> list, double cx, double cy, double cr){
		// recursively call on each child
		for(int i = 1; i <= 4; i++) {
			if (hasChild(i)) {
				if (Geometry.circleIntersectsRectangle(cx, cy, cr, getChild(i).getX1(), getChild(i).getY1(), getChild(i).getX2(), getChild(i).getY2())) {
					getChild(i).addToList(list, cx, cy, cr);
				}
			}
		}
		// check to see if point is in circle, add it to found if it is
		if(Geometry.pointInCircle(point.getX(), point.getY(), cx, cy, cr)) {
			list.add(point);
		}
	}

	// helper check functions, returns true if in point
	public boolean checkQ1(int x, int y){
		return (x > point.getX() && x <= x2 && y < point.getY() && y >= y1);
	}
	public boolean checkQ2(int x, int y){
		return (x < point.getX() && x >= x1 && y < point.getY() && y >= y1);
	}
	public boolean checkQ3(int x, int y){
		return (x < point.getX() && x >= x1 && y > point.getY() && y <= y2);
	}
	public boolean checkQ4(int x, int y){
		return (x > point.getX() && x <= x2 && y > point.getY() && y <= y2);
	}

	// returns true if point is a leaf
	public boolean isLeaf(){
		return (c1 == null && c2 == null && c3 == null && c4 == null);
	}

}
