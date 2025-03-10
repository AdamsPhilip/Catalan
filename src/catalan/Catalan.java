package catalan;

import java.util.ArrayList;
import java.util.Collections;

public class Catalan {

    private ArrayList<Solution> solutions;
    private final String path;

    public Catalan(String path) {
        this.solutions = new ArrayList<>();
        this.path = path;
    }

    public ArrayList<Move> solve() throws UnsolvableGameException {
        Graph graph = new Graph(); //erstellt leeren Graph
        if (!graph.readGraphFromFile(this.path)){ //ruft die Methode zum Lesen und erstellen des Graphen auf und testet, ob dies ordnungsgemäß Funktioniert
            throw new UnsolvableGameException("Graph kann nicht gelesen werden");
        }
        testGraphForSolve(graph); // 1. Test auf lösbarkeit
        doNextMove(new ArrayList<>(), graph);
        if (this.solutions.isEmpty()) { // 2. Test auf lösbarkeit
            throw new UnsolvableGameException("Es gibt keine Lösung dieses Graphen!");
        }
        return getBestSolution();
    }

    private void doNextMove(ArrayList<Move> movesB, Graph graph) {
        if (graph.numVertices() == 1) { //Basisfall 1 der Rekursion, beendet einen lösbaren Zug
            this.solutions.add(new Solution(movesB));
        }
        else {
            for (Vertex vertex : graph.getVertices()) {
                if (graph.getNeighbours(vertex).size() == 3) {  //"Basisfall 2" der Rekursion, wenn kein Vertex passt, läuft ein unlösbarer Zug hier ins leere
                    Move move = new Move(vertex, graph);
                    ArrayList<Move> nextMoves = new ArrayList<>(movesB);
                    nextMoves.add(move);
                    doNextMove(nextMoves, move.getGraphAfter()); //Rekursiver Aufruf von doNextMove
                }
            }
        }
    }

    private ArrayList<Move> getBestSolution() {
        Collections.sort(this.solutions);
        return this.solutions.get(0).getMoves();
    }

    private void testGraphForSolve(Graph graph) throws UnsolvableGameException {
        if (graph.numVertices() %3 != 1) {  // Testet, ob die Anzahl an Knoten überhaupt einen lösbaren Graphen ergeben kann
            throw new UnsolvableGameException("Die Anzahl an Knoten kann keinen Lösbaren Graphen ergeben!");
        }
    }

    public static void main(String[] args) throws UnsolvableGameException {
        System.out.println("\nSolution by Philip Adams \n=============\n" + new Catalan(args[0]).solve() + "\n");
    }
}
