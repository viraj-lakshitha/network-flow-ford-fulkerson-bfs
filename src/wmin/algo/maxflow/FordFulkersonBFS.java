package wmin.algo.maxflow;

import java.util.*;
import java.lang.*;
import java.util.LinkedList;

public class FordFulkersonBFS {

    private int count = 0;//to get the path number of augmented path

    private static final int vertices = Integer.parseInt(Run.graphDetails.get(0)); //Number of vertices in graph
    static ArrayList<Integer> flowList = new ArrayList<>(); // to store the flow of each path

    private boolean breadthFirstSearch(int[][] rGraph, int s, int t, int[] parent) {

        // Create an array of visits and mark all vertices as not visited
        boolean[] visited = new boolean[FordFulkersonBFS.vertices];
        for (int i = 0; i < vertices; ++i)
            visited[i] = false;

        // Create a queue to store nodes(vertices)
        LinkedList<Integer> pathQueue = new LinkedList<>();
        pathQueue.add(s);  //add(enqueue) source vertex S (starting node.)
        visited[s] = true; //mark S(source node) vertex as visited
        parent[s] = -1; //add -1 as the value to the path

        // Standard Breadth First Search Loop
        count++; //to get the path number of augmented path
//        System.out.print("Augmented path "+ count + " : " +s); //print the first node of thr path

        while (pathQueue.size() != 0) {
            int n = pathQueue.poll();

            for (int m = 0; m < vertices; m++) {
                if (!visited[m] && rGraph[n][m] > 0) {
                    pathQueue.add(m);
                    parent[m] = n;
                    visited[m] = true;

                    if (visited[m]){
//                        System.out.print(" "+m); //print the path
                    }
                }
            }
        }
        // If we reached sink t (the end point) in Breadth First Search starting from source, then return true, else false
        return (visited[t]);
    }

    // Returns the maximum flow from s to t in the given graph using Ford Fulkerson Algorithm
    int maxFlowFind(int[][] graph, int s, int t) {
        int u, v;

        // Residual graph where residualGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If residualGraph[i][j] is 0, then there is
        // not)
        int[][] residualGraph = new int[vertices][vertices]; //create residual graph using number of vertices

        for (u = 0; u < vertices; u++)
            for (v = 0; v < vertices; v++)
                residualGraph[u][v] = graph[u][v]; //fill residual graph with the original graph data

        //To store path that is determined by Breadth First Search
        int[] parent = new int[vertices];

        int max_flow = 0; // Initial flow

        //calculate flow along the augmented path
        while (breadthFirstSearch(residualGraph, s, t, parent)) {
//            System.out.println("parent :"+ Arrays.toString(parent));
            //Find max flow from the path that is determined by the Breadth First Search
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);

            }
            // updating residual capacities of the edges and reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= path_flow; //forward
                residualGraph[v][u] += path_flow;  //reverse
            }

            flowList.add(path_flow); // store the flow of augmented path to the flowList array

//            System.out.println("\tFlow = " + path_flow); //print the flow of each path

            max_flow += path_flow; //add path flow to get max flow
        }
        // Return the maximum fow to the graph
        return max_flow;
    }
}
