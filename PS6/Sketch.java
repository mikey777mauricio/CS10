import java.awt.*;
import java.util.Map;
import java.util.TreeMap;
/**
 * A sketch of user generate shapes
 *
 * @author Mikey Mauricio, CS10, Fall 2021
 */
public class Sketch {
    public static Map<Integer, Shape> idAndShape; // map of id and shapes
    public int id; // id

    public Sketch(){
        idAndShape = new TreeMap<>(); // new treemap
    }


    /**
     * removeShape
     * removes shape from sketch
     * @param key index of shape to remove
     */
    public void removeShape(int key){
        idAndShape.put(key, null); // set shape to null
    }

    /**
     * getIdAndShape
     * returns id and shape map
     * @return map of id and shapes
     */
    public Map<Integer, Shape> getIdAndShape(){
        return idAndShape; // return map
    }

    /**
     * recolorShape
     * method recolors a selected shape
     * @param index of shape
     * @param color to change shape
     */
    public void recolorShape(int index, Color color){
       Shape newShape = idAndShape.get(index); // new shape
       newShape.setColor(color); // set color
       idAndShape.put(index, newShape); // replace in map
    }

    /**
     * moveShape
     * method moves shape given x and y values
     * @param index index of shape
     * @param dx change of x
     * @param dy change of y
     */
    public void moveShape(int index, int dx, int dy){
        idAndShape.get(index).moveBy(dx, dy); // move selected shape
    }

    /**
     * getID
     * method returns the id of a given shape
     * @param shape shape to get id
     * @return id of given shape
     */
    public static int getID(Shape shape){
        // iterate through each shape
        for(int x : idAndShape.keySet()){
            // if it matches give shape
            if(idAndShape.get(x) != null && idAndShape.get(x).toString().equals(shape.toString())) return x; // return id
        }
        return -1; // else return -1
    }

    /**
     * addShape
     * adds shape to sketch
     * @param shape to add
     */
    public void addShape(Shape shape){
        int index = idAndShape.size(); // index = size of map
        idAndShape.put(index, shape); // put index and given shape into map

    }

    /**
     * toString
     * returns a string representation of the sketch
     * @return string of sketch
     */
    public String toString(){
        String res = ""; // initialize res
        // iterate through each shape
        for(int id : idAndShape.keySet()){
            res += id + "|" + idAndShape.get(id) + "\n"; // add to res
        }
        return res; // return string
    }



}
