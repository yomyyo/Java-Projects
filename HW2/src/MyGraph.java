import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Andrew Nguyen & Sam Tang
 * @since 02 Feb 2017
 *
 * CSCI 241, WWU - Professor Ahmed, Winter 2017
 */
public class MyGraph {

    //HashMap appears to be the best case.
    private HashMap<String, Vertex> graph;

    /**
     * Constructor for MyGraph
     * @param vertexFileName filename of nodes
     * @param edgeFileName filename of edges (start, end, distance, time, cost)
     */
    public MyGraph(String vertexFileName, String edgeFileName) {
        graph = new HashMap<>();

        //try-catch because readInputData() throws a FNF Exception
        try {
            loadVertices(vertexFileName);
            loadEdges(edgeFileName);
        } catch (FileNotFoundException err) {
            System.out.print(err + "\n^^Call to MyGraph constructor.");
        }
    }

    /**
     * Loads the vertices from vertex.txts
     * @param vertexFileName
     * @throws FileNotFoundException
     */
    private void loadVertices(String vertexFileName) throws FileNotFoundException {
        //Each node also contains a method, getData() that returns a String
        //Of their start, end, distance, cost, time. Via graph.get(key).getData()
        //Creation of all nodes. Nodes are just names for now, they contain null edges.
        Scanner sc = new Scanner(new File(vertexFileName));
        String start;

        while (sc.hasNext()) {
            start = sc.nextLine();
            graph.put(start, new Vertex(start));
        }
    }

    /**
     * Loads the edges from edges.txt
     * @param edgeFileName
     * @throws FileNotFoundException
     */
    private void loadEdges(String edgeFileName) throws FileNotFoundException {
        //Read in the edges file. Edges are added to nodes/
        Scanner sc = new Scanner(new File(edgeFileName));
        String start;
        String end; int distance, time, cost;

        while (sc.hasNext()) {
            start = sc.nextLine();
            end = sc.nextLine();
            distance = Integer.parseInt(sc.nextLine());
            time = Integer.parseInt(sc.nextLine());
            cost = Integer.parseInt(sc.nextLine());

            //edges are added to nodes via addEdge(start, end, d, t, c);
            graph.get(start).addEdge(graph.get(start), graph.get(end), distance, time, cost);
        }
    }

    /**
     * Override of toString().
     * @return Returns a big, multi-line string to be printed that shows all info in this format:
     * NodeName: [Start --> End (distance, time, cost), ...]
     */
    public String toString() {
        String result = "";
        Set<String> keys = graph.keySet();
        for (String str : keys) {
//            System.out.println(graph.get(str));
            result += (graph.get(str).toString() + "\n");
        }
        return (result);
    }


    public HashMap<String, Vertex> getGraph() {
        return graph;
    }

    /**
     * Extracts a vertex with a minimum determined value from an ArrayList
     * @param vList An input ArrayList of Vertices
     * @return Returns the extracted vertex
     */
    private Vertex extractMin(ArrayList<Vertex> vList){
        //grabs the Vertex with the minimum value in an ArrayList and returns it
        
        Vertex min = new Vertex("min");
        min.value = Integer.MAX_VALUE;
        for(Vertex v : vList){
            if(v.value < min.value){
                min = v;
                return min;
            }
            min = v;
        }
        return min;
    }

    /*Uses start as the starting vertex to explore using djikstra's alg and
    respective value. Returns an ArrayList containing each vertex in the graph in order 
    of when it was explored and its value as their value for the shortest path.*/
    /**
     * Usage of Djikstra's Algorithm to determine the shortest route from a starting vertex to all other vertices
     * @param start Starting vertex as a String
     * @param routeParam 1, 2, 3, or 4 to determine distance, time, cost, or all
     * @return Returns an ArrayList of the value costs from the starting vertex to every other vertex
     */
    public ArrayList<Vertex> djikstra(Vertex start, int routeParam) {
        ArrayList<Vertex> jumps = new ArrayList<>();
        ArrayList<Vertex> vertexList = new ArrayList<>();
        ArrayList<Vertex> route = new ArrayList<>();

        Set<String> keys = graph.keySet();

        for (String str : keys) {
            vertexList.add(graph.get(str));
        }

        for(Vertex v : vertexList){
            v.value = Integer.MAX_VALUE;
        }

        start.value = 0;
        while(!vertexList.isEmpty()){
            Vertex u = extractMin(vertexList);
            vertexList.remove(u);
            route.add(u);
//            System.out.println(vertexList);
//            System.out.println(route);
//            System.out.println(jumps);

            switch (routeParam) {
                case 1:
                    for(Edge e : u.getEdges()){
                        if(graph.get(e.getEnd()).value > u.value + e.getDistance()) {
                            graph.get(e.getEnd()).value = u.value + e.getDistance();
                            graph.get(e.getEnd()).prev = u;
                        }
                    }
                    break;
                case 2:
                    for(Edge e : u.getEdges()) {
                        if(graph.get(e.getEnd()).value > u.value + e.getTime()){
                            graph.get(e.getEnd()).value = u.value + e.getTime();
                            graph.get(e.getEnd()).prev = u;
                        }
                    }
                    break;
                case 3:
                    for(Edge e : u.getEdges()) {
                        if(graph.get(e.getEnd()).value > u.value + e.getCost()){
                            graph.get(e.getEnd()).value = u.value + e.getCost();
                            graph.get(e.getEnd()).prev = u;
                        }
                    }
                    break;
            }
        }
        return route;

    }

    /**
     * Functions as a singular route-finding function. Depending on the integer parameter the user inputs,
     * it can find the shortest route, the fastest route, or the cheapest route.
     * @param startVertex Starting vertex as a String
     * @param endVertex Edning vertex as a String
     * @param routeParam 1, 2, 3, or 4 to determine distance, time, cost, or all
     * @return Returns an integer of the value of the route
     */
    public int findRoute(String startVertex, String endVertex, int routeParam) {
        ArrayList<Vertex> route = djikstra(graph.get(startVertex), routeParam);
        for(Vertex v : route){
            if(v.getName().equals(endVertex)){
                return v.value;
            }
        }
        return Integer.MAX_VALUE;
    }
}

