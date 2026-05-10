import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Step2Define extends JPanel {

    private AppState state;


    private JRadioButton rbProduct;
    private JRadioButton rbProcess;


    private JRadioButton rbHealth;
    private JRadioButton rbEducation;


    private JPanel scenarioPanel;
    private ButtonGroup scenarioGroup;
    private ArrayList<JRadioButton> scenarioButtons;

    public Step2Define(AppState state) {
        this.state = state;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        scenarioButtons = new ArrayList<>();
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;


        JLabel title = new JLabel("Step 2: Define Quality Dimensions");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(30, 58, 138));
        gbc.gridy = 0;
        add(title, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 1; gbc.insets = new Insets(2, 12, 12, 12);
        add(sep, gbc);
        gbc.insets = new Insets(8, 12, 8, 12);


        gbc.gridy = 2;
        add(makeSectionPanel("2a. Quality Type",
                "Product Quality", "Process Quality", true), gbc);


        gbc.gridy = 3;
        add(makeSectionPanel("2b. Mode",
                "Health", "Education", false), gbc);


        gbc.gridy = 4;
        scenarioPanel = new JPanel(new GridLayout(0, 1, 4, 4));
        scenarioPanel.setBackground(new Color(248, 250, 252));
        scenarioPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(203, 213, 225)),
                        "2c. Scenario",
                        TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 13), new Color(30, 58, 138)
                ),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        add(scenarioPanel, gbc);


        gbc.gridy = 5; gbc.weighty = 1;
        add(new JPanel() {{ setOpaque(false); }}, gbc);


        refreshScenarios();
    }


    private JPanel makeSectionPanel(String title,
                                    String opt1, String opt2,
                                    boolean isQualityType) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(203, 213, 225)),
                        title,
                        TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 13), new Color(30, 58, 138)
                ),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        ButtonGroup bg = new ButtonGroup();
        JRadioButton rb1 = makeRadio(opt1);
        JRadioButton rb2 = makeRadio(opt2);
        bg.add(rb1);
        bg.add(rb2);
        rb1.setSelected(true);

        ActionListener listener = e -> {
            if (isQualityType) {
                state.qualityType = rb1.isSelected() ? "Product" : "Process";
            } else {
                state.mode = rb1.isSelected() ? "Health" : "Education";
            }
            refreshScenarios();
        };
        rb1.addActionListener(listener);
        rb2.addActionListener(listener);

        if (isQualityType) {
            rbProduct = rb1;
            rbProcess = rb2;
        } else {
            rbHealth     = rb1;
            rbEducation  = rb2;
        }

        panel.add(rb1);
        panel.add(rb2);
        return panel;
    }

    private JRadioButton makeRadio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setFont(new Font("Arial", Font.PLAIN, 13));
        rb.setBackground(new Color(248, 250, 252));
        rb.setFocusPainted(false);
        return rb;
    }


    private void refreshScenarios() {
        scenarioPanel.removeAll();
        scenarioButtons.clear();
        scenarioGroup = new ButtonGroup();

        ArrayList<Scenario> scenarios = ScenarioData.getScenarios(state.mode, state.qualityType);
        for (Scenario sc : scenarios) {
            final Scenario selected = sc;
            JRadioButton rb = makeRadio(selected.getName());
            scenarioGroup.add(rb);
            scenarioPanel.add(rb);
            scenarioButtons.add(rb);
            rb.addActionListener(e -> state.selectedScenario = selected);
        }


        if (!scenarioButtons.isEmpty()) {
            scenarioButtons.get(0).setSelected(true);
            state.selectedScenario = scenarios.get(0);
        }

        scenarioPanel.revalidate();
        scenarioPanel.repaint();
    }


    public boolean validateFields() {
        if (state.selectedScenario == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a scenario to continue.",
                    "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
