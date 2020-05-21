# Julio Herrera 19402
# Modulo de grafo con direcciones que implementa el uso de networkX y el algoritmo de floyd warshall

import networkx as nx

# Crea el grafo
graph = nx.DiGraph()

# Inserta los vertices y la arista
def addEdge(sourceVertex, targetVertex, distance):
    # Si es 0 se elimina la arista
    if (distance == 0):
        if graph.has_edge(sourceVertex, targetVertex):
            graph.remove_edge(sourceVertex, targetVertex)
        else:
            print("No existe la ruta")
    else:
        # Si los vertices no existen, los crea
        if not containsVertex(sourceVertex):
            graph.add_node(sourceVertex)
        if not containsVertex(targetVertex):
            graph.add_node(targetVertex)
        # Crea la arista indicada
        graph.add_edge(sourceVertex, targetVertex, weight=float(distance))

# Comprueba si existe le vertice en el grafo
def containsVertex(vertex):
    if vertex in graph.nodes():
        return True
    else:
        return False

# Muestra la ruta mas corta entre dos vertices y la ruta entre ellos
def getMinEdge(sourceVertex, targetVertex):
    # Obtiene la ruta entre cada uno de las conexiones y sus distancias minimas
    predecesors, distance = nx.floyd_warshall_predecessor_and_distance(graph)
    print("La distancia mas corta es (Km):")
    print(distance[sourceVertex][targetVertex])
    # Si la distancia es infinito no hacer nada
    if (distance[sourceVertex][targetVertex] != float('Inf')):
        print("Las ciudades por las que pasar son: ")
        pred = predecesors[sourceVertex][targetVertex]
        preds = []
        if (pred == sourceVertex):
            print("Ninguna")
        else:
            # Recorre para obtener los nodos intermedios
            while (pred != sourceVertex):
                preds.append(pred)
                pred = predecesors[sourceVertex][pred]
            preds = preds[::-1] # Da vuelta al array
            for pred in preds:
                print(pred)

# La funcion center de networkx retorna varios nodos centrales
def getCenter():
    print(nx.center(graph))