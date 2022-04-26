import java.lang.reflect.Array;
import java.util.*;

public class Course {
    String courseName; // course name
    ArrayList<Course> prereqs; // list of pre reqs

    public Course(String name) {
        this.courseName = name; // set name
        this.prereqs = new ArrayList<>(); // initialize list

    }

    public void addPrereq(Course name) {
        prereqs.add(name); // add course

    }

    public ArrayList<Course> getPrereqs() {
        return prereqs; // return list
    }

    public String toString() {
        return courseName; // return name

    }

    public static AdjacencyMapGraph<Course, String> buildGraph(List<Course> courseList) {
        // new graph
        AdjacencyMapGraph<Course, String> courseGraph = new AdjacencyMapGraph<>();
        // iterate through courses
        for (Course course : courseList) {
            // add vertex if not in already
            if (!courseGraph.hasVertex(course)) courseGraph.insertVertex(course);
            // add edge for each prereq
            for (Course pre : course.getPrereqs()) {
                courseGraph.insertDirected(pre, course, " prereq for");
            }
        }

        return courseGraph;
    }

    public static void orderedCourses(AdjacencyMapGraph<Course, String> courseGraph){
        Stack<Course> toTake = new Stack<>(); // new stack
        ArrayList<Course> taken = new ArrayList<>(); // courses to print
        Map<Course, Integer> inDegs = new HashMap<>(); // map of degrees
        for(Course course : courseGraph.vertices()){ // iterate through each course
            // put into map
            inDegs.put(course, course.getPrereqs().size());
        }
        // iterate through each course
        for(Course x : courseGraph.vertices()){
            // add all start
            if(courseGraph.inDegree(x) == 0){
                toTake.push(x);
            }
        }

        // while toTake is not empty
        while(!toTake.isEmpty()){
            // get next
            Course curr = toTake.pop();
            // add to taken
            taken.add(curr);
            // iterate through each next class
            for(Course next : courseGraph.outNeighbors(curr)){
                // update through degrees
                inDegs.put(next, inDegs.get(next) - 1);
                // if 0, add to toTake
                if(inDegs.get(next) == 0) toTake.push(next);
            }
        }
        System.out.println(taken); // print list
    }

    public static void main(String[] args) {
        Course cs1 = new Course("CS1");
        Course cs2 = new Course("CS2");
        cs2.addPrereq(cs1);

        Course cs3 = new Course("CS3");
        cs3.addPrereq(cs1);
        cs3.addPrereq(cs2);

        Course cs4 = new Course("CS4");
        cs4.addPrereq(cs2);
        cs4.addPrereq(cs3);

        Course cs5 = new Course("CS5");
        cs5.addPrereq(cs2);

        Course cs6 = new Course("CS6");
        cs6.addPrereq(cs3);
        cs6.addPrereq(cs5);

        List<Course> courseList = new ArrayList<Course>();
        courseList.add(cs1);
        courseList.add(cs2);
        courseList.add(cs3);
        courseList.add(cs4);
        courseList.add(cs5);
        courseList.add(cs6);
        System.out.println(buildGraph(courseList));
        orderedCourses(buildGraph(courseList));


    }
}
