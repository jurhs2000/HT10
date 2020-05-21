/**
 * @author Julio Herrera 19402
 * 
 * Clase que simula una ciudad para los nodos del grafo
 * 
 * Esta clase simplemente contiene el nombre de la ciudad y sobreescribe
 * los metodos "equals" y "toString"
 */
public class City {

    /**
     * Nombre de la ciudad
     */
    private String name;

    public City() {}

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * El nuevo equals compara los nombres de la ciudad sin importar
     * si estan en mayusculas o minusculas
     */
    @Override
    public boolean equals(Object obj) {
        return name.toLowerCase().equals(((City)obj).name.toLowerCase());
    }
    
    @Override
    public String toString() {
        return getName();
    }
}