import java.util.ArrayList;


public class Scenario {
    private String name;
    private ArrayList<QDimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(QDimension d) {
        dimensions.add(d);
    }

    public String getName()                       { return name; }
    public ArrayList<QDimension> getDimensions()   { return dimensions; }

    @Override
    public String toString() { return name; }
}