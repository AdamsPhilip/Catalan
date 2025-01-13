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

    // ist implementiert, da in der Spezifikation gefordert, wird aber nicht Verwendet
    public Graph getGraphBefore() {
        return this.graphBefore;
    }

    public Graph getGraphAfter() {
        Graph graphClone = null;
        try {
            graphClone = (Graph) this.graphBefore.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone not supported");
        }
        assert graphClone != null;
        this.graphAfter = graphClone.collapseNeighbours(this.SelectedVertex); // erstellt die Veränderung nach einem Zug im Graphen
        return this.graphAfter;
    }

    @Override
    public String toString() {
        return "select vertex " + this.SelectedVertex.getID();
    }

}
