package catalan;

public class Move {
    private final Vertex SelectedVertex;
    private final Graph graphBefore;
    private Graph graphAfter;

    public Move(Vertex selectedVertex, Graph graphBefore) {
        this.SelectedVertex = selectedVertex;
        this.graphBefore = graphBefore;
        this.graphAfter = null;
    }

    public Vertex getSelectedVertex() {
        return this.SelectedVertex;
    }

    public Graph getGraphBefore() {
        return this.graphBefore;
    }

    public Graph getGraphAfter() {
        Graph graphColne = null;
        try {
            graphColne = (Graph) this.graphBefore.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert graphColne != null;
        this.graphAfter = graphColne.collapseNeighbours(this.SelectedVertex);
        return this.graphAfter;
    }

    @Override
    public String toString() {
        return "select vertex " + this.SelectedVertex.getId();
    }

}
