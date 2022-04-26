import net.datastructures.Graph;
import net.datastructures.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BusRoute {
    private static class RouteNode implements Comparable<RouteNode> {
        private String name;
        private int distance;
        private RouteNode predecessor;

        private RouteNode(String name) {
            this.name = name;
            distance = Integer.MAX_VALUE; //init to infinity
            predecessor = null;
        }

        public int compareTo(RouteNode q2) {
            return distance - q2.distance;
        }

        public String getName() {
            return name;
        }

        public int getDistance() {
            return distance;
        }

        public RouteNode getPredecessor() {
            return predecessor;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public void setPredecessor(RouteNode pred) {
            this.predecessor = pred;
        }

        /**
         * relax
         * updates the best defined path by relaxing edges
         * @param first initial node
         * @param second next node
         * @param graph graph in which traversing
         * @param pen penalty or weight of edge
         */
        public static void relax(RouteNode first, RouteNode second, AdjacencyMapGraph<RouteNode, Integer> graph, Integer pen) {
            // if found a better path
            if (first.getDistance() + graph.getLabel(first, second) + pen < second.getDistance()) {
                // update new distance
                second.setDistance(first.getDistance() + graph.getLabel(first, second) + pen);
                // update predecessor
                second.setPredecessor(first);
            }

        }

        /**
         * findRoute
         * calculates the best path given an edge penalty
         * @param graph graph to traverse
         * @param pen penalty
         * @param start starting node
         * @param finish goal node
         */
        public static void findRoute(AdjacencyMapGraph<RouteNode, Integer> graph, int pen, RouteNode start, RouteNode finish) {
            // min priority queue
            ArrayListMinPriorityQueue<RouteNode> nextRoute = new ArrayListMinPriorityQueue<>();

            // set all edge weights to infinity and add to next route
            for (RouteNode stop : graph.vertices()) {
                stop.setDistance(Integer.MAX_VALUE);
                nextRoute.insert(stop);
                stop.setPredecessor(null);
            }

            // initialize start distance to 0
            start.setDistance(0);
            // while there is another route
            while ((!nextRoute.isEmpty())) {
                // extra min
                RouteNode curr = nextRoute.extractMin();
                // iterate through each next node
                for (RouteNode nextStop : graph.outNeighbors(curr)) {
                    // relax edge weights
                    relax(curr, nextStop, graph, pen);
                }
            }
            // list to hold each route (reverse)
            List<RouteNode> taken = new ArrayList<>();

            // set curr to goal
            RouteNode curr = finish;
            // while curr does not equal null
            while (curr != null) {
                // add to taken
                taken.add(curr);
                // get previous node
                curr = curr.getPredecessor();
            }

            // string to print route
            String res = "";
            // iterate through taken in reverse
            for(int i = taken.size()-1 ; i >=0; i-- ){
                // add stop to res
                res += taken.get(i).getName() + " ";

            }

            // print res
            System.out.println(res);

        }


        public static void main(String[] args) {
            AdjacencyMapGraph<RouteNode, Integer> busMap =
                    new AdjacencyMapGraph<RouteNode, Integer>();
            int transferPenalty = 0;

            //create RouteNodes for graph
            RouteNode Home = new RouteNode("Home");
            RouteNode B = new RouteNode("B");
            RouteNode C = new RouteNode("C");
            RouteNode D = new RouteNode("D");
            RouteNode E = new RouteNode("E");
            RouteNode Work = new RouteNode("Work");

            //add RouteNodes to graph
            busMap.insertVertex(Home);
            busMap.insertVertex(B);
            busMap.insertVertex(C);
            busMap.insertVertex(D);
            busMap.insertVertex(E);
            busMap.insertVertex(Work);

            //add edges between RouteNodes with distance as edge label
            busMap.insertUndirected(Home, B, 3);
            busMap.insertUndirected(Home, C, 5);
            busMap.insertUndirected(B, D, 2);
            busMap.insertUndirected(D, E, 1);
            busMap.insertUndirected(E, Work, 1);
            busMap.insertUndirected(C, Work, 12);
            busMap.insertUndirected(D, Work, 4);

            //TODO complete this method
            findRoute(busMap, transferPenalty, Home, Work);
        }
    }
}