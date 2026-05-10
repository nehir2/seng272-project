import java.util.ArrayList;


public class QDimension {
    private String name;
    private int coefficient;
    private ArrayList<Metric> metrics;

    public QDimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric m) {
        metrics.add(m);
    }


    public double getDimensionScore() {
        double sumWeighted = 0;
        int sumCoeff = 0;
        for (Metric m : metrics) {
            sumWeighted += m.getScore() * m.getCoefficient();
            sumCoeff += m.getCoefficient();
        }
        if (sumCoeff == 0) return 0;
        return sumWeighted / sumCoeff;
    }


    public String getName()                  { return name; }
    public int getCoefficient()              { return coefficient; }
    public ArrayList<Metric> getMetrics()    { return metrics; }
}
