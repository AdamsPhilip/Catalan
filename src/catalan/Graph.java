package catalan;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private HashMap<Vertex, ArrayList> edges;

    public Graph() {
        this.edges = new HashMap<>();
    }

    public boolean readGraphFromFile(String filepath){

        ArrayList<String[]> lines = new ArrayList<>();
        try {
            Files.lines(Paths.get(filepath))
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split(" "))
                    .filter(arr -> arr.length > 2)
                    .forEach(line -> lines.add(line));
        } catch (Exception e) { return false;

        }
        if (createVertex(lines) && createEdge(lines)){
            return true;
        }
        return false;
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
          //  System.out.println(Arrays.toString(line));
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
            if (v1 == v.getId()) {
                edges.get(v).add(new Vertex(v2));
            }
            if (v2 == v.getId()) {
                edges.get(v).add(new Vertex(v1));
            }
        }
    }

    // Neue Kante setzen mit zwei Vertex-Objekten
    public void setEdges(Vertex v1, Vertex v2){
        for (Vertex v : edges.keySet()) {
            if (v1.equals(v)) {
                edges.get(v).add(v2);
            }
            if (v2.equals(v)) {
                edges.get(v).add(v1);
            }
        }
    }

    public void printGraph(){
        this.edges.entrySet().stream().forEach(System.out::println);
    }

    public int numVertices(){
        return edges.size();
    }

    public ArrayList<Vertex> getVertices(){
        return new ArrayList<>(edges.keySet());
    }

    public Boolean areNeighbours(Vertex u, Vertex v){
        for (Vertex v2 : edges.keySet()) {
            if (v2.equals(u)){
                this.edges.get(v2).contains(v);
                return true;
            }
        }
        return false;
    }
}
