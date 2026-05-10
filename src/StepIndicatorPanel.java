import javax.swing.*;
import java.awt.*;


public class StepIndicatorPanel extends JPanel {

    private static final String[] STEP_NAMES = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private static final Color COLOR_DONE    = new Color(34, 197, 94);
    private static final Color COLOR_ACTIVE  = new Color(37, 99, 235);
    private static final Color COLOR_FUTURE  = new Color(156, 163, 175);
    private static final Color BG            = new Color(248, 250, 252);

    private int currentStep;

    public StepIndicatorPanel(int currentStep) {
        this.currentStep = currentStep;
        setBackground(BG);
        setPreferredSize(new java.awt.Dimension(0, 70));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int n = STEP_NAMES.length;


        int cellW = w / n;

        for (int i = 0; i < n; i++) {
            int cx = cellW * i + cellW / 2;
            int cy = h / 2 - 5;


            Color circleColor;
            if (i < currentStep)       circleColor = COLOR_DONE;
            else if (i == currentStep) circleColor = COLOR_ACTIVE;
            else                        circleColor = COLOR_FUTURE;


            if (i > 0) {
                g2.setColor(i <= currentStep ? COLOR_DONE : COLOR_FUTURE);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(cellW * (i - 1) + cellW / 2 + 14, cy + 10, cx - 14, cy + 10);
            }


            g2.setColor(circleColor);
            g2.fillOval(cx - 12, cy - 2, 24, 24);


            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            String label = (i < currentStep) ? "✓" : String.valueOf(i + 1);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, cx - fm.stringWidth(label) / 2, cy + 15);


            g2.setColor(i == currentStep ? COLOR_ACTIVE : (i < currentStep ? COLOR_DONE : COLOR_FUTURE));
            Font nameFont = (i == currentStep)
                    ? new Font("Arial", Font.BOLD, 11)
                    : new Font("Arial", Font.PLAIN, 11);
            g2.setFont(nameFont);
            FontMetrics fm2 = g2.getFontMetrics();
            g2.drawString(STEP_NAMES[i], cx - fm2.stringWidth(STEP_NAMES[i]) / 2, cy + 38);
        }
    }
}