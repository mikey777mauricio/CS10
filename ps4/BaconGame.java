/*
  Name: Mikey Mauricio
  Date: 10/28/2021
  Purpose: Implementation of Bacon Game with command-line interface
  PS4
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconGame extends PSGraphLib {
    String center; // center of universe
    Map<String, String> idToActors; // id to actors map
    Map<String, String> idToMovies; // id to movies map
    Map<String, Set<String>> movieToActors; // movie to actor map
    Graph<String, Set<String>> baconGraph; // graph of bacon game
    Graph<String, Set<String>> pathTree; // graph of path
    Map<String, Double> avgSepMap; // map of average separations
    Map<String, Integer> degreeMap; // map of degrees
    Map<String, Double> scoreMap; // map of scores

    public BaconGame(String actors, String movies, String movieToActor) throws Exception {
        center = "Kevin Bacon"; // set start center as Kevin Bacon
        idToActors = getIDMap(actors); // get ID to actor map
        idToMovies = getIDMap(movies); // get ID to movie map
        movieToActors = getMoviesToActors(movieToActor); // get movies to actor map
        getGraph(); // get graph
        System.out.println("Loading information of " + baconGraph.numVertices() + " actors...");
        getPathTree(); // get path tree
        getAvgSepMap(); // get average separation map
        getDegreeMap(); // get map of degrees
        getScoreMap(); // get score map
        startGame(); // start game
        methodU(center); // print initial center
    }

    public static void main(String[] args) throws Exception {
        try {
            BaconGame test = new BaconGame("ps4/actors.txt", "ps4/movies.txt", "ps4/movie-actors.txt");
        } catch (IOException e) {
            System.out.println("File not found");
            throw e;
        }
    }

    /**
     * getDegreeMap
     * constructs map of all actors and their degrees
     */
    public void getDegreeMap(){
        degreeMap = new TreeMap<>(); // initialize degree map
        for(String actor : baconGraph.vertices()){
            degreeMap.put(actor, baconGraph.outDegree(actor)); // add each actor
        }
    }

    /**
     * getScoreMap
     * initializes scoreMap for each actor, score is product of degrees and average separation
     */
    public void getScoreMap(){
        scoreMap = new TreeMap<>(); // initialize score map
        for(String actor : baconGraph.vertices()){
            double score = (double)degreeMap.get(actor) * avgSepMap.get(actor); // calc score
            scoreMap.put(actor, score); // put actor in map
        }
    }

    /**
     * methodR
     * prints user given number of recommendations of a good or bad "bacon", negative input
     * leads to actors with the lowest score and positive will compute the highest scores
     * @param num amount of recommendations to print
     */
    public void methodR(int num){
        int sign = num; // make final to provide for comparator
        class ScoreComp implements Comparator<String>{
            public int compare(String a1, String a2){
                int c;
                // account for positive or negative
                if (sign > 0) c = (int) (scoreMap.get(a2) - scoreMap.get(a1));
                else c = (int) (scoreMap.get(a1) - scoreMap.get(a2));
                return c;
            }
        }
        // new comparator
        ScoreComp comp = new ScoreComp();
        // PQ for sorting actors
        PriorityQueue<String> sortedScore = new PriorityQueue<>(comp);
        for(String actor : baconGraph.vertices()){
            // add each actor to PQ
            sortedScore.add(actor);
        }
        // string for describing best or worst
        String bestOrWorst = "best";
        if (num < 0) bestOrWorst = "worst";
        num = Math.abs(num); // guaranteed num to positive
        System.out.println("The top " + num + " " + bestOrWorst + " centers: ");
        while(!sortedScore.isEmpty() && num > 0){
            // print highest or lowest priority in PQ
            System.out.println(sortedScore.poll());
            num --; // decrement counter
        }
    }

    /**
     * handInputs
     * handles user input and computes corresponding method or throws exception
     * and restarts game
     * @param line filename to read
     * @throws Exception when invalid input
     */
    public void handleInputs(String line) throws Exception{

        // handle if empty input
        if(line.length() < 1){
            System.out.println("Invalid Input");
            getInput();
        }
        // set line to array of characters
        char[] letters = line.toCharArray();
        // set key as first char in array
        char key = Character.toLowerCase(letters[0]);
        // if key is c
        if (key == 'c') {
            // split to get input number
            String[] getNum = line.split(" ");
            try {
                // cast to integer
                int num = Integer.parseInt(getNum[1]);
                methodC(num); // do method c
            }
            catch (Exception e){
                System.out.println("Invalid Input");
            }
        }
        // if key is d
        else if (key == 'd'){
            // split input to get user boundaries
            String[] getNum = line.split(" ");
            try {
                // set num 1 as second index
                // set num 2 as third index
                if (getNum.length > 2){
                    int num1 = Integer.parseInt(getNum[1]);
                    int num2 = Integer.parseInt(getNum[2]);
                // do method d, set min to minimum of numbers and max to largest
                    methodD(Math.min(num1, num2), Math.max(num1, num2));
                }
                else System.out.println("Need 2 numbers...");
            }
            // if input cannot parse to int or not given 2 numbers
            catch (Exception e){
                System.out.println("Invalid Input");
            }
        }
        // if key is i
        else if (key == 'i') {
            methodI(); // do method i
        }
        // if key is p
        else if (key == 'p') {
            // get name by slicing input, start at index 2,
            // first index = letter, second = space
            try {
                String name = line.substring(2);
                // do method p, if invalid name, path will not exist
                methodP(name);
            }
            // handle out of bounds exception
            catch (Exception e){
                System.out.println("Invalid Input");
            }
        }
        // if key is s
        else if (key == 's') {
            // get user boundaries by splitting string
            String[] getNum = line.split(" ");
            try {
                // set num1 to first number input, and num 2 to second input
                int num1 = Integer.parseInt(getNum[1]);
                int num2 = Integer.parseInt(getNum[2]);
                // using boundaries, do method s
                methodS(Math.min(num1, num2), Math.max(num1, num2));
            }
            // handle out of bound or parsing exception
            catch (Exception e){
                System.out.println("Invalid Input");
            }
        }
        // if key is u
        else if (key == 'u') {
            try {
                // get name by slicing input
                String name = line.substring(2);
                if(baconGraph.hasVertex(name)) {
                    methodU(name); // do method u
                }
                else System.out.println("Invalid Name");
            }
            // handle out of bounds or invalid key exception
            catch (Exception e){
                System.out.println("Invalid Input");
                center = "Kevin Bacon"; // reset center
            }
        }
        // if key is r
        else if (key == 'r'){
            // split to get input number
            String[] getNum = line.split(" "); // get num
            try {
                // cast to integer
                int num = Integer.parseInt(getNum[1]);
                methodR(num); // do method c
            }
            catch (Exception e){ // if unable to parse
                System.out.println("Invalid Input");
            }
        }
        else if (key == 'q'){
            System.out.println("Thank you for playing.");
            System.exit(0);
        }
        else{
            System.out.println("Invalid Input");
        }

        getInput(); // loop game until quit

    }

    /**
     * startGame
     * starts bacon game, creates new scanner for user input, and displays game message
     * @throws Exception if invalid file or invalid input
     */
    public void startGame() throws Exception {
        displayStart(); // display start method
        getInput(); // get user input

    }

    /**
     * getInput
     * Initializes a scanner and retrieves input from user for handling
     * @throws Exception when input is invalid
     */
    public void getInput() throws Exception {
        Scanner in = new Scanner(System.in); // new scanner
        String line;  // line to hold user input
        System.out.println("Kevin Bacon game >");
        line = in.nextLine(); // set line to user input
        handleInputs(line); // send line to handInput method
    }

    /**
     * displayStart
     * creates pathTree and gets average separation with current center
     * displays game message
     */
    public void displayStart() {
        getPathTree(); // update path tree with current center
        // print game message
        System.out.println("Commands:");
        System.out.println("c <#>: list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation");
        System.out.println("d <low> <high>: list actors sorted by degree, with degree between low and high");
        System.out.println("i: list actors with infinite separation from the current center");
        System.out.println("p <name>: find path from <name> to current center of the universe");
        System.out.println("s <low> <high>: list actors sorted by non-infinite separation from the current center, with separation between low and high");
        System.out.println("u <name>: make <name> the center of the universe");
        System.out.println("r <#>: list best (positive number) or worst (negative) <#> of actors who would make the best center");
        System.out.println("q: quit game);");
        System.out.println("\n");
        methodU(center);

    }

    /**
     * getGraph
     * uses idToActors, idToMovies, and movieToActors map to create game graph
     */
    public void getGraph() {
        // baconGraph initialized as Adjacency Map Graph
        baconGraph = new AdjacencyMapGraph<>();
        // for each actor
        for (String actors : idToActors.values()) {
            // insert vertex
            baconGraph.insertVertex(actors);
        }
        // for each movie in movie to actors map
        for (String movie : movieToActors.keySet()) {
            // for each actor in movie to actors map
            for (String actor : movieToActors.get(movie)) {
                // for each actor in movie to actors map
                for (String actor2 : movieToActors.get(movie)) {
                    // do not pair actor with the same actor
                    if (!actor.equals(actor2)) {
                        // if graph does not have edge
                        if (!baconGraph.hasEdge(actor, actor2)) {
                            // new edge label is set of movies
                            Set<String> edgeLabel = new TreeSet<>();
                            // add movie to label
                            edgeLabel.add(movie);
                            // insert directed path from actor to actor2 with edge label
                            baconGraph.insertDirected(actor, actor2, edgeLabel);
                            // if edge already exists, add movie to edge label
                        } else baconGraph.getLabel(actor, actor2).add(movie);
                    }
                }
            }
        }
    }


    /**
     * methodD
     * creates list of sorted actors by degree, with degree between low and high inputs
     * @param low lower bound
     * @param high upper bound
     */
    public void methodD(int low, int high) {
        // new priority queue to sort actors by degree
        PriorityQueue<String> sortedActorsPQ = new PriorityQueue<>((String actor1, String actor2)
                -> (degreeMap.get(actor1) - degreeMap.get(actor2)));
        // for each actor
        for (String actor : baconGraph.vertices()) {
            sortedActorsPQ.add(actor);
        }
            List<String> sorted = new ArrayList<>(); // list to hold sorted actors
            while (!sortedActorsPQ.isEmpty()) {
                // add removed actor from PQ to sorted list
                String actor = sortedActorsPQ.remove();
                // add if matches boundary requirements
                if(degreeMap.get(actor) < high && degreeMap.get(actor) > low) sorted.add(actor + ":" + degreeMap.get(actor));
            }
            System.out.println("List of actors who have degrees between " + low + "-" + high + ": ");
            System.out.println(sorted); // print sorted list
            System.out.println("\n");
    }

    /**
     * methodI
     * prints actors with infinite separation from current center of universe
     */
    public void methodI() {
        // get set of actors not connected to current center
        Set<String> infiniteSeparation = missingVertices(baconGraph, bfs(baconGraph, center));
        System.out.println("Actors with infinite separation from " + center + ":");
        System.out.println(infiniteSeparation); // print set
        System.out.println("\n");
    }

    /**
     * methodP
     * prints the shortest path from input to current center, if path exists
     * @param name name of actor
     */
    public void methodP(String name) {
        List<String> shortestPath; // list to hold the shortest path
        pathTree = bfs(baconGraph, center); // update path tree using current center
        if (pathTree.hasVertex(name)) { // if path exists
            shortestPath = PSGraphLib.getPath(pathTree, name); // get path
            printActorInfo(shortestPath, name); // print actors information
        } else {
            System.out.println("Path does not exist."); // print if path not found
            System.out.println("\n");
        }

    }

    /**
     * getAvgSep
     * gets average separation from root to all vertices
     * @param root name of actor as root
     * @return average separation
     */
    public double getAvgSep(String root) {
        // get bfs tree with actor as root
        Graph<String, Set<String>> actorPath = bfs(baconGraph, root);
        // use Graph Lib to calculate average separation
        return PSGraphLib.averageSeparation(actorPath, root); // return separation
    }

    /**
     * getAvgSepMap
     * calculates and puts each actor's average separation into avgSepMap
     */
    public void getAvgSepMap(){
        avgSepMap = new TreeMap<>(); // initialize map
        for(String actor : baconGraph.vertices()){ // put every actor in map
            avgSepMap.put(actor, getAvgSep(actor));
        }
    }

    /**
     * methodC
     * list top (positive number) or bottom (negative) <#> centers of the universe, sorted by average separation
     * @param num number of actors
     */
    public void methodC(int num) {
        // new comparable class
        class ActorComp implements Comparator<String> {
            public int compare(String a1, String a2) {
                double c;
                if(num > 0) c = avgSepMap.get(a2) - avgSepMap.get(a1);
                else c = avgSepMap.get(a1) - avgSepMap.get(a2);
                if (c < 0) return -1;
                if (c > 0) return 1;
                return 0;
            }
        }
        // new comparable from comparator class
        ActorComp comp = new ActorComp();
        // set PQ to use comparator class
        PriorityQueue<String> unsortedCenters = new PriorityQueue<>(comp);
        // add all actors to unsorted PQ
        for (String actor : baconGraph.vertices()) {
            unsortedCenters.add(actor);  // add to PQ
        }
        String topOrBottom = "top";
        if (num < 0) topOrBottom = "bottom";
        // set count limit
        int counter = Math.abs(num);
        // new arraylist to hold actors to print
        ArrayList<String> actorsToPrint = new ArrayList<>();
        while (!unsortedCenters.isEmpty() & counter > 0) {
            // add PQ actors to sorted until PQ is empty
            String actor = unsortedCenters.poll();
            actorsToPrint.add(actor + ":" + avgSepMap.get(actor));
            counter--;
        }

        System.out.println("List of " + topOrBottom +
                " centers of the universe sorted by average separation:");
        System.out.println(actorsToPrint);
        System.out.println("\n");
    }

    /**
     * methodS
     * list actors sorted by non-infinite separation from the current center,
     * with separation between user inputs of low and high
     * @param low lower bound
     * @param high upper bound
     */
    public void methodS(int low, int high) {
        // new priority queue for sorting actors
        PriorityQueue<String> actorsToPrint = new PriorityQueue<>((String a1, String a2)
                -> (getPath(pathTree, a1).size() - getPath(pathTree, a2).size()));
        getPathTree();  // update path tree
        for (String actor : pathTree.vertices()) {  // for actors in path tree
            // if actors separation is between user input boundaries
            if (getPath(pathTree, actor).size() - 1 > low && getPath(pathTree, actor).size() - 1 < high) {
                if (!actor.equals(center)) actorsToPrint.add(actor); // if not current center, add to PQ
            }
        }
        List<String> sortedActors = new ArrayList<>();  // list to hold sorted actors
        while (!actorsToPrint.isEmpty()) {
            // add actors from PQ until PQ is empty
            String actor = actorsToPrint.remove();
            sortedActors.add(actor + ":" + getPath(pathTree, actor).size());
        }
        System.out.println("List of actors sorted by non-finite separation from " + center + "");
        System.out.println(sortedActors);  // print list
        System.out.println("\n");
    }

    /**
     * methodU
     * sets displays new the center of the universe message
     */
    public void methodU(String name) {
        center = name;
        getPathTree();
        // print new center display message
        System.out.println(center + " is now the center of the acting universe, " +
                "connected to " + pathTree.numVertices() +
                "/" + baconGraph.numVertices() + " actors with average separation " + getAvgSep(center) + "\n");
    }

    /**
     * getPathTree
     * sets pathTree to bfs tree with inputs of game graph and current center
     */
    public void getPathTree() {
        pathTree = PSGraphLib.bfs(baconGraph, center); // update path tree with current center
    }


    /**
     * getIDMap
     * reads file of ID to movie or actor and returns map of IDs to actors/movies
     * @param filename name of file
     * @return Map of ID to Actor/Movie
     * @throws IOException when file is invalid
     */
    public Map<String, String> getIDMap(String filename) throws IOException {
        Map<String, String> idToName = new TreeMap<>(); // map to hold ID, Actor/Movie
        // new buffered reader
        try (BufferedReader input = new BufferedReader(new FileReader(filename))) {
            String line;  // set line
            // while there is another line to read
            while ((line = input.readLine()) != null) {
                String[] lineSplit = line.split("\\|"); // split line
                idToName.put(lineSplit[0], lineSplit[1]); // put id, movie/name into graph
            }
        } catch (Exception e) {
            System.out.println("File not found");
            throw e;
        }
        return idToName; // return map
    }

    /**
     * getMoviesToActors
     * reads movie to actors file and uses ID maps to create moviesToActors map
     * In the map, movies are the key and set of actors in movie are value
     * @param filename name of file
     * @return map of Movies to List of Actors
     * @throws IOException if file is unable to read
     */
    public Map<String, Set<String>> getMoviesToActors(String filename) throws IOException {
        movieToActors = new TreeMap<>(); // map to hold movie and actors co-staring
        // new buffered reader
        try (BufferedReader input = new BufferedReader(new FileReader(filename))) {
            String line;  // set line as string
            // while there is another line to read
            while ((line = input.readLine()) != null) {
                String[] lineSplit = line.split("\\|");  // split line
                String movie = idToMovies.get(lineSplit[0]);  // set movie to first index
                String actor = idToActors.get(lineSplit[1]); // set actor to second index
                if (!movieToActors.containsKey(movie)) { // if movie not in map
                    Set<String> setActors = new TreeSet<>(); // create set to hold actors
                    setActors.add(actor); // add actor
                    movieToActors.put(movie, setActors); // put movie as key and set as value
                } else movieToActors.get(movie).add(actor); // add actor to value
            }
        } catch (IOException e) {
            System.out.println("File not found");
            throw e;
        }
        // close file

        return movieToActors; // return map
    }

    /**
     * printActorsInfo
     * prints path size of actor to current center, prints each edge leading to current center
     * @param path path tree of actor to current center
     * @param actor name of actor to print information
     */
    public void printActorInfo(List<String> path, String actor) {
        int actorNum = path.size() - 1; // actor path size not including actor
        System.out.println(actor + "'s number is " + actorNum); // print actor number
        // print each edge leading to actor
        for (String actor2 : path) {
            if (!actor2.equals(actor)) {
                System.out.println(actor + " appeared in " +
                        baconGraph.getLabel(actor, actor2) + " with " + actor2);
                actor = actor2; // set next actor as current actor
            }
        }
        System.out.println("\n");
    }
}
