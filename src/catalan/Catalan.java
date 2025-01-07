package catalan;

import java.util.ArrayList;

public class Catalan {

    private ArrayList<ArrayList<Move>> moves;
    private final String path;

    public Catalan(String path) {
        this.moves = new ArrayList<>();
        this.path = path;
    }

    public ArrayList<Move> solve() throws UnsolvableGameException {
        Graph graph = new Graph();
        graph.readGraphFromFile(this.path);
        makeMove(new ArrayList<>(), graph);
        return sort(this.moves);
    }

    private void makeMove(ArrayList<Move> movesB, Graph graph) {
       // System.out.println("test");
        //graph.printGraph();

        //System.out.println(movesB);
        if (graph.numVertices() == 1) {
            this.moves.add(new ArrayList<>(movesB));
        }
        //System.out.println("gesamte liste");
        //for (ArrayList<Move> move : this.moves) {
        //    System.out.println(move);
        //}
        else {
            for (Vertex vertex : graph.getVertices()) {
                if (graph.getNeighbours(vertex).size() == 3) {
                    Move move = new Move(vertex, graph);
                    ArrayList<Move> nextMoves = new ArrayList<>(movesB);
                    nextMoves.add(move);
                    makeMove(nextMoves, move.getGraphAfter());
                }
            }
        }
    }

    /*
    public Graph cloneGraph(Graph graph){
        Graph graphClone = null;
        try {
            graphClone = (Graph) graph.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return graphClone;
    }*/

    public ArrayList<Move> sort(ArrayList<ArrayList<Move>> moves) throws UnsolvableGameException {
        if (moves.size() == 0) {
            throw new UnsolvableGameException("Graph nicht lösbar");
        }
        ArrayList<Move> bestMoves = moves.get(0);
        for (int i = 1; i < moves.size(); i++) {
            for (int j = 0; j < moves.get(i).size(); j++) {
                if (moves.get(i).get(j).getSelectedVertex().getID() == bestMoves.get(j).getSelectedVertex().getID()) {
                    continue;
                }
                if (moves.get(i).get(j).getSelectedVertex().getID() < bestMoves.get(j).getSelectedVertex().getID()) {
                    bestMoves = moves.get(i);
                    break;
                }

                if (moves.get(i).get(j).getSelectedVertex().getID() > bestMoves.get(j).getSelectedVertex().getID()) {
                    break;
                }
            }
        }

        return bestMoves;
    }

    public int movesSize(){
        return this.moves.size();
    }


    public static void main(String[] args) throws UnsolvableGameException {
        //Catalan catalan = new Catalan();
        // System.out.println("Solution \n=============\n" + new Catalan().solve("/home/philip/Dokumente/Studium/39-Inf-PP/Catalan/Code2/gml-files/level_56.gml"));
        System.out.println("\nSolution by Philip Adams \n=============\n" + new Catalan(args[0]).solve());
       // System.out.println("Ways to solve: " + catalan.movesSize());
                // Startbar über Terminal: cd catalan (package): java -cp . catalan.Catalan /home/philip/Dokumente/Studium/39-Inf-PP/Catalan/Code2/gml-files/level_56.gml
    }
}
