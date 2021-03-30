package wmin.algo.maxflow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Run {

    public static ArrayList<String> graphDetails = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        /*
        String dataFileName = "./benchmark/ladder_9.txt";
        String dataFileName = "./benchmark/bridge_1.txt";
         */
        String dataFileName = "./benchmark/cw-flow.txt";

        int[][] graph = generateGraph(dataFileName);

        FordFulkersonBFS m = new FordFulkersonBFS(); //create the instance of FordFulkersonBFS class

        int valueOfS = 0; // To store the source node
        int valueOfT = Integer.parseInt(graphDetails.get(0))-1; //To store the sink node
        System.out.println("MAXIMUM_FLOW : " + m.maxFlowFind(graph, valueOfS, valueOfT));
    }

    private static int[][] generateGraph(String dataFileName) throws IOException {

        readFile(dataFileName);

        int numOfVertex = Integer.parseInt(graphDetails.get(0));
        int[][] matrix = new int[numOfVertex][numOfVertex];
        ArrayList<Edge> edgeList = new ArrayList<Edge>();

        for (int i = 1; i < graphDetails.size(); i++) {
            String line1 = graphDetails.get(i);
            String[] edgeRelations = line1.split(" ", 3);
            edgeList.add(new Edge(
                    Integer.parseInt(edgeRelations[0]),
                    Integer.parseInt(edgeRelations[1]),
                    Integer.parseInt(edgeRelations[2])
            ));
        }

        for (int i = 0; i < edgeList.size(); i++ ) {
            Edge currentEdge = edgeList.get(i);
            int startVertex = currentEdge.startVertex;
            int endVertex = currentEdge.endVertex;
            int weight = currentEdge.weight;
            matrix[startVertex][endVertex] = weight;
        }
        return matrix;
    }

    private static List<String> readFile(String filename) throws IOException {

        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String readData = bufferedReader.readLine();
        int numberOfVertices = Integer.parseInt(readData);
        graphDetails.add(readData);

        while ((readData = bufferedReader.readLine()) != null) {
            graphDetails.add(readData);
        }
        return graphDetails;
    }
}

/**
 * Support Class for generateGraph() method
 * */
class Edge {
    int startVertex;
    int endVertex;
    int weight;

    public Edge(int startVertex, int endVertex, int weight) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.weight = weight;
    }
}