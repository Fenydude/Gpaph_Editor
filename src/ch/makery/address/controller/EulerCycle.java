package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Vertex;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Vector;

public class EulerCycle {


    public boolean euler(Vertex vertex) {
        for (Arc arc : vertex.getArcs()) {
            if (vertex == arc.getBegin()) {
                if (!arc.isVisited()) {
                    arc.setVisited(true);
                    System.out.print(vertex.getVertexId() + "  -> " + "  ");
                    euler(arc.getEnd());

                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public  void DFS(ArrayList<ArrayList<Integer>> adjMatrix, boolean[] visited, int n, int i) {
        System.out.print(" ->  " + (i + 1));
        visited[i] = true;
        for (int j = 0; j < n; j++) {
            if (!(visited[j]) && adjMatrix.get(i).get(j) == 1) {
                DFS(adjMatrix, visited, n, j);
            }

        }
    }

   public ArrayList<Integer> findEiler(int vertex, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> matrix){
       path.add(vertex);
        for(int i = 0; i < matrix.size(); i++){
            if(matrix.get(vertex).get(i) == 1){
                matrix.get(vertex).set(i,0);

                findEiler(i, path, matrix);
            }
        }
        return path;
    }



    public boolean hamiltonovCycle(Vertex vertex) {
        for (Arc arc : vertex.getArcs()) {
            if (vertex == arc.getBegin()) {
                if (!vertex.isVisited()) {
                    vertex.setVisited(true);
                    hamiltonovCycle(arc.getEnd());
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkForEiler(ArrayList<ArrayList<Integer>> matrix) {
        int size = matrix.size();
        ArrayList<Integer> dp = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            dp.add(0);
        }
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                if (matrix.get(i).get(k) == 1) {
                    dp.set(i, dp.get(i) + 1);
                    dp.set(k, dp.get(k) - 1);
                }
            }
        }
        for (Integer i : dp) {
            if (i != 0) return false;
        }
        return true;
    }


}
