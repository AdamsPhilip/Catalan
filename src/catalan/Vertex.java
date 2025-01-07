package catalan;

import java.util.Objects;

public class Vertex {
    private final int id;

    public Vertex(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                '}';
    }
}
