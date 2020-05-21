/**
 * @author Julio Herrera
 * 
 * Implementacion de la interfaz de grafo con utilizacion de generics
 * para representar un grafo que utiliza una estructura de datos de
 * matrices de adyacencia y el algoritmo de floyd para calcular las 
 * rutas mas cortas entre los vertices
 */
import java.util.Arrays;

public class FloydGraph<V> implements Graph<V> {
    private Integer vertexCount; // Cantidad de vertices del grafo
    private V[] vertexes; // Vertices del grafo
    private Integer[][] edges; // Matriz de adyacencia para rutas mas cortas
    private Integer[][] original; // Matriz de adyacencia original
    private V[][] interVertexes; // Matriz de nodos intermedios
    private Class<V> type; // Tipo de clase del generico para crear las matrices
    String leftAlignSFormat = "| %-15s "; // Formato para imprimir las matrices

    /**
     * @param vertexCount cantidad de nodos (vertices) del grafo
     * @param type tipo del generico que se utiliza (el .class)
     */
    public FloydGraph(Integer vertexCount, Class<V> type) {
        this.vertexCount = vertexCount;
        createMatrix(type);
    }

    /**
     * Crea las matrices a utilizar en el grafo
     * @param type tipo de generico
     */
    private void createMatrix(Class<V> type) {
        /**
         * Esto es utilizado para poder crear las matrices de tipo generico en java
         */
        @SuppressWarnings("unchecked")
        V [] vertexes = (V[]) java.lang.reflect.Array.newInstance(type, vertexCount); // Esto puede causar aumento en la complejidad del tamaño
        this.vertexes = vertexes;
        this.original = new Integer[vertexCount][vertexCount];
        @SuppressWarnings("unchecked")
        V[][] interVertexes = (V[][]) java.lang.reflect.Array.newInstance(type, vertexCount, vertexCount);
        this.interVertexes = interVertexes;
        this.type = type;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i == j) {
                    original[i][j] = 0;
                } else {
                    original[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    /**
     * Crea una nueva arista entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @param edge peso de la arista
     */
    @Override
    public void addEdge(V sourceVertex, V targetVertex, Integer edge) {
        if (!sourceVertex.equals(targetVertex)) { // Verifica que los vertices no sean iguales
            // Si los vertices no existen, los crea
            if (!containsVertex(sourceVertex)) {
                addVertex(sourceVertex);
            }
            if (!containsVertex(targetVertex)) {
                addVertex(targetVertex);
            }
            int sco = getVertexPosition(sourceVertex);
            int tco = getVertexPosition(targetVertex);
            // edge = 0 significa que se elimino la arista y se coloca como infinito
            if (edge > 0) {
                original[sco][tco] = edge;
            } else {
                original[sco][tco] = Integer.MAX_VALUE; // (infinito)
            }
        }
        // Need to do a calculateNewEdges after add a new edge
    }

    /**
     * Agrega un nuevo vertice (nodo) al grafo
     * @param vertex vertice o nodo
     * @return vertice o nodo que se agrego
     */
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

    /**
     * Obtiene la ruta mas corta entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @return peso de la ruta mas corta
     */
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

    /**
     * Obtiene la arista entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @return peso de la arista entre los vertices
     */
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

    /**
     * Muestra los vertices por los que hay que pasar por la ruta mas corta entre dos vertices
     * @param sourceVertex vertice de origen
     * @param targetVertex vertice de destino
     * @return matriz con los vertices que hay que pasar
     */
    @Override
    public V[] showEdges(V sourceVertex, V targetVertex) {
        @SuppressWarnings("unchecked")
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

    /**
     * Obtiene la posicion de un vertice en cada una de las matrices del grafo
     * basado en la matriz de una dimension de vertices
     * @param vertex vertice a encontrar
     * @return la posicion del vertice en la matriz o -1 si no existe
     */
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

    /**
     * Para comprobar si un vertice esta en el grafo
     * @param vertex vertice a comprobar
     */
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

    /**
     * Obtiene el vertice que es el centro del grafo
     * @return el vertice
     */
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

    /**
     * Muestra la matriz de vertices\
     * @return matriz de vertices del grafo
     */
    @Override
    public V[] showVertexes() {
        System.out.format(leftAlignSFormat, "-----");
        for (int i = 0; i < vertexCount; i++) {
            System.out.format(leftAlignSFormat, vertexes[i].toString().toUpperCase());
        }
        return vertexes;
    }

    /**
     * Muestra la matriz original\
     * @return matriz de pesos originales
     */
    @Override
    public Integer[][] showOriginal() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.format(leftAlignSFormat, vertexes[i].toString().toUpperCase());
            for (int j = 0; j < vertexCount; j++) {
                if (original[i][j] == Integer.MAX_VALUE) {
                    System.out.format(leftAlignSFormat, "inf");
                    //System.out.print("inf\t");
                } else {
                    System.out.format(leftAlignSFormat, original[i][j]);
                    //System.out.print(original[i][j] + "\t");
                }
            }
            System.out.println();
        }
        return original;
    }

    /**
     * Muestra la matriz de rutas mas cortas\
     * @return matriz de adyacencia con las rutas mas cortas
     */
    @Override
    public Integer[][] showGraph() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.format(leftAlignSFormat, vertexes[i].toString().toUpperCase());
            for (int j = 0; j < vertexCount; j++) {
                if (edges[i][j] == Integer.MAX_VALUE) {
                    System.out.format(leftAlignSFormat, "inf");
                    //System.out.print("inf\t");
                } else {
                    System.out.format(leftAlignSFormat, edges[i][j]);
                    //System.out.print(edges[i][j] + "\t");
                }
            }
            System.out.println();
        }
        return edges;
    }

    /**
     * Muestra la matriz de nodos intermedios\
     * @return matriz de vertices por los que ir por una ruta mas corta
     */
    @Override
    public V[][] showIntermediateVertex() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.format(leftAlignSFormat, vertexes[i].toString().toUpperCase());
            for (int j = 0; j < vertexCount; j++) {
                if (i == j) {
                    System.out.format(leftAlignSFormat, " ---- ");
                } else {
                    System.out.format(leftAlignSFormat, interVertexes[i][j]);
                }
            }
            System.out.println();
        }
        return interVertexes;
    }

    /**
     * Utiliza el algoritmo de floyd marshall para calcular las rutas mas cortas
     * entre los vertices del grafo
     */
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
        // Copia la matriz original a la matriz de rutas mas cortas
        edges = new Integer[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            edges[i] = Arrays.copyOf(original[i], original[i].length);
        }
        // Encontrar las rutas mas cortas, gurdarlas en la matriz "edges" y guardar los nodos intermedios
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (i == j) { // Encuentra cuando los vertices son iguales
                    // Recorre cada uno de las aristas de nuevo y encuantra los que se pueden operar
                    for (int k = 0; k < vertexCount; k++) {
                        for (int l = 0; l < vertexCount; l++) {
                            if (k !=i && l != j) { // Para evitar la fila y columna bloquedas
                                if (k != l) { // Para evitar cuando son iguales (peso 0)
                                    // Hacer si ninguno de los pesos a comparar es infinito
                                    if (!(edges[i][l] == Integer.MAX_VALUE || edges[k][j] == Integer.MAX_VALUE)) {
                                        // Si la suma de los pesos de cruce es menor a la del peso actual
                                        if (edges[i][l] + edges[k][j] < edges[k][l]) {
                                            // Guardar el nuevo menor peso
                                            edges[k][l] = edges[i][l] + edges[k][j];
                                            // Guardar el numero de iteracion en los nodos intermedios
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