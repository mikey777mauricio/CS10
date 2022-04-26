/**
 * SA1 New Blob Wondering without Shiver
 * @author Mikey Mauricio, CS10. Fall 2021
 */
public class WandererResolutely extends Blob{  //inherits instance variables the blob has
    private double amountStep, currentStep; // instance variables for amount of steps between velocity changes

    public WandererResolutely(double x, double y) {
        super(x, y);  // constructor 1
        currentStep = 0; // set current step to 0
        amountStep = (int)(6 * Math.random() + 4);  // set random step count from 4-9
    }

    @Override
    public void step() {
        if(currentStep== amountStep) { // if current step reaches limit, change dx and dy
            dx = (Math.random()-0.5) *2; // random from -1 to 1
            dy = (Math.random()-0.5) *2;

            currentStep =0; // set current step to 0
        }
        else{
            currentStep++; //increase current step
        }

        x += dx; // move blobs position
        y += dy;

    }
}
