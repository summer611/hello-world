package hw4.puzzle;

import java.util.*;
import edu.princeton.cs.algs4.Queue;
public class Board implements WorldState{

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    int size;
    private int[][] boardTiles;
    public Board(int[][] tiles) {
        size = tiles.length;
        boardTiles=new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardTiles[i][j] = tiles[i][j];
            }
        }
    }
    public int tileAt(int i, int j){
        if (i < 0 || i >= size || j < 0 || j >= size ) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return boardTiles[i][j];
    }
    public int size(){
        return size;
    }
    public Iterable<WorldState> neighbors(){
        int blankX=0;
        int blankY=0;
        int[][] neighbor=new int[size][size];
        Queue<WorldState> neighbors=new Queue<>();

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(tileAt(i,j)==0) {
                    blankX=i;
                    blankY=j;
                }
                neighbor[i][j]=tileAt(i,j);
            }
        }
        for(int k=0;k<size;k++){
            for(int l=0;l<size;l++){
                if(Math.abs(blankX-k)+Math.abs(blankY-l)==1) {
                    neighbor[blankX][blankY] = neighbor[k][l];
                    neighbor[k][l] = 0;
                    Board neighborB = new Board(neighbor);
                    neighbors.enqueue(neighborB);
                    neighbor[k][l] = neighbor[blankX][blankY];
                    neighbor[blankX][blankY] = 0;
                }
            }
        }
        return neighbors;
    }
    public int hamming(){
        int distanceH=0;
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != i * size + j + 1) {
                    distanceH++;
                }
            }
        }
        return distanceH;
    }
    public int manhattan(){
        int distanceM=0;
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i,j)!=0) {
                    int X=(tileAt(i,j)-1)/size;
                    int Y=Math.floorMod(tileAt(i,j)-1,size);
                    distanceM+=(Math.abs(X-i)+Math.abs(Y-j));
                }
            }
        }
        return distanceM;
    }
    public int estimatedDistanceToGoal(){
        return manhattan();
    }
    public boolean equals(Object y){
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board other=(Board)y;
        if(size!=other.size){
            return false;
        }else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (tileAt(i, j) != other.tileAt(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
