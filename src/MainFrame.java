import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame extends JFrame {

    private static final int TOTAL_STEPS = 5;

    private AppState state;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StepIndicatorPanel indicator;


    private Step1Profile  step1;
    private Step2Define   step2;
    private Step3Plan     step3;
    private Step4Collect  step4;
    private Step5Analyse  step5;


    private JButton btnBack;
    private JButton btnNext;

    public MainFrame() {
        state = new AppState();
        initFrame();
        buildUI();
        setVisible(true);
    }

    private void initFrame() {
        setTitle("ISO/IEC 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 640);
        setMinimumSize(new java.awt.Dimension(700, 520));
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        setLayout(new BorderLayout());


        indicator = new StepIndicatorPanel(0);
        add(indicator, BorderLayout.NORTH);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        step1 = new Step1Profile(state);
        step2 = new Step2Define(state);
        step3 = new Step3Plan(state);
        step4 = new Step4Collect(state);
        step5 = new Step5Analyse(state);

        cardPanel.add(step1, "0");
        cardPanel.add(step2, "1");
        cardPanel.add(step3, "2");
        cardPanel.add(step4, "3");
        cardPanel.add(step5, "4");

        add(cardPanel, BorderLayout.CENTER);


        JPanel navPanel = buildNavPanel();
        add(navPanel, BorderLayout.SOUTH);


        getRootPane().setDefaultButton(btnNext);


        showStep(0);
    }

    private JPanel buildNavPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));


        JLabel statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        statusLabel.setForeground(Color.GRAY);
        panel.add(statusLabel, BorderLayout.WEST);


        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

        btnBack = makeNavButton("← Back", false);
        btnNext = makeNavButton("Next →", true);

        btnBack.addActionListener(e -> goBack());
        btnNext.addActionListener(e -> goNext());

        btnPanel.add(btnBack);
        btnPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        btnPanel.add(btnNext);
        panel.add(btnPanel, BorderLayout.EAST);

        return panel;
    }

    private JButton makeNavButton(String text, boolean primary) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new java.awt.Dimension(110, 36));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (primary) {
            btn.setBackground(new Color(37, 99, 235));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(75, 85, 99));
            btn.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        }
        return btn;
    }

    private void goNext() {
        int cur = state.currentStep;


        if (cur == 0 && !step1.validateFields()) return;
        if (cur == 1 && !step2.validateFields()) return;

        if (cur == TOTAL_STEPS - 1) {

            JOptionPane.showMessageDialog(this,
                    "Measurement session completed!\nThank you, " + state.username + ".",
                    "Session Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showStep(cur + 1);
        }
    }

    private void goBack() {
        if (state.currentStep > 0) {
            showStep(state.currentStep - 1);
        }
    }

    private void showStep(int step) {
        state.currentStep = step;


        if (step == 2) step3.refresh();
        if (step == 3) step4.refresh();
        if (step == 4) step5.refresh();

        cardLayout.show(cardPanel, String.valueOf(step));
        indicator.setCurrentStep(step);


        btnBack.setEnabled(step > 0);
        btnNext.setText(step == TOTAL_STEPS - 1 ? "Finish ✓" : "Next →");

        setTitle("ISO/IEC 15939 — Step " + (step + 1) + ": "
                + new String[]{"Profile", "Define", "Plan", "Collect", "Analyse"}[step]);
    }
}