import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Adım 3: Seçilen senaryonun boyut ve metriklerini tablo halinde gösterir.
 * Bu adım salt-okunurdur.
 */
public class Step3Plan extends JPanel {

    private AppState state;
    private JPanel contentPanel; // Tabloların dinamik ekleneceği panel

    public Step3Plan(AppState state) {
        this.state = state;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        buildUI();
    }

    private void buildUI() {
        // Başlık
        JLabel title = new JLabel("Step 3: Plan Measurement");
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

    /**
     * Seçilen senaryo değiştiğinde tabloları yeniden oluşturur.
     * MainFrame her "Next" tıklamasında bu metodu çağırır.
     */
    public void refresh() {
        contentPanel.removeAll();

        if (state.selectedScenario == null) return;

        // Senaryo bilgisi
        JLabel scenLabel = new JLabel("Scenario: " + state.selectedScenario.getName());
        scenLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        scenLabel.setForeground(Color.GRAY);
        scenLabel.setBorder(BorderFactory.createEmptyBorder(4, 16, 12, 16));
        contentPanel.add(scenLabel);

        // Her boyut için ayrı tablo
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

        // Boyut başlığı
        JLabel dimTitle = new JLabel(dim.getName() + "  (Coefficient: " + dim.getCoefficient() + ")");
        dimTitle.setFont(new Font("Arial", Font.BOLD, 14));
        dimTitle.setForeground(new Color(30, 58, 138));
        dimTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        block.add(dimTitle, BorderLayout.NORTH);

        // Tablo
        String[] cols = {"Metric", "Coefficient", "Direction", "Range", "Unit"};
        ArrayList<Metric> metrics = dim.getMetrics();
        Object[][] data = new Object[metrics.size()][5];
        for (int i = 0; i < metrics.size(); i++) {
            Metric m = metrics.get(i);
            data[i][0] = m.getName();
            data[i][1] = m.getCoefficient();
            data[i][2] = m.getDirectionText();
            data[i][3] = m.getRangeText();
            data[i][4] = m.getUnit();
        }

        JTable table = makeStyledTable(data, cols);
        block.add(new JScrollPane(table) {{
            setPreferredSize(new java.awt.Dimension(0, table.getRowHeight() * metrics.size() + 28));
            setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        }}, BorderLayout.CENTER);

        return block;
    }

    static JTable makeStyledTable(Object[][] data, String[] cols) {
        JTable table = new JTable(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setGridColor(new Color(226, 232, 240));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setIntercellSpacing(new java.awt.Dimension(8, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(239, 246, 255));
        header.setForeground(new Color(30, 58, 138));

        // Satır renk değişimi
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        return table;
    }
}