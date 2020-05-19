package ch.makery.address.controller;

import ch.makery.address.model.Arc;
import ch.makery.address.model.Vertex;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Vector;

public class EulerCycle {


    public ArrayList<Integer> findEiler(int vertex, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> matrix) {
        path.add(vertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(vertex).get(i) == 1) {
                matrix.get(vertex).set(i, 0);

                findEiler(i, path, matrix);
            }
        }
        return path;
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
