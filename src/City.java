public class City {

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
    
    @Override
    public boolean equals(Object obj) {
        return name.toLowerCase().equals(((City)obj).name.toLowerCase());
    }
    
    public String toString() {
        return getName();
    }
}