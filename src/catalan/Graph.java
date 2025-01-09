package catalan;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Graph implements Cloneable{
    private HashMap<Vertex, ArrayList<Vertex>> edges;

    public Graph() {
        this.edges = new HashMap<>();
    }

    public Graph(HashMap<Vertex, ArrayList<Vertex>> edges) {
        this.edges = edges;
    }

    public boolean readGraphFromFile(String filepath){

        ArrayList<String[]> lines = new ArrayList<>();
        try {
            Files.lines(Paths.get(filepath)) // Jede Zeile der Datei wird, in diesem stream, in ein Array ausgelesen
                    .filter(line -> !line.isEmpty()) //Filtert leere und Kopfzeile raus
                    .map(String::strip)
                    .map(line -> line.split(" "))
                    .filter(arr -> arr.length > 2) // sortiert die erste Zeile der Datei: "graph [" aus
                    .forEach(lines::add);
        } catch (Exception e) { return false;

        }
        return createVertex(lines) && createEdge(lines);
    }

    //Lege die Knoten aus der GML datei an
    private Boolean createVertex(ArrayList<String[]> gmlData){
        gmlData.stream().filter(line -> line[0].strip().equals("node"))
                .forEach(line -> edges.putIfAbsent(new Vertex(Integer.valueOf(line[3])), new ArrayList<>()));
        return true;
    }

    //Lege die Kanten aus der GML datei an
    private Boolean createEdge(ArrayList<String[]> gmlData){
        for(int i = 0; i < gmlData.size(); i++) {
            String[] line = gmlData.get(i);

            if (line[0].strip().equals("edge")) { // Filtert die Zeilen einer Kante
                int v1 = Integer.valueOf(line[3].strip());
                int v2 = Integer.valueOf(line[5].strip());
                setEdge(v1, v2); // ruft die Methode zum Einpflegen der Kanten in die HashMap ein
            }

        }
        return true;
    }

    // Neue Kante setzen mit zwei Integer (initiales anlegen beim Datei lesen)
    private void setEdge(int v1, int v2){
        for (Vertex v : edges.keySet()) {
            if (v1 == v.getID()) {
                edges.get(v).add(new Vertex(v2));
            }
            if (v2 == v.getID()) {
                edges.get(v).add(new Vertex(v1));
            }
        }
    }

    // Neue Kante setzen mit zwei Vertex-Objekten zum Kollabieren
    private void setEdge(Vertex vColl, Vertex vRemove){
        for (Vertex v : this.edges.keySet()) {
            if (vColl.equals(v)) {
                for (Vertex v4 : getNeighbours(vRemove)){
                    if(!this.edges.get(v).contains(v4) && !v4.equals(vColl) && !v4.equals(vRemove)) {
                        this.edges.get(v).add(v4);
                        this.edges.get(v4).add(vColl);
                    }

                }

            }
            this.edges.get(v).remove(vRemove); //entfernt alle Kanten zu den entfernten knoten
        }
    }

    public int numVertices(){
        return edges.size();
    }

    public ArrayList<Vertex> getVertices(){
        return new ArrayList<>(edges.keySet());
    }

    // ist implementiert, da in der Spezifikation gefordert, wird aber nicht Verwendet
                    public Boolean areNeighbours(Vertex u, Vertex v){
                        for (Vertex v2 : edges.keySet()) {
                            if (v2.equals(u) && this.edges.get(v2).contains(v)) {
                                return true;
                            }
                        }
                        return false;
                    }

    public ArrayList<Vertex> getNeighbours(Vertex u){
        return new ArrayList<>(edges.get(u));
    }

    public Graph collapseNeighbours(Vertex u){
        ArrayList<Vertex> neighbours = getNeighbours(u);
        for (Vertex v : neighbours) {
            setEdge(u,v);
            removeVertex(v);
        }

        return Graph.this;
    }

    private void removeVertex(Vertex v){
        this.edges.remove(v);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return Objects.equals(edges, graph.edges);
    }

    // überschreibt clone s. Texxt
    @Override
    public Graph clone() throws CloneNotSupportedException{
        Graph g = (Graph) super.clone();
        g.edges = new HashMap<>();
        for (Vertex v : this.edges.keySet()) {
            g.edges.put(new Vertex(v.getID()), new ArrayList<>(cloneVertices(v))); // legt alle Objekte in der HashMap neu an

        }
        return g;
    }

    //Clont die ArrayList im Value
    public ArrayList<Vertex> cloneVertices(Vertex u) {
        return this.edges.get(u).stream().map(v -> new Vertex(v.getID())).collect(Collectors.toCollection(ArrayList::new));

    }

}