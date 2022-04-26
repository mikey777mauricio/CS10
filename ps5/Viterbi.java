import javax.swing.*;
import java.io.*;
import java.util.*;
/**
 * Using the HMM, this code reads over a file of text, writes to output each words POS, and then prints POS
 * to console.
 *
 * @author Mikey Mauricio
 * @Purpose PS5
 * @Date 11/4/2021
 */
public class Viterbi {
    final double unseen = -15.0; // unseen value
    HashSet<String> currStates; // set of current states
    Map<String, Double> currScores; // map of current scores

    public static void main(String[] args) throws IOException {
        Viterbi test = new Viterbi();
        HiddenMarkovModel hmm = new HiddenMarkovModel("ps5/brown-train-tags.txt", "ps5/brown-train-sentences.txt");
        test.posViterbi(hmm, test.splitLines("ps5/brown-test-sentences.txt"));
        //test.inputSentence(hmm);

    }

    /**
     * inputSentence
     * allows user input to generate POS list
     *
     * @param hmm Hidden Markov Model
     * @throws IOException for invalid inputs or error from HMM
     */
    public void inputSentence(HiddenMarkovModel hmm) throws IOException {
        Scanner in = new Scanner(System.in); // new scanner
        // initialize new array list of list, to match posViterbi param
        List<List<String>> comList = new ArrayList<>();
        while (true) {
            // list for words
            List<String> wordsToPosList = new ArrayList<>();
            System.out.print("Input Sentence: "); // output to user
            String line = in.nextLine(); // get line
            String[] words = line.split(" "); // split line
            for(String word : words){
                wordsToPosList.add(word.toLowerCase()); // add to list in lowercase
            }
            comList.add(wordsToPosList); // add to list to annotate
            Viterbi user = new Viterbi(); // new viterbi object
            user.posViterbi(hmm, comList); // pos viterbi on user input
        }
    }

    /**
     * splitLines
     * splits a text file into a list of lines. Each line is also split into another list in order to be
     * able to access each word individually
     *
     * @param filename text file to split
     * @return List of already split sentences
     * @throws IOException if file is not found
     */
    public List<List<String>> splitLines(String filename) throws IOException {
        // create new buffered reader
        BufferedReader in = new BufferedReader(new FileReader(filename));
        List<List<String>> sentenceList = new ArrayList<>(); // list to hold words
        try {
            String line; // initialize line to be split
            while ((line = in.readLine()) != null) {
                // initialize individual line list
                List<String> sentence = new ArrayList<>();
                // split line into list
                String[] words = line.split(" ");
                for (String s : words) {
                    // add each word to list
                    sentence.add(s.toLowerCase());
                }
                // add sentence to list of sentences
                sentenceList.add(sentence);

            }
        }
        // handle exceptions
        catch(Exception e){
            System.out.println("Error 404: File not found.");
        }
        finally {
            in.close(); // close file
        }
        return sentenceList; // return list
    }

    /**
     * posViterbi
     * Reads over text file and writes to output file the POS for each word. Also prints each word's POS.
     *
     * @param hmm Hidden Markov Model
     * @param sentences sentences to annotate
     * @throws IOException if text file not found
     */
    public void posViterbi(HiddenMarkovModel hmm, List<List<String>> sentences) throws IOException {
        // create new buffered reader
        BufferedWriter out = new BufferedWriter(new FileWriter("ps5/output.txt"));
        // split text into list of sentences
        List<Map<String, String>> backTracer; // initialize backTracer
        // iterate through each sentence
        for (List<String> sentence : sentences) {
            currStates = new HashSet<>(); // initialize currStates or set as empty
            currStates.add("#"); // add start state
            currScores = new HashMap<>(); // initialize currScores or set as empty
            currScores.put("#", 0.0); // add start state and score of 0

            backTracer = new ArrayList<>(); // set backTracer to empty array list
            for (int i = 0; i < sentence.size(); i++) { // iterate through each observation
                Map<String, String> backTrack = new HashMap<>(); // initialize Map to track previous state
                backTracer.add(backTrack); // add to backTracer
                HashSet<String> nextStates = new HashSet<>(); // initialize next states set
                Map<String, Double> nextScores = new HashMap<>(); // initialize next score map
                for (String state : currStates) { // iterate through each current state
                    // iterate through each transition in state key set
                    for (String nextState : hmm.transitions.get(state).keySet()) {
                        if (!nextState.equals("total")) { // if state is not the total
                            nextStates.add(nextState); // add next state to set
                            double nextScore; // initialize next score
                            // calculate if seen
                            if (hmm.observations.get(nextState).containsKey(sentence.get(i))) {
                                nextScore = currScores.get(state) +
                                        hmm.transitions.get(state).get(nextState) +
                                        hmm.observations.get(nextState).get(sentence.get(i));
                            } else {
                                // calculate if unseen
                                nextScore = currScores.get(state) +
                                        hmm.transitions.get(state).get(nextState) + unseen;
                            }
                            // if next state is not in nextScores or is larger than previous score
                            if (!nextScores.containsKey(nextState) || nextScore > nextScores.get(nextState)) {
                                nextScores.put(nextState, nextScore); // add to nextScore map

                                backTrack.put(nextState, state); // add to back track

                            }
                        }

                    }

                }
                // set currents to next
                currStates = nextStates;
                currScores = nextScores;

            }
            // write to out put and print
            writeToFileHelper(backTracer, currScores, out);
        }

        // close output file
        out.close();

    }

    /**
     * writeToFileHelper
     * writes best estimated POS into output file and prints to console
     *
     * @param backTracer for method to find previous state
     * @param currScores for method to find the best possible score
     * @param out buffered writer to write POS
     * @throws IOException if output file not found
     */
    public void writeToFileHelper(List<Map<String, String>> backTracer, Map<String, Double> currScores,
                                  BufferedWriter out) throws IOException {
        double max = Double.NEGATIVE_INFINITY; // set initialize max score to negative infinity
        String type = null; // set most likely POS to null
        Stack<String> tags = new Stack<>(); // stack to list POS for output and print
        for (String x : currScores.keySet()) { // get last words POS
            // see if POS leads to a higher score
            if (currScores.get(x) > max) {
                max = currScores.get(x); // set new max
                type = x; // set type to new best POS
            }
        }
        tags.add(type); // add POS to stack

        // iterate through previous states until start
        for (int i = backTracer.size() - 1; i >= 0; i--) {
            tags.add(backTracer.get(i).get(type)); // add stack
            type = backTracer.get(i).get(type); // set type to current states type
        }
        String res = ""; // initialize res
        // while stack is not empty
        while(!tags.isEmpty()){
            res += tags.pop() + " "; // pop next tag
        }
        try {
            out.write(res.substring(2) + "\n"); // write sentence of POS to output file
            System.out.println(res + "\n"); // bring to console
        }
        // handle exceptions
        catch (Exception e){
            System.out.println("Error 404: Output file not found");
        }

    }

}
