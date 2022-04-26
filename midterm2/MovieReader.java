import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MovieReader {
    Map<String, Set<String>> peopleToMovies; //map
    public MovieReader(String filename) throws IOException {
        peopleToMovies = new TreeMap<>();
        Set<String> moveList;
        BufferedReader input = new BufferedReader(new FileReader(filename));
        try{
            String line;
            while((line = input.readLine()) != null){
                moveList = new TreeSet<>(); // new movie list
                String[] lineSplit = line.split(":"); // split line
                String person = lineSplit[0]; // get name
                String[] movies = lineSplit[1].split(","); // get list movies
                for(String movie : movies){
                    moveList.add(movie); // add to movieList
                }
                peopleToMovies.put(person, moveList); // put person and list into map
            }
        }
        catch (Exception e){
            throw e;
        }
        finally {
            input.close(); // close file
        }

    }
    public double similarity(String personA, String personB) {
        double intersect = 0; // intersect at 0 to start
        for(String personAMovies : peopleToMovies.get(personA)){ // loop personA movies
            for(String personBMovies : peopleToMovies.get(personB)){ //loop personB movies
                if(personAMovies.equals(personBMovies)) intersect ++; // increase intersect if some movie
            }
        }
        double union = peopleToMovies.get(personA).size() + peopleToMovies.get(personB).size() - intersect;
        if(union == 0) return 0;
        return intersect / union;
        // runtime is theta(n * m) where n is number of personA movies, and m is number of personB movies
    }

    public void getRec(String person, int k){
        // need to make comparator class
        class PersonComp implements Comparator<String> {
            public int compare(String person1, String person2){
                // compare
                double c = similarity(person, person1) - similarity(person, person2);
                if(c < 0) return 1;
                if(c > 0) return -1;
                return 0;
            }
        }
        PersonComp comp = new PersonComp();
        // add all into priority queue, with comp in constructor, for sorting
        PriorityQueue<String> unsortedList = new PriorityQueue<String>(comp);
        for(String nameToSort : peopleToMovies.keySet()){
            if(!nameToSort.equals(person)){
                unsortedList.add(nameToSort);
            }
        }
        ArrayList<String> sorted = new ArrayList<>();
        while(sorted.size() < k){ // only get top k people
            sorted.add(unsortedList.poll());
        }
        System.out.println(sorted);
        ArrayList<String> moviesToWatch = new ArrayList<>(); // movies to print
        for(String similarPerson : sorted){ // loop through people
            for(String movie : peopleToMovies.get(similarPerson)){ // loop through their movies
                // if movie not already printed and person has not watched
                if(!moviesToWatch.contains(movie) && !peopleToMovies.get(person).contains(movie)){
                    moviesToWatch.add(movie); // add to movies to print to mark as printed
                    System.out.println(movie); // print movie
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MovieReader test = new MovieReader("midterm2/movies.txt");
        System.out.println(test.peopleToMovies);
        test.getRec("Bob", 3);
    }
}
