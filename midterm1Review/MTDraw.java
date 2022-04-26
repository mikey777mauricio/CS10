import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MTDraw extends DrawingGUI {
    public ArrayList<Point> ovals = new ArrayList<Point>();;

    public MTDraw() {
        super("MT Draw", 800, 600);
        ovals = new ArrayList<Point>();
    }
    public void handleMousePress(int x, int y) {
        ovals.add(new Point(x, y));
        repaint();
    }
    public void draw(Graphics g) {
        for(Point oval : ovals)
        {
            g.fillOval((int)oval.getX(), (int)oval.getY(), 20,10);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MTDraw();
            }
        });
    } }
