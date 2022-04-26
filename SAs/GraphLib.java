import net.datastructures.Vertex;

import java.util.*;

/**
 * Library for graph analysis
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * 
 */
public class GraphLib {
	/**
	 * Takes a random walk from a vertex, up to a given number of steps
	 * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
	 * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
	 * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
	 * @param g		graph to walk on
	 * @param start	initial vertex (assumed to be in graph)
	 * @param steps	max number of steps
	 * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
	 * 			    null if start isn't in graph
	 */
	public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
		int randSteps = (int)(Math.random() * steps + 1);
		List<V> path = new ArrayList<>();
		V current = start;
		for(int i = 0; i <= steps; i++){
			int randNeighbor = (int)(Math.random() * g.outDegree(current));
			path.add(current);
			int x = 0;
			for(V u : g.outNeighbors(current)){
				if(x == randNeighbor){
					current = u;
				}
				else x++;
			}
		}


		return path;
	}
	
	/**
	 * Orders vertices in decreasing order by their in-degree
	 * @param g		graph
	 * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
	 */
	public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
		PriorityQueue<V> maxVert = new PriorityQueue<V>((V v1, V v2) -> (g.inDegree(v2) - g.inDegree(v1)));
		for(V u : g.vertices()){
			maxVert.add(u);
		}
		List<V> orderedList = new ArrayList<>();
		while(!maxVert.isEmpty()){
			orderedList.add(maxVert.remove());
		}
		return orderedList;
	}
}
