package ch.makery.address.controller;


import ch.makery.address.model.Arc;
import ch.makery.address.model.Graph;
import ch.makery.address.model.Vertex;
import javafx.scene.layout.Pane;


import java.util.*;


public class PlanarityController {
    private static final int COUNT_OF_NODES_K5 = 5;
    private static final int COUNT_OF_ARCS_IN_DIRECTED_K5 = 10;
    private static final int COUNT_OF_NODES_K33 = 6;
    private static final int COUNT_OF_ARCS_IN_DIRECTED_K33 = 9;
    private boolean isG5 = false;
    private final Graph graph;

    public PlanarityController(Graph graph) {
        this.graph = graph;
    }

    public boolean verify() {
        int arcsCount = 0;
        for (Vertex vertex : graph.getVertices()) {
            for (Arc arc : vertex.getArcs()) {
                arcsCount++;
            }
        }
        arcsCount = arcsCount / 2;
        if (graph.getVertices().size() >= COUNT_OF_NODES_K5
                && arcsCount == graph.getVertices().size() * (graph.getVertices().size() - 1)) {
            return false;
        }

        List<Vertex> someKuratowskiGraph = permute();

        if (someKuratowskiGraph.size() == COUNT_OF_NODES_K5
                && arcsCount >= COUNT_OF_ARCS_IN_DIRECTED_K5) {
            return false;

        } else if (someKuratowskiGraph.size() == COUNT_OF_NODES_K33
                && arcsCount >= COUNT_OF_ARCS_IN_DIRECTED_K33) {
            return false;
        }


        return true;
    }

    private List<Vertex> permute() {
        List<Vertex> permutation = new ArrayList<>();

        for (Vertex one : graph.getVertices()) {
            permutation.clear();
            permutation.add(one);

            for (Vertex two : graph.getVertices()) {
                if (permutation.contains(two)) {
                    continue;
                }
                permutation.add(two);

                for (Vertex three : graph.getVertices()) {
                    if (permutation.contains(three)) {
                        continue;
                    }
                    permutation.add(three);

                    for (Vertex four : graph.getVertices()) {
                        if (permutation.contains(four)) {
                            continue;
                        }
                        permutation.add(four);

                        for (Vertex five : graph.getVertices()) {
                            if (permutation.contains(five)) {
                                continue;
                            }
                            permutation.add(five);

                            for (Vertex six : graph.getVertices()) {
                                if (permutation.contains(six)) {
                                    continue;
                                }
                                permutation.add(six);

                                for (int i = 0; i < permutation.size(); i++) {
                                    for (int j = 0; j < permutation.size(); j++) {
                                        Collections.swap(permutation, i, j);

                                        if (isK33(permutation)) {
                                            return permutation;
                                        }
                                    }
                                }

                                permutation.remove(six);
                            }

                            if (isK5(permutation)) {
                                return permutation;
                            }

                            permutation.remove(five);
                        }
                        permutation.remove(four);
                    }
                    permutation.remove(three);
                }
                permutation.remove(two);
            }
        }

        return permutation;
    }

    private boolean isK5(List<Vertex> permutation) {
        boolean isK5 = true;
        for (Vertex begin : permutation) {
            for (Vertex end : permutation) {
                if (begin == end) {
                    continue;
                }
                isK5 &= isPathExist(begin, end);
            }
        }
        isG5 = isK5;
        return isK5;
    }

    private boolean isK33(List<Vertex> permutation) {
        boolean isK33 = true;
        List<Vertex> homes = permutation.subList(0, COUNT_OF_NODES_K33 / 2);
        List<Vertex> wells = permutation.subList(COUNT_OF_NODES_K33 / 2, COUNT_OF_NODES_K33);
        for (Vertex home : homes) {
            for (Vertex well : wells) {
                isK33 &= isPathExist(home, well);
            }
        }
        return isK33;
    }

    private Arc arcForRemove;

    private boolean isPathExist(Vertex source, Vertex destination) {
        Map<Vertex, Boolean> visitedNodes = new HashMap<>();
        for (Vertex node : graph.getVertices()) {
            visitedNodes.put(node, false);
        }
        LinkedList<Vertex> queue = new LinkedList<>();
        visitedNodes.replace(source, true);
        queue.add(source);
        while (queue.size() != 0) {
            source = queue.poll();
            for (Arc arc : source.getArcs()) {
                if (arc.getBegin() == source) {
                    if (arc.getEnd() == (destination)) {
                        arcForRemove = arc;
                        return true;
                    }

                    if (!visitedNodes.get(arc.getEnd())) {
                        visitedNodes.replace(arc.getEnd(), true);
                        queue.add(arc.getEnd());
                    }
                }
            }

        }

        return false;
    }

    public Graph makePlanar(Graph graph) {
        if (!verify()) {
            while (!verify()) {
                graph.getVertices().forEach(vertex -> vertex.getArcs().remove(arcForRemove));
                arcForRemove.getBegin().getArcs().remove(arcForRemove);
                arcForRemove.getEnd().getArcs().remove(arcForRemove);
                graph.removeArcFromMatrix(arcForRemove);
                ((Pane) graph.getTab().getContent()).getChildren().removeAll(arcForRemove.getArrow());
                ((Pane) graph.getTab().getContent()).getChildren().remove(arcForRemove);
            }
        }
        return graph;
    }
}
