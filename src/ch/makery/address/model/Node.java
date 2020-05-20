package ch.makery.address.model;

public  class Node {

    int source;

    int destination;


    public Node(int source, int destination) {

        this.source = source;

        this.destination = destination;

    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }
}
