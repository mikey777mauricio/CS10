import java.util.ArrayList;

public class Explosion extends DrawingGUI {
    private static final int width=800, height=600;
    private ArrayList<Blob> blobList = new ArrayList<Blob>();
    public Explosion() {
        super("explosion", width, height);
        blobList.add(new Blob(width/2, height/2));
        startTimer();
    }
    public void handleTimer() {
        ArrayList<Blob> newGen = new ArrayList<Blob>();
        for(Blob blob : blobList){
            newGen.add(new Blob(blob.getX() + (Math.random() -.5),blob.getY() + (Math.random() -.5)));
            newGen.add(new Blob(blob.getX() + (Math.random() -.5),blob.getY() + (Math.random() -.5)));

        }
        blobList = newGen;
    }
}