/**
 * @author Julio Herrera
 * 
 * Prueba unitaria de la calse que implementa el grafo
 * y aplica el algoritmo de floyd marshall
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class FloydGraphTest {

	/**
     * Con esta prueba verificaremos que jUnit este funcionando
     */
	@Test
    public void pruebaJUnit() {
        assertTrue(true);
    }
	
	/**
     * Prueba del insercion de vertice
     */
    @Test
    public void addVertexTest() {
    	Graph<City> graph = new FloydGraph<>(4, City.class);
    	graph.addVertex(new City("Antigua"));
    	assertTrue(graph.containsVertex(new City("Antigua")));
    }
    
    /**
     * Prueba de insercion de arista
     */
    @Test
    public void addEdgeTest() {
    	Graph<City> graph = new FloydGraph<>(4, City.class);
    	graph.addEdge(new City("Antigua"), new City("VillaNueva"), 7);
        assertEquals(graph.getDirectEdge(new City("Antigua"), new City("VillaNueva")), Integer.valueOf(7));
    }
    
    /**
     * Prueba de algoritmo de floyd marshall
     */
    @Test
    public void calculateNewEdgesTest() {
    	Graph<City> graph = new FloydGraph<>(4, City.class);
    	graph.addEdge(new City("Mixco"), new City("Antigua"), 8);
    	graph.addEdge(new City("Antigua"), new City("Escuintla"), 1);
    	graph.addEdge(new City("Mixco"), new City("SantaLucia"), 1);
    	graph.addEdge(new City("Escuintla"), new City("Mixco"), 4);
    	graph.addEdge(new City("SantaLucia"), new City("Antigua"), 2);
    	graph.addEdge(new City("SantaLucia"), new City("Escuintla"), 9);
    	graph.calculateNewEdges();
    	assertEquals(graph.getEdge(new City("Mixco"), new City("Antigua")), Integer.valueOf(3));
    }
    
    /**
     * Prueba para obtener el centro del grafo
     */
    @Test
    public void getCenterTest() {
    	Graph<City> graph = new FloydGraph<>(4, City.class);
    	graph.addEdge(new City("Mixco"), new City("Antigua"), 8);
    	graph.addEdge(new City("Antigua"), new City("Escuintla"), 1);
    	graph.addEdge(new City("Mixco"), new City("SantaLucia"), 1);
    	graph.addEdge(new City("Escuintla"), new City("Mixco"), 4);
    	graph.addEdge(new City("SantaLucia"), new City("Antigua"), 2);
    	graph.addEdge(new City("SantaLucia"), new City("Escuintla"), 9);
    	graph.calculateNewEdges();
    	assertEquals(graph.getCenter(), new City("Escuintla"));
    }

}
