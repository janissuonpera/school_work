/*
 * Janis Suonperä
 * 428258
 * janis.suonpera@tuni.fi
 *
 * Tira harjoitustyö
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Float;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class T2018 {

    private String line;
    private float x[];
    private float y[];
    private Node[] nodes;

    private void readInput() {

        x = new float[400];
        y = new float[400];
        nodes = new Node[400];
        try {
            BufferedReader br = new BufferedReader(new FileReader("Tdata.txt"));

            for (int i = 0; i < 400; i++) {
                line = br.readLine();
                String[] values = line.split(",");
                x[i] = Float.parseFloat(values[0]);
                y[i] = Float.parseFloat(values[1]);
                System.out.print(x[i] + " , " + y[i] + "\n");

                Node node = new Node(x[i], y[i]);
                nodes[i] = node;
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    private void writeOutput() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
            for (int i = 0; i < 10; i++) {
                bw.write(Float.toString(x[i]));
                bw.write(",");
                bw.write(Float.toString(y[i]));
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        System.out.println("Tiedosto kirjoitettu");
    }

    private void add_neighbors() {
        for (int i = 0; i < nodes.length; i++) {

            //I will store the index of the closest node to closest_node[0]
            //and its distance to closest_node[1]
            Object[] closest_node = new Object[2];
            closest_node[0] = -1;
            closest_node[1] = Double.MAX_VALUE;

            for (int j = 0; j < x.length; j++) {
                double distance = Math.sqrt(Math.pow(nodes[i].x() - x[j], 2) + Math.pow(nodes[i].y() - y[j], 2));
                
                //If distance is smaller than the previous smallest distance, nodes are not the same and second node is not already a
                //neighbor of the first, add it as a neighbor
                if (distance < (double) closest_node[1] && j != i && nodes[i].not_in_neighbors(nodes[j])) {
                    closest_node[0] = j;
                    closest_node[1] = distance;
                }
            }
            if ((int) closest_node[0] != -1 && (double) closest_node[1] != Double.MAX_VALUE) {
                nodes[i].add_neighbor(nodes[(int) closest_node[0]]);
            }
        }

    }

    private void print_nodes() {
        for (int i = 0; i < 10; i++) {
            if (nodes[i] != null) {
                String node_info = "Node: " + nodes[i].x() + " , " + nodes[i].y() + " and closest nodes: ";

                Node[] neighbors = nodes[i].neighbors();
                for (int j = 0; j < neighbors.length; j++) {
                    node_info += neighbors[j].x() + " , " + neighbors[j].y() + "    ";
                }

                System.out.println(node_info);
            }
        }
    }

    private void breadth_first_search(String filename) {
        //Next_subgraph takes the first unvisited node, in this case always
        //nodes[0] which has coordinates of x[0], y[0]
        Node start = next_subgraph();
        Queue<Node> queue = new LinkedList<Node>();
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            int count = 0;
            while (start != null) {
                //As the graph is mostly likely not connected, this will print
                //a breath-first search for all subgraphs in the graph and save them
                //in a txt-file.
                bw.write("BFS for subgraph " + count);
                bw.newLine();
                queue.add(start);
                while (!queue.isEmpty()) {
                    Node current = queue.poll();
                    if (current != null && current.visited() == false) {
                        current.visited(true);
                        //System.out.println(current.x() + " , " + current.y());

                        bw.write(Float.toString(current.x()));
                        bw.write(",");
                        bw.write(Float.toString(current.y()));
                        bw.newLine();

                        for (int i = 0; i < current.neighbors().length; i++) {
                            if (current.neighbors()[i] != null && current.neighbors()[i].visited() == false) {
                                queue.add(current.neighbors()[i]);
                            }
                        }
                    }
                }
                bw.newLine();
                count++;
                start = next_subgraph();
            }
            bw.close();
            System.out.println(filename + " created");
            reset_visited();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private void depth_first_search(String filename) {
        LinkedList<Node> results = new LinkedList<Node>();
        //Next_subgraph takes the first unvisited node, in most cases
        //nodes[0] which has coordinates of x[0], y[0]
        Node start = next_subgraph();

        while (start != null) {
            //Adding null values as a bookmark where a new subgraph starts
            results.add(null);
            dps_2(start, results);
            start = next_subgraph();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            int count = 0;
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i) == null) {
                    if (count != 0) {
                        bw.newLine();
                    }
                    //As the graph is mostly likely not connected, this will print
                    //a breath-first search for all subgraphs in the graph and save them
                    //in a txt-file.
                    bw.write("DFS for subgraph " + count);
                    bw.newLine();
                    count++;
                } else {
                    bw.write(results.get(i).x() + "," + results.get(i).y());
                    bw.newLine();
                }
            }
            bw.close();
            System.out.println(filename + " created");
            reset_visited();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    //Utility method for depth-first search. Meant to help with recursion.
    //The first DFS method shares a linkedlist with this method which adds
    //nodes there recursively
    private void dps_2(Node start, LinkedList<Node> results) {

        Stack<Node> stack = new Stack<Node>();
        stack.add(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current != null && current.visited() == false) {
                //System.out.println(current.x() + " , " + current.y());
                current.visited(true);
                //If node isnt in the results list already
                if (results.indexOf(current) == -1) {
                    results.add(current);
                }
                for (Node neighbor : current.neighbors()) {
                    if (neighbor != null && neighbor.visited() == false) {
                        dps_2(neighbor, results);
                    }
                }

            }
        }
    }

    //Returns the first node of the next unvisited subgraph
    private Node next_subgraph() {
        for (Node node : nodes) {
            if (node != null) {
                if (node.visited() == false) {
                    return node;
                }
            }
        }
        return null;
    }

    //Set visited for all nodes as false
    private void reset_visited() {
        for (Node node : nodes) {
            if (node != null) {
                node.visited(false);
            }
        }
    }

    //Counts how many neighbors a node has and how many it is a neighbor for.
    private void count_degrees() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Degrees.txt"));
            for (Node n : nodes) {
                int out = n.neighbors().length;
                int in = 0;
                for (Node n2 : nodes) {
                    if (n2 != n && n2.not_in_neighbors(n) == false) {
                        in++;
                    }
                }
                bw.write(n.x() + " , " + n.y() + " : out:" + out + " in:" + in);
                bw.newLine();
                //System.out.println(n.x() + " , " + n.y() + "  :  out:" + out + "   in:" + in);
            }
            bw.close();
            System.out.println("Degrees.txt created");
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    //Removes a node by making its index null and removing it as a neighbor
    //from all other nodes
    private void remove_node(int remove) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null && nodes[remove] != null) {
                nodes[i].remove_neighbor(nodes[remove]);
            }
        }
        nodes[remove] = null;
        breadth_first_search("DIM.txt");
    }

    //Adds neighbors to nodes until a depth-first search is able to reach every
    //node in the graph. My DFS adds connected nodes to the linked list, so the graph
    //is not connected until it reaches size of 400.
    private void make_complete() {
        LinkedList<Node> results = new LinkedList<Node>();
        while (results.size() < 400) {
            results = new LinkedList<Node>();
            dps_2(nodes[0], results);
            add_neighbors();
            reset_visited();
        }
        reset_visited();
        depth_first_search("COMP.txt");
    }

    //Not implemented.
    private void min_span() {
//        LinkedList<W_S_D> wsd = new LinkedList();
//
//        for (int i = 0; i < nodes.length; i++) {
//            Node[] neighbors = nodes[i].neighbors();
//            for (int j = 0; j < neighbors.length; j++) {
//                if (neighbors[j] != null) {
//                    double distance = Math.sqrt(Math.pow(nodes[i].x() - neighbors[j].x(), 2) + Math.pow(nodes[i].y() - neighbors[j].y(), 2));
//                    W_S_D edge = new W_S_D(distance, nodes[i], neighbors[j]);
//                    wsd.add(edge);
//                }
//            }
//        }
//
//        wsd.sort(Comparator.comparing(W_S_D::weight));
//
//        LinkedList<Node> result = new LinkedList();
//
//        for (int i = 0; i < wsd.size(); i++) {
//            W_S_D current = wsd.get(i);
//
//            if (result.indexOf(current.src()) != -1 && result.indexOf(current.dest()) == -1 && !isCyclic(result, current.dest())) {
//                result.add(current.dest());
//            } else if (result.indexOf(current.src()) == -1 && result.indexOf(current.dest()) == -1) {
//                result.add(current.src());
//                result.add(current.dest());
//            } else if (result.indexOf(current.src()) == -1 && result.indexOf(current.dest()) != -1 && !isCyclic(result, current.src())) {
//                result.add(current.src());
//            }
//        }
//
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter("MSP.txt"));
//            for(Node current : result){
//                bw.write(Float.toString(current.x()));
//                bw.write(",");
//                bw.write(Float.toString(current.y()));
//                bw.newLine();
//            }
//            bw.newLine();
//            bw.close();
//            System.out.println("MSP.txt" + " created");
//            reset_visited();
//        } catch (IOException e) {
//            System.err.format("IOException: %s%n", e);
//        }
    }
    
    //Not implemented
   /* private boolean isCyclic(LinkedList<Node> result, Node dest) {
        Node[] neigh = dest.neighbors();
        Boolean cycle = false;

        Boolean test = false;
        for (Node n : neigh) {
            if (n.visited()) {
                test = true;
            }
        }
        if (test == false) {
            return false;
        }

        Stack<Node> stack = new Stack<Node>();
        stack.add(dest);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            current.visited(true);

            Node[] neighs = current.neighbors();
            for (Node n : neighs) {
                if (n.visited() == true) {
                    if (n == dest) {
                        return true;
                    }
                    stack.add(n);
                }
            }

        }

        return false;
    }*/

    public static void main(String[] args) {
        T2018 ht = new T2018();
        ht.readInput();
        ht.writeOutput();

        //Add two closest neighbors to the nodes.
        ht.add_neighbors();
        ht.add_neighbors();

        //Prints the nodes to the console for inspection
        //Prints first 10 nodes in the nodes-array. Change to 400 manually if you want
        //a more in-depth look
        ht.print_nodes();

        //Prints the nodes in to file named BFS.txt in breadth first search order
        ht.breadth_first_search("BFS.txt");

        //Prints the nodes in to file named BFS.txt in depth first search order
        ht.depth_first_search("DFS.txt");

        //Counts how many neighbors a node has and how many nodes is it a neighbor to
        ht.count_degrees();

        //Adds neighbors to all nodes until all nodes all connected.
        ht.make_complete();

        //Removes a specified node, in this case the node with the index of 53. It
        //has coordinates 0.702909,1.43305. You can compare files BFS.txt and DIM.txt to see
        //that the corresponding node has indeed been removed in the first graph.
        ht.remove_node(53);
        
        //Not implemented
        //ht.min_span();
    }
}

class Node {
    private float x; //Pisteen x-koordinaatti.
    private float y; //Pisteen y-koordinaatti.
    private Node neighbors[]; //Linkit pisteen naapureihin
    private boolean visited;
    
    public Node(float x1, float y1){
        x = x1;
        y = y1;
        visited = false;
        neighbors = new Node[1];
    }
    
    public void add_neighbor(Node neigh){
        if(neighbors[neighbors.length-1]==null)
            neighbors[neighbors.length-1] = neigh;
        else{
            neighbors = Arrays.copyOf(neighbors, neighbors.length+1);
            neighbors[neighbors.length-1] = neigh;
        }
    }
    
    public void remove_neighbor(Node neigh){
        Node[] new_neigh = new Node[neighbors.length-1];
        boolean removed = false;
        int i = 0;
        for(Node n : neighbors){
            if(n!=null){
                if(n.x()==neigh.x() && n.y()==neigh.y()){
                    n = null;
                    removed = true;
                }
            }
            if(n!=null && i<new_neigh.length){
                new_neigh[i] = n;
                i++;
            }
            
        }
        if(removed)
            neighbors = new_neigh;
    }
    
    public float x(){
        return x;
    }
    public float y(){
        return y;
    }
    public Node[] neighbors(){
        return neighbors;
    }
    public boolean visited(){
        return visited;
    }
    
    public void visited(Boolean visit){
        visited = visit;
    }
    
    public boolean not_in_neighbors(Node j){
        for (Node neighbor : neighbors) {
            if(neighbor==null)
                return true;
            if(neighbor.x() == j.x() && neighbor.y() == j.y()){
                return false;
            }
        }
        return true;
    }
}