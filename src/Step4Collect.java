import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;


public class Step4Collect extends JPanel {

    private AppState state;
    private JPanel contentPanel;

    public Step4Collect(AppState state) {
        this.state = state;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        buildUI();
    }

    private void buildUI() {
        JLabel title = new JLabel("Step 4: Collect Data");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(30, 58, 138));
        title.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 16));
        add(title, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
    }

    public void refresh() {
        contentPanel.removeAll();
        if (state.selectedScenario == null) return;

        for (QDimension dim : state.selectedScenario.getDimensions()) {
            contentPanel.add(buildDimensionBlock(dim));
            contentPanel.add(Box.createVerticalStrut(16));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel buildDimensionBlock(QDimension dim) {
        JPanel block = new JPanel(new BorderLayout(0, 6));
        block.setBackground(Color.WHITE);
        block.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

        JLabel dimTitle = new JLabel(dim.getName() + "  (Coefficient: " + dim.getCoefficient() + ")");
        dimTitle.setFont(new Font("Arial", Font.BOLD, 14));
        dimTitle.setForeground(new Color(30, 58, 138));
        block.add(dimTitle, BorderLayout.NORTH);

        String[] cols = {"Metric", "Direction", "Range", "Value", "Score (1–5)", "Coeff / Unit"};
        ArrayList<Metric> metrics = dim.getMetrics();
        Object[][] data = new Object[metrics.size()][6];

        for (int i = 0; i < metrics.size(); i++) {
            Metric m = metrics.get(i);
            data[i][0] = m.getName();
            data[i][1] = m.getDirectionText();
            data[i][2] = m.getRangeText();
            data[i][3] = formatValue(m.getValue());
            data[i][4] = m.getScore();
            data[i][5] = m.getCoefficient() + " / " + m.getUnit();
        }

        JTable table = Step3Plan.makeStyledTable(data, cols);


        table.getColumnModel().getColumn(4).setCellRenderer(new ScoreCellRenderer());

        block.add(new JScrollPane(table) {{
            setPreferredSize(new java.awt.Dimension(0, table.getRowHeight() * metrics.size() + 28));
            setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        }}, BorderLayout.CENTER);

        return block;
    }


    private String formatValue(double v) {
        if (v == Math.floor(v)) return String.valueOf((int) v);
        return String.format("%.1f", v);
    }


    static class ScoreCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(CENTER);
            setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
            if (!isSelected && value instanceof Double) {
                double score = (Double) value;
                if (score >= 4.5)      setBackground(new Color(220, 252, 231)); // yeşil
                else if (score >= 3.0) setBackground(new Color(254, 249, 195)); // sarı
                else                   setBackground(new Color(254, 226, 226)); // kırmızı
            }
            return this;
        }
    }
}