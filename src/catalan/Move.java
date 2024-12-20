package catalan;

public class Move {
    private final Vertex SelectedVertex;
    private final Graph graphBefore;
    private final Graph graphAfter;

    public Move(Vertex selectedVertex, Graph graphBefore) {
        this.SelectedVertex = selectedVertex;
        this.graphBefore = graphBefore;
        this.graphAfter = graphBefore;
    }

    public Vertex getSelectedVertex() {
        return this.SelectedVertex;
    }

    public Graph getGraphBefore() {
        return this.graphBefore;
    }

    public Graph getGraphAfter() {
        return this.graphAfter.collapseNeighbours(SelectedVertex);
    }

    public String toString() {
        return "select vertex " + this.SelectedVertex;
    }

}
