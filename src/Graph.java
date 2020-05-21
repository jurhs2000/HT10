/**
 * @author Julio Herrera 19402
 * 
 * Interfaz para el grafo con uso de generics
 * 
 * Esta interfaz define limitadamente los metodos utilizados para esta
 * hoja de trabajo.
 * GRAFO BASADO EN DIRECCIONES
 */
public interface Graph<V> {
    /**
     * Crea una nueva arista entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @param edge peso de la arista
     */
    void addEdge(V sourceVertex, V targetVertex, Integer edge);
    /**
     * Agrega un nuevo vertice (nodo) al grafo
     * @param vertex vertice o nodo
     * @return vertice o nodo que se agrego
     */
    V addVertex(V vertex);
    /**
     * Obtiene la ruta mas corta entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @return peso de la ruta mas corta
     */
    Integer getEdge(V sourceVertex, V targetVertex);
    /**
     * Obtiene la arista entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @return peso de la arista entre los vertices
     */
    Integer getDirectEdge(V sourceVertex, V targetVertex);
    /**
     * Muestra los vertices por los que hay que pasar por la ruta mas corta entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @return matriz con los vertices que hay que pasar
     */
    V[] showEdges(V sourceVertex, V targetVertex);
    /**
     * Para comprobar si un vertice esta en el grafo
     * @param vertex vertice a comprobar
     */
    boolean containsVertex(V vertex);
    /**
     * Obtiene el vertice que es el centro del grafo
     * @return el vertice
     */
    V getCenter();
    /**
     * Muestra la matriz original\
     * @return matriz de pesos originales
     */
    Integer[][] showOriginal();
    /**
     * Muestra la matriz de rutas mas cortas\
     * @return matriz de adyacencia con las rutas mas cortas
     */
    Integer[][] showGraph();
    /**
     * Muestra la matriz de nodos intermedios\
     * @return matriz de vertices por los que ir por una ruta mas corta
     */
    V[][] showIntermediateVertex();
    /**
     * Muestra la matriz de vertices\
     * @return matriz de vertices del grafo
     */
    V[] showVertexes();
    /**
     * Utiliza el algoritmo para calcular las rutas mas cortas
     */
    void calculateNewEdges();
}