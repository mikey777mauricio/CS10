import java.awt.*;

/**
 * A message
 * splits message into commands
 *
 * @author Mikey Mauricio, CS10, Fall 2021
 */
public class Message {
    String info; // message
    String[] commands; // list of commands

    public Message(String msg){
        this.info = msg;
        this.commands = msg.split(" ");

    }

    /**
     * getCommand
     * returns command
     * @return command
     */
    public String getCommand(){
        return commands[0]; // return command

    }

    /**
     * handle
     * method handles messages that have been split into commands
     * @param sketch to alter
     */
    public synchronized void handle(Sketch sketch){
        // if add
        if(commands[0].equals("add")){
            // add shape
            sketch.getIdAndShape().put(Integer.parseInt(commands[commands.length-1]), getShape());
        }
        // if move
        else if (commands[0].equals("move")){
            // call move shape method
            sketch.moveShape(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
        }
        // if delete
        else if (commands[0].equals("delete")){
            // call delete method
            sketch.removeShape(Integer.parseInt(commands[1]));
        }
        // if recolor
        else if (commands[0].equals("recolor")){
            // initialize new shape
            Color newColor = new Color(Integer.parseInt(commands[2]));
            // call recolor shape method
            sketch.recolorShape(Integer.parseInt(commands[1]),newColor);
        }
    }

    /**
     * getShape
     * given message, this method determines and creates a shape from the message
     * @return shape with correct coordinates and color
     */
    public Shape getShape(){
        // set all coordinates
        int x1 = Integer.parseInt(commands[2]);
        int y1 = Integer.parseInt(commands[3]);
        int x2 = Integer.parseInt(commands[4]);
        int y2 = Integer.parseInt(commands[5]);
        int rgb = Integer.parseInt(commands[6]);
        Shape shape = null; // new shape
        // set shape to corresponding type
        if(commands[1].equals("rectangle")){
            shape = new Rectangle(x1,y1,x2,y2, new Color(rgb));

        }
        if(commands[1].equals("ellipse")){
            shape = new Ellipse(x1,y1,x2,y2, new Color(rgb));

        }
        if(commands[1].equals("segment")){
            shape = new Segment(x1,y1,x2,y2, new Color(rgb));

        }
        // if freehand
        if(commands[1].equals("freehand")){
            // new polyline
            shape = new Polyline(new Point(Integer.parseInt(commands[2]), Integer.parseInt(commands[3])), new Color(rgb));
            // iterate through each x and y coordinate and add to polyline list of points
            for(int k = 4; k < commands.length-1; k+=2){
                ((Polyline)shape).addPoint(new Point(Integer.parseInt(commands[k]),
                        Integer.parseInt(commands[k+1])));
            }


        }
        return shape; // return shape

    }

    /**
     * getID
     * returns id of selected shape
     * @return id
     */
    public int getID(){
        return Integer.parseInt(commands[commands.length-1]);
    }

    /**
     * toString
     * returns message as whole
     * @return message
     */
    public String toString(){
        return info;
    }
}
