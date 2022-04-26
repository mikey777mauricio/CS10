import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Creates a Hidden Markov Model (HMM) with observations as words in a sentence
 * and states being tags. The maps are made to help train the computer and
 * help the HMM to identify individual word's part of speech given any text.
 *
 * @author Mikey Mauricio
 * @Purpose PS5
 * @Date 11/4/2021
 */
public class HiddenMarkovModel {
    Map<String, Map<String, Double>> transitions; // map to hold transitions
    Map<String, Map<String, Double>> observations; // map to hold observations
    public HiddenMarkovModel(String tagFile, String sentencesFile) throws IOException {
        transitions = new TreeMap<>();
        observations = new TreeMap<>();
        getMapTransitions(tagFile); // get transition map
        getObservationsMap(tagFile, sentencesFile); // get observation map
        convertToLog(); // convert each score to logarithmic value
    }

    /**
     * getMapTransitions
     * Creates a map that holds POS as keys with a value being another map with
     * POS and a given score for the transition.
     *
     * @param tags file that holds sentences with POS instead of words
     * @throws IOException when file is not found
     */
    public void getMapTransitions(String tags) throws IOException {
        //new buffered reader to read tag file
        BufferedReader inTrans = new BufferedReader(new FileReader(tags));
        // map to hold POs and associated score
        Map<String, Double> transValue = new TreeMap<>();
        transitions.put("#", transValue); // add start to map
        String line; // initialize string variable for reading
        while((line = inTrans.readLine()) != null){ // while another line can be read
            String[] tagList = line.split(" "); // split line into list
            for(String s : tagList){ // iterate through each POS
                if(!transitions.containsKey(s)) { // if map does not yet contain POS
                    transValue = new TreeMap<>(); // value for key
                    transitions.put(s, transValue); // add to map
                }
            }
            // add start transition
            if(!transitions.get("#").containsKey(tagList[0])){
                transitions.get("#").put(tagList[0], 0.0);
            }
            // put first POS into start
            transitions.get("#").put(tagList[0], transitions.get("#").get(tagList[0])+1);
            // put rest of POS into map
            for(int i = 0; i < tagList.length -1; i++){
                if(!transitions.get(tagList[i]).containsKey(tagList[i+1])){
                    transitions.get(tagList[i]).put(tagList[i+1],0.0);
                }
                transitions.get(tagList[i]).put(tagList[i+1],transitions.get(tagList[i]).get(tagList[i+1])+1);
            }

        }
        inTrans.close(); // close file
        // add total column
        for(String state : transitions.keySet()){
            double total = 0; // initialize total
            for(double x : transitions.get(state).values()){
                total += x; // increment total by value
            }
            transitions.get(state).put("total", total); // update total
        }

    }

    /**
     * getObservationsMap
     * Creates a map that holds POS as keys with a value being another Map that
     * holds words as keys and score for the observation.
     *
     * @param transFile file that contains sentences with only POS
     * @param obsFile file that contains sentences that align with transFile
     * @throws IOException when either file is unable to be found
     */
    public void getObservationsMap(String transFile, String obsFile) throws IOException {
        // create new reader for each file
        BufferedReader inTrans = new BufferedReader(new FileReader(transFile));
        BufferedReader inObs = new BufferedReader(new FileReader(obsFile));
        Map<String, Double> wordMap; // initialize word map
        String lineT; String lineO; // initialize line for each file
        while((lineT = inTrans.readLine()) != null){ // while another line to read
            lineO = inObs.readLine(); // read next line
            // split both lines to list
            String[] transList = lineT.split(" ");
            String[] obsList = lineO.split(" ");
            for(int i = 0; i < transList.length; i++){ // iterate through each list
                if(!observations.containsKey(transList[i])){ // add if the map does not contain
                    wordMap = new TreeMap<>();
                    observations.put(transList[i], wordMap);
                }
                if(!observations.get(transList[i]).containsKey(obsList[i])){
                    observations.get(transList[i]).put(obsList[i], 0.0);
                }
                // update observation map
                observations.get(transList[i]).put(obsList[i],
                        observations.get(transList[i]).get(obsList[i])+1);
            }
        }
        // close each file
        inTrans.close(); inObs.close();
        // create totals
        for(String state : observations.keySet()){
            double total = 0;
            for(double x : observations.get(state).values()){
                total += x;
            }
            observations.get(state).put("total", total); // update total
        }
    }

    /**
     * convertToLog
     * converts each score in each map to a logarithmic value
     */
    public void convertToLog(){
        for(String state : transitions.keySet()){ // iterate through each key set
            for(String trans : transitions.get(state).keySet()){ // iterate through each value
                if(!trans.equals("total")) {
                    // get frequency
                    double freq = transitions.get(state).get(trans);
                    // get total
                    double total = transitions.get(state).get("total");
                    // calculate new score
                    double prob = Math.log(freq / total);
                    // update state value
                    transitions.get(state).put(trans, prob);
                }
            }
        }
        for(String state : observations.keySet()){ // iterate through each key set
            for(String obs : observations.get(state).keySet()){ // iterate through each value
                if(!obs.equals("total")){
                    // get frequency
                    double freq = observations.get(state).get(obs);
                    // get total
                    double total = observations.get(state).get("total");
                    // calculate new score
                    double prob = Math.log(freq / total);
                    // update state value
                    observations.get(state).put(obs, prob);
                }
            }
        }
    }





}
