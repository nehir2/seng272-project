import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;


public class Step5Analyse extends JPanel {

    private AppState state;
    private JPanel contentPanel;

    public Step5Analyse(AppState state) {
        this.state = state;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Step 5: Analyse Results");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(30, 58, 138));
        title.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 16));
        add(title, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        add(new JScrollPane(contentPanel) {{
            setBorder(BorderFactory.createEmptyBorder());
        }}, BorderLayout.CENTER);
    }

    public void refresh() {
        contentPanel.removeAll();
        if (state.selectedScenario == null) return;

        ArrayList<QDimension> dims = state.selectedScenario.getDimensions();


        contentPanel.add(makeSectionTitle("5a. Dimension Scores"));
        JPanel barsPanel = new JPanel(new GridBagLayout());
        barsPanel.setBackground(Color.WHITE);
        barsPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < dims.size(); i++) {
            QDimension dim = dims.get(i);
            double score = dim.getDimensionScore();


            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel nameLabel = new JLabel(dim.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
            nameLabel.setPreferredSize(new java.awt.Dimension(200, 24));
            barsPanel.add(nameLabel, gbc);


            gbc.gridx = 1; gbc.weightx = 1;
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue((int)(score / 5.0 * 100));
            bar.setPreferredSize(new java.awt.Dimension(300, 22));
            bar.setForeground(scoreColor(score));
            bar.setBackground(new Color(226, 232, 240));
            bar.setBorderPainted(false);
            barsPanel.add(bar, gbc);


            gbc.gridx = 2; gbc.weightx = 0;
            JLabel scoreLabel = new JLabel(String.format("%.2f / 5.00", score));
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 12));
            scoreLabel.setForeground(scoreColor(score));
            scoreLabel.setPreferredSize(new java.awt.Dimension(90, 24));
            barsPanel.add(scoreLabel, gbc);
        }
        contentPanel.add(barsPanel);
        contentPanel.add(Box.createVerticalStrut(12));


        contentPanel.add(makeSectionTitle("5b. Radar Chart "));
        ArrayList<String> radarLabels = new ArrayList<>();
        ArrayList<Double> radarValues = new ArrayList<>();
        for (QDimension d : dims) {
            radarLabels.add(d.getName());
            radarValues.add(d.getDimensionScore());
        }
        RadarChart radar = new RadarChart(radarLabels, radarValues);
        radar.setAlignmentX(Component.LEFT_ALIGNMENT);
        radar.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        contentPanel.add(radar);
        contentPanel.add(Box.createVerticalStrut(12));


        contentPanel.add(makeSectionTitle("5c. Gap Analysis"));
        contentPanel.add(buildGapPanel(dims));
        contentPanel.add(Box.createVerticalStrut(20));

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private JPanel buildGapPanel(ArrayList<QDimension> dims) {
        QDimension worst = dims.get(0);
        for (QDimension d : dims) {
            if (d.getDimensionScore() < worst.getDimensionScore()) worst = d;
        }

        double score = worst.getDimensionScore();
        double gap   = 5.0 - score;
        String level = qualityLevel(score);
        Color  levelColor = scoreColor(score);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(254, 242, 242));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(252, 165, 165), 1, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 140));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panel.add(bold("  Weakest Dimension: " + worst.getName()), gbc);
        panel.add(plain("Score: " + String.format("%.2f", score) + " / 5.00"), gbc);
        panel.add(plain("Gap:   " + String.format("%.2f", gap) + " points below target"), gbc);

        JLabel levelLabel = new JLabel("Quality Level: " + level);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 13));
        levelLabel.setForeground(levelColor);
        panel.add(levelLabel, gbc);

        panel.add(italic("This dimension has the lowest score and requires the most improvement."), gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createEmptyBorder(4, 16, 4, 16));
        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }

    private String qualityLevel(double score) {
        if (score >= 4.5) return "Excellent";
        if (score >= 3.5) return "Good";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor";
    }

    private Color scoreColor(double score) {
        if (score >= 4.0) return new Color(22, 163, 74);  // yeşil
        if (score >= 3.0) return new Color(202, 138, 4);  // sarı
        return new Color(185, 28, 28);                     // kırmızı
    }

    private JLabel makeSectionTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));
        lbl.setForeground(new Color(30, 58, 138));
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 16, 4, 16));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JLabel bold(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        return l;
    }

    private JLabel plain(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        return l;
    }

    private JLabel italic(String text) {
        JLabel l = new JLabel("<html><i>" + text + "</i></html>");
        l.setFont(new Font("Arial", Font.ITALIC, 12));
        l.setForeground(Color.GRAY);
        return l;
    }
}
