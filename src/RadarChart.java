import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class RadarChart extends JPanel {

    private ArrayList<String> labels;
    private ArrayList<Double> values;
    private static final double MAX_VAL = 5.0;

    private static final Color COLOR_GRID  = new Color(203, 213, 225);
    private static final Color COLOR_FILL  = new Color(37, 99, 235, 60);
    private static final Color COLOR_LINE  = new Color(37, 99, 235);
    private static final Color COLOR_DOT   = new Color(30, 58, 138);

    public RadarChart(ArrayList<String> labels, ArrayList<Double> values) {
        this.labels = labels;
        this.values = values;
        setBackground(Color.WHITE);
        setPreferredSize(new java.awt.Dimension(380, 320));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (labels == null || labels.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = Math.min(cx, cy) - 55;
        int n = labels.size();


        g2.setStroke(new BasicStroke(0.8f));
        for (int level = 1; level <= 5; level++) {
            double r = radius * level / 5.0;
            Polygon poly = makePolygon(cx, cy, r, n);
            g2.setColor(COLOR_GRID);
            g2.drawPolygon(poly);


            if (level == 1 || level == 3 || level == 5) {
                g2.setFont(new Font("Arial", Font.PLAIN, 9));
                g2.setColor(Color.GRAY);
                g2.drawString(String.valueOf(level), cx + 3, (int)(cy - r + 4));
            }
        }


        g2.setColor(COLOR_GRID);
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(-90 + 360.0 * i / n);
            int px = (int)(cx + radius * Math.cos(angle));
            int py = (int)(cy + radius * Math.sin(angle));
            g2.drawLine(cx, cy, px, py);
        }


        int[] dataX = new int[n];
        int[] dataY = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(-90 + 360.0 * i / n);
            double r = radius * Math.min(values.get(i), MAX_VAL) / MAX_VAL;
            dataX[i] = (int)(cx + r * Math.cos(angle));
            dataY[i] = (int)(cy + r * Math.sin(angle));
        }


        g2.setColor(COLOR_FILL);
        g2.fillPolygon(dataX, dataY, n);


        g2.setColor(COLOR_LINE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawPolygon(dataX, dataY, n);


        g2.setColor(COLOR_DOT);
        for (int i = 0; i < n; i++) {
            g2.fillOval(dataX[i] - 4, dataY[i] - 4, 8, 8);
        }


        g2.setFont(new Font("Arial", Font.BOLD, 11));
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(-90 + 360.0 * i / n);
            int lx = (int)(cx + (radius + 22) * Math.cos(angle));
            int ly = (int)(cy + (radius + 22) * Math.sin(angle));

            String label = labels.get(i);
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(label);


            int textX = lx - tw / 2;
            if (lx > cx + radius / 2)      textX = lx;
            else if (lx < cx - radius / 2)  textX = lx - tw;

            g2.setColor(new Color(30, 58, 138));
            g2.drawString(label, textX, ly + 4);
        }
    }


    private Polygon makePolygon(int cx, int cy, double r, int n) {
        Polygon poly = new Polygon();
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(-90 + 360.0 * i / n);
            poly.addPoint((int)(cx + r * Math.cos(angle)), (int)(cy + r * Math.sin(angle)));
        }
        return poly;
    }
}