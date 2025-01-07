package catalan;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
            Files.lines(Paths.get(filepath))
                    .filter(line -> !line.isEmpty())
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
            String source = line[0].strip();

            if (source.equals("edge")) {
                int v1 = Integer.valueOf(line[3].strip());
                int v2 = Integer.valueOf(line[5].strip());
                setEdge(v1, v2);
                edges.putIfAbsent(new Vertex(Integer.valueOf(line[3])), new ArrayList<>());
            }

        }
        return true;
    }

    // Neue Kante setzen mit zwei Integer
    public void setEdge(int v1, int v2){
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
    public void setEdge(Vertex vColl, Vertex vRemove){
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

                            public void printGraph(){
                                this.edges.entrySet().forEach(System.out::println);
                            }

    public int numVertices(){
        return edges.size();
    }

    public ArrayList<Vertex> getVertices(){
        return new ArrayList<>(edges.keySet());
    }

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

                                    public HashMap<Vertex, ArrayList<Vertex>> getEdges() {
                                        return new HashMap<>(edges);
                                    }

    @Override
    public Graph clone() throws CloneNotSupportedException{
        Graph g = (Graph) super.clone();
        g.edges = new HashMap<>();
        for (Vertex v : this.edges.keySet()) {
            g.edges.put(new Vertex(v.getID()), new ArrayList<>(this.edges.get(v)));

        }
        return g;
    }

}