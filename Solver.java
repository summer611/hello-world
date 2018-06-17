package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    Node first;
    Node current;

    public Solver(WorldState initial) {
        first = new Node(initial, 0, null);
        current = first;
        current.findNeighbor();
// if I chose to circulate here: while(!solved), every time the bestPQ will be renewed...That's why I got stuck and doubt the algorithm.
    }
    public int moves(){
        return current.moves;
    }
    public Iterable<WorldState> solution(){
        List<WorldState> solution=new ArrayList();
        while (current!=null){
            solution.add(current.worldState);
            current=current.previous;
        }
        return solution;
    }

    public class Node implements Comparable<Node>{
        WorldState worldState;
        int moves;
        int priority;
        Node previous;
        public MinPQ bestPQ=new MinPQ();

        public Node(WorldState ws,int m,Node pre){
            worldState=ws;
            moves=m;
            previous=pre;
            priority=priority();
        }
        @Override
       public int compareTo(Node other){
          return this.priority-other.priority;
        }

        public int priority(){
           int estimate=worldState.estimatedDistanceToGoal();
           return this.moves+estimate;
        }

        public void findNeighbor(){
            int count=0;
            while(!current.worldState.isGoal()) {
                for (WorldState neighbor : current.worldState.neighbors()) {
                    if (previous == null || !previous.worldState.equals(neighbor)) {
                        Node state = new Node(neighbor, current.moves + 1, current);
                        bestPQ.insert(state);
                    }
                }
                current = (Node) bestPQ.delMin();
                count++;
               // System.out.println(current.worldState);
            }System.out.println(current.worldState+" "+count);
        }
    }
}
