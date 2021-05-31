package cwk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MaxFlowAlgorithm
{
    static final int vertices = reader().getVertex();

    public static void main(String[] args)
    {   double t1 = System.currentTimeMillis();
        System.out.println("Number of vertices : " + vertices);
        MaxFlowAlgorithm maxFlow = new MaxFlowAlgorithm();
        System.out.println( maxFlow.fordFulkerson(reader().getAdjMatrix(), 0, reader().getVertex() - 1) + " is the Maximum flow of given input file.");

        double t2 = System.currentTimeMillis();
        double T = t2 - t1;

        System.out.println("Time " + T + " miliseconds");//--------------------
    }
    boolean bfs(int Graph[][], int s, int t, int parent[])
    {
        boolean visited[] = new boolean[vertices]; //checking the nodes
        for (int i = 0; i < vertices; ++i)
        {
            visited[i] = false; //initialling give false value to all nodes
        }

        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        linkedList.add(s);          // add node to linkedlist
        visited[s] = true; // after node visited give true value
        parent[s] = -1;


        while (linkedList.size() != 0)
        {
            int u = linkedList.poll();

            for (int v = 0; v < vertices; v++)
            {
                if (visited[v] == false && Graph[u][v] > 0)
                {
                    if (v == t)
                    {
                        parent[v] = u;
                        return true;
                    }
                    linkedList.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }


    int fordFulkerson(int graph[][], int s, int t)
    {   int u;
        int v;

        int rGraph[][] = new int[vertices][vertices];  // Creating graph

        for (u = 0; u < vertices; u++)
        {
            for (v = 0; v < vertices; v++)
            {
                rGraph[u][v] = graph[u][v];
            }
        }
        int parent[] = new int[vertices];
        int max_flow = 0;

        while (bfs(rGraph, s, t, parent))
        {   int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            for (v = t; v != s; v = parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }
            max_flow += path_flow;
        }
        return max_flow;
    }

    public static MatrixGraph reader()
    {
        ArrayList<ArrayList<Integer>> dataList = new ArrayList<>();
        try {
            File dataFile = new File("viva.txt");
            Scanner scan = new Scanner(dataFile);

            while (scan.hasNextLine())
            {
                String data = scan.nextLine();
                String[] temp = data.split(" ");
                ArrayList<Integer> store = new ArrayList<>();
                for (String string : temp)
                {
                    store.add(Integer.parseInt(string));
                }
                dataList.add(store);
            }
            scan.close();

        } catch (FileNotFoundException e)
        {
            System.out.println("Cannot fine input file.");
            e.printStackTrace();
        }

        MatrixGraph graph = null;

        for (ArrayList<Integer> arraylist2 : dataList)
        {   if (dataList.indexOf(arraylist2) == 0)
            {   graph = new MatrixGraph(arraylist2.get(0));
            } else
                {graph.addEdge(arraylist2.get(0), arraylist2.get(1), arraylist2.get(2));
                }
        }
        //graph.printGraph();
        return graph;
    }

}

class MatrixGraph
{   private int nodes;
    private int adjMatrix[][];
    public MatrixGraph(int nodes)
    {
        this.nodes = nodes;
        adjMatrix = new int[nodes][nodes];
    }

    public void addEdge(int startNode, int endNode, int capacity)
    {
        this.adjMatrix[startNode][endNode] = capacity;
    }

    public void printGraph(){
        for(int i = 0; i < nodes;i++ )
        {
            for(int j = 0; j < nodes;j++)
            {
                System.out.print(adjMatrix[i][j]);
            }
            System.out.println("");
        }
    }

    public int[][] getAdjMatrix()
    {
        return adjMatrix;
    }
    public void setAdjMatrix(int[][] adjMatrix)
    {
        this.adjMatrix = adjMatrix;
    }
    public int getVertex()
    {
        return nodes;
    }
    public void setNodes(int nodes)
    {
        this.nodes = nodes;
    }
}

