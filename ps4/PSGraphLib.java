/*
 * Name: Mikey Mauricio
 * Date: 10/28/2021
 * Purpose: Library Graph class with BFS-related methods
 * PS4
 */

import java.util.*;

public class PSGraphLib {

    /**
     * bfs
     * Method performs a breadth first search given a graph and a starting vertex.
     * @param g graph to traverse
     * @param start starting vertex
     * @param <V> Vertex of type generic
     * @param <E> Edge of type generic
     * @return returns a path tree used to find the shortest path
     */
    public static <V, E> Graph<V, E> bfs(Graph<V, E> g, V start) {
        // new bfs tree
        Graph <V,E> bfsTree = new AdjacencyMapGraph<>();
        // set to mark vertices as visited
        Set<V> visited = new HashSet<>();
        // queue to mark vertices to visit
        Queue<V> toVisit = new LinkedList<>();
        toVisit.add(start); // enqueue start
        visited.add(start); // add start to visited
        bfsTree.insertVertex(start); // insert into bfs tree

        while(!toVisit.isEmpty()){  // until no more vertices
            V u = toVisit.remove(); // dequeue to get next vertex
            for (V v : g.outNeighbors(u)){  // loop through out-neighbors
                if(!visited.contains(v)){ // if not visited
                    visited.add(v); // mark as visited
                    toVisit.add(v); // add to queue
                    bfsTree.insertVertex(v); // insert into tree
                    // insert new edge
                    bfsTree.insertDirected(v, u, g.getLabel(u, v));
                }
            }
        }
        return bfsTree; // return tree
    }

    /**
     * getPath
     * uses path tree from bfs in order to return shortest path from start to goal vertices
     * @param tree tree to traverse
     * @param v goal vertex
     * @param <V> Vertex of type generic
     * @param <E> Edge of type generic
     * @return returns a list of vertices in order representing a path
     */
    public static <V, E> List<V> getPath(Graph<V, E> tree, V v) {
        List<V> shortestPath = new ArrayList<>(); // initialize path list
        V current = v; // set current to root
        shortestPath.add(current); // add it to path list

        while(tree.outDegree(current) > 0){ // while it has out neighbors
            for(V u : tree.outNeighbors(current)){ // iterate through them
                shortestPath.add(u); // add to shorted path
                current = u; // set current to u
            }
        }
        return shortestPath; // return shortest path
    }

    /**
     * missingVertices
     * returns a set of vertices that are not contained in the subgraph provided
     * @param graph graph which holds all vertices
     * @param subgraph subgraph of graph provided
     * @param <V> Vertex of type generic
     * @param <E> Edge of type generic
     * @return returns set of vertices not present in subgraph
     */
    public static <V, E> Set<V> missingVertices(Graph<V, E> graph, Graph<V, E> subgraph) {
        Set<V> missing = new HashSet<>(); // hashset to hold missing vertices
        for(V v : graph.vertices()){ // iterate through each vertex
            if(!subgraph.hasVertex(v)){ // if subgraph does not contain
                missing.add(v); // add to missing set
            }
        }
        return missing; // return set
    }

    /**
     * averageSeparation
     * calculates the average separation between vertices and root provided
     * @param tree path tree with root as center
     * @param root root to calculate separation
     * @param <V> Vertex of type generic
     * @param <E> Edge of type generic
     * @return double value holding average separation
     */
    public static <V, E> double averageSeparation(Graph<V, E> tree, V root) {
        int sum = averageSepHelper(tree, root, 0); // call helper function
        return (double)(sum)/(double)(tree.numVertices()-1); // subtract 1 due to not including root
    }

    public static <V,E> int averageSepHelper(Graph<V, E> tree, V current, int pathLength){
        int sum = pathLength; // set sum to path length
        if(tree.inDegree(current) > 0){ // if tree has in neighbors
            for(V u : tree.inNeighbors(current)){ // iterate through each
                // add to sum for each neighbor
                sum += averageSepHelper(tree, u, pathLength+1);
            }
        }
        return sum; // return sum
    }


}
