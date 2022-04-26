import java.io.*;

/**
 * prints and calculates accuracy of Viterbi class.
 *
 * @author Mikey Mauricio
 * @Purpose PS5
 * @Date 11/4/2021
 */
public class ps5test {
    public static void compareFiles(String tags, String output) throws IOException {
        // new buffer reader for output file and given tags
        BufferedReader tag = new BufferedReader(new FileReader(tags));
        BufferedReader out = new BufferedReader(new FileReader(output));
        String tagLine; String outLine; // initialize tag and out line
        double wrong = 0.0; double total = 0.0; // initialize wrong and total values
        try {
            while ((tagLine = tag.readLine()) != null) { // while another line to read
                outLine = out.readLine(); // read next line
                // split each line into list
                String[] tagList = tagLine.split(" ");
                String[] outList = outLine.split(" ");
                // iterate through each POS
                for (int i = 0; i < tagList.length; i++) {
                    total++; // increment total
                    // if tags do not match
                    if (!tagList[i].equals(outList[i])) {
                        wrong++; // increment wrong
                    }
                }
            }
        }
        // handle if there is an exception
        catch (Exception e){
            System.out.println("Error");
        }

        System.out.println("# Incorrect: " + wrong); // print amount wrong
        System.out.println("% Correct: " + 100*(1.0 - (wrong/total))); // print correct percentage
    }

    /**
     * main
     * calls compareFile function given output and example tags
     *
     * @param args default param
     * @throws IOException if file not found
     */
    public static void main(String[] args) throws IOException {
        // call compareFiles method
        ps5test.compareFiles("ps5/brown-test-tags.txt", "ps5/output.txt");
    }
}
