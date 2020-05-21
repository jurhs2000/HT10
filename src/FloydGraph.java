import java.lang.reflect.Array;
import java.util.Arrays;

public class FloydGraph<V> implements Graph<V> {
    private Integer vertexCount;
    private Integer edgeCount;
    private V[] vertexes;
    private Integer[][] edges;
    private Integer[][] original;
    private V[][] interVertexes;
    private Class<V> type;

    public FloydGraph(Integer vertexCount, Integer edgeCount, Class<V> type) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        createMatrix(type);
    }

    private void createMatrix(Class<V> type) {
        this.vertexes = (V[]) java.lang.reflect.Array.newInstance(type, vertexCount); // Esto puede causar aumento en la complejidad del tamaño
        //this.edges = new Integer[vertexCount][vertexCount];
        this.original = new Integer[vertexCount][vertexCount];
        this.interVertexes = (V[][]) java.lang.reflect.Array.newInstance(type, vertexCount, vertexCount);
        this.type = type;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i == j) {
                    //edges[i][j] = 0;
                    original[i][j] = 0;
                } else {
                    //edges[i][j] = Integer.MAX_VALUE;
                    original[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    @Override
    public void addEdge(V sourceVertex, V targetVertex, Integer edge) {
        if (!sourceVertex.equals(targetVertex)) {
            if (!containsVertex(sourceVertex)) {
                addVertex(sourceVertex);
            }
            if (!containsVertex(targetVertex)) {
                addVertex(targetVertex);
            }
            int sco = getVertexPosition(sourceVertex);
            int tco = getVertexPosition(targetVertex);
            if (edge > 0) {
                original[sco][tco] = edge;
            } else {
                original[sco][tco] = Integer.MAX_VALUE;
            }
        }
        // Need to do a calculateNewEdges after add a new edge
    }

    @Override
    public V addVertex(V vertex) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertexes[i] == null) {
                vertexes[i] = vertex;
                return vertex;
            }
        }
        return null;
    }

    @Override
    public Integer getEdge(V sourceVertex, V targetVertex) {
        if (!sourceVertex.equals(targetVertex)) {
            if (containsVertex(sourceVertex) && containsVertex(targetVertex)) {
                int sco = -1;
                int tco = -1;
                for (int i = 0; i < vertexCount; i++) {
                    if (vertexes[i] != null) {
                        if (vertexes[i].equals(sourceVertex)) {
                            sco = i;
                        }
                        if (vertexes[i].equals(targetVertex)) {
                            tco = i;
                        }
                    }
                }
                return edges[sco][tco];
            }
        }
        return 0;
    }

    @Override
    public Integer getDirectEdge(V sourceVertex, V targetVertex) {
        if (!sourceVertex.equals(targetVertex)) {
            if (containsVertex(sourceVertex) && containsVertex(targetVertex)) {
                int sco = -1;
                int tco = -1;
                for (int i = 0; i < vertexCount; i++) {
                    if (vertexes[i] != null) {
                        if (vertexes[i].equals(sourceVertex)) {
                            sco = i;
                        }
                        if (vertexes[i].equals(targetVertex)) {
                            tco = i;
                        }
                    }
                }
                return original[sco][tco];
            }
        }
        return 0;
    }

    @Override
    public V[] showEdges(V sourceVertex, V targetVertex) {
        V[] intermediates = (V[]) java.lang.reflect.Array.newInstance(type, vertexCount);
        int sco = getVertexPosition(sourceVertex);
        int tco = getVertexPosition(targetVertex);
        int newtco = tco;
        int cont = 0;
        while(!interVertexes[sco][tco].equals(targetVertex)) {
            while (!interVertexes[sco][newtco].equals(interVertexes[sco][getVertexPosition(interVertexes[sco][newtco])])) {
                newtco = getVertexPosition(interVertexes[sco][newtco]);
                System.out.println((cont + 1) + ". " + interVertexes[sco][newtco].toString());
                intermediates[cont] = interVertexes[sco][newtco];
                cont++;
            }
            System.out.println((cont + 1) + ". " + interVertexes[sco][tco].toString());
            intermediates[cont] = interVertexes[sco][tco];
            sco = getVertexPosition(interVertexes[sco][tco]);
            cont++;
        }
        if (intermediates[0] == null) {
            System.out.println("Ninguna");
        }
        return intermediates;
    }

    private int getVertexPosition(V vertex) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertexes[i] != null) {
                if (vertexes[i].equals(vertex)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean containsVertex(V vertex) {
        for (int i = 0; i < vertexCount; i++) {
            try {
                if (vertexes[i].equals(vertex)) {
                    return true;
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Integer edge) {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (edges[i][j].equals(edge)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Integer removeEdge(Integer edge) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public V removeVertex(V vertex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V getCenter() {
        Integer limit = 0;
        Integer maxs[] = new Integer[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (edges[j][i] > limit) {
                    limit = edges[j][i];
                }
            }
            maxs[i] = limit;
            limit = 0;
        }
        limit = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (maxs[i] < limit) {
                limit = maxs[i];
                index = i;
            }
        }
        return vertexes[index];
    }

    @Override
    public V[] showVertexes() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print(vertexes[i].toString() + "\t");
        }
        return vertexes;
    }

    @Override
    public Integer[][] showOriginal() {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (original[i][j] == Integer.MAX_VALUE) {
                    System.out.print("inf\t");
                } else {
                    System.out.print(original[i][j] + "\t");
                }
            }
            System.out.println();
        }
        return original;
    }

    @Override
    public Integer[][] showGraph() {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (edges[i][j] == Integer.MAX_VALUE) {
                    System.out.print("inf\t");
                } else {
                    System.out.print(edges[i][j] + "\t");
                }
            }
            System.out.println();
        }
        return edges;
    }

    @Override
    public V[][] showIntermediateVertex() {
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i == j) {
                    System.out.print("---\t\t\t");
                } else {
                    System.out.print(interVertexes[i][j] + "\t\t");
                }
            }
            System.out.println();
        }
        return interVertexes;
    }

    @Override
    public void calculateNewEdges() {
        // Iniciar matriz de nodos intermedios
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i != j) {
                    interVertexes[i][j] = vertexes[j];
                }
            }
        }
        edges = new Integer[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            edges[i] = Arrays.copyOf(original[i], original[i].length);
        }
        // Empezar ciclo
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i == j) {
                    for (int k = 0; k < vertexCount; k++) {
                        for (int l = 0; l < vertexCount; l++) {
                            if (k !=i && l != j) {
                                if (k != l) {
                                    if (!(edges[i][l] == Integer.MAX_VALUE || edges[k][j] == Integer.MAX_VALUE)) {
                                        if (edges[i][l] + edges[k][j] < edges[k][l]) {
                                            edges[k][l] = edges[i][l] + edges[k][j];
                                            interVertexes[k][l] = vertexes[i];
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Se calcularon las rutas mas cortas!");
    }
    
}