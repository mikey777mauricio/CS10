public class SA7 extends GraphLib{
    public static void main(String[] args) {
        Graph<String, String> relationships = new AdjacencyMapGraph<>();
        relationships.insertVertex("A");
        relationships.insertVertex("B");
        relationships.insertVertex("C");
        relationships.insertVertex("D");
        relationships.insertVertex("E");

        relationships.insertDirected("A", "B", "IDK");
        relationships.insertDirected("A", "C", "IDK");
        relationships.insertDirected("A", "D", "IDK");
        relationships.insertDirected("A", "E", "IDK");

        relationships.insertDirected("B", "A", "IDK");
        relationships.insertDirected("B", "C", "IDK");
        relationships.insertDirected("C", "A", "IDK");
        relationships.insertDirected("C", "B", "IDK");

        relationships.insertDirected("C", "D", "IDK");
        relationships.insertDirected("E", "B", "IDK");
        relationships.insertDirected("E", "C", "IDK");
        System.out.println(relationships);
        System.out.println(randomWalk(relationships, "A", 3));
        System.out.println(verticesByInDegree(relationships));
    }
}
