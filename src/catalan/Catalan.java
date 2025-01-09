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
        if (!graph.readGraphFromFile(this.path)){
            throw new ExceptionInInitializerError("Graph kann nicht gelesen werden");
        }
        testGraphForSolve(graph); // 1. Test auf lösbarkeit
        doNextMove(new ArrayList<>(), graph);
        if (this.moves.isEmpty()) { // 2. Test auf lösbarkeit
            throw new UnsolvableGameException("Es gibt keine Lösung dieses Graphen!");
        }
        return sort(this.moves);
    }

    private void doNextMove(ArrayList<Move> movesB, Graph graph) {
        if (graph.numVertices() == 1) { //Basisfall 1 der Rekursion, beendet einen lösbaren Zug
            this.moves.add(new ArrayList<>(movesB));
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

    private ArrayList<Move> sort(ArrayList<ArrayList<Move>> moves) {
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
        moves.forEach(System.out::println);
        return bestMoves;
    }

    private void testGraphForSolve(Graph graph) throws UnsolvableGameException {
        if (graph.numVertices() %3 != 1) {  // Testet, ob die Anzahl an Knoten überhaupt einen lösbaren Graphen ergeben kann
            throw new UnsolvableGameException("Die Anzahl an Knoten kann keinen Lösbaren Graphen ergeben!");
        }
    }

    public static void main(String[] args) throws UnsolvableGameException {
       // Catalan catalan = new Catalan("/home/philip/Dokumente/Studium/39-Inf-PP/Catalan/Code2/gml-files/impossible_graph1.gml");
        System.out.println("Solution \n=============\n" + new Catalan("/home/philip/Dokumente/Studium/39-Inf-PP/Catalan/Code2/gml-files/level_56.gml").solve());
        //System.out.println("\nSolution by Philip Adams \n=============\n" + new Catalan(args[0]).solve() + "\n");
       // System.out.println("Ways to solve: " + catalan.movesSize());
                // Startbar über Terminal: cd catalan (package): java -cp . catalan.Catalan /home/philip/Dokumente/Studium/39-Inf-PP/Catalan/Code2/gml-files/level_56.gml
    }
}
