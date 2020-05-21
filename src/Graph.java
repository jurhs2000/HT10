public interface Graph<V> {
    void addEdge(V sourceVertex, V targetVertex, Integer edge);
    V addVertex(V vertex);
    Integer getEdge(V sourceVertex, V targetVertex);
    Integer getDirectEdge(V sourceVertex, V targetVertex);
    V[] showEdges(V sourceVertex, V targetVertex);
    boolean containsVertex(V vertex);
    boolean containsEdge(Integer edge);
    Integer removeEdge(Integer edge);
    V removeVertex(V vertex);
    V getCenter();
    Integer[][] showOriginal();
    Integer[][] showGraph();
    V[][] showIntermediateVertex();
    V[] showVertexes();
    void calculateNewEdges();
}