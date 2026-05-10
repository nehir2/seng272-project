import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


public class Step1Profile extends JPanel {

    private JTextField txtUsername;
    private JTextField txtSchool;
    private JTextField txtSession;
    private AppState state;

    public Step1Profile(AppState state) {
        this.state = state;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel title = new JLabel("Step 1: Profile");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(30, 58, 138));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        JLabel sub = new JLabel("Please enter your information to begin the measurement session.");
        sub.setForeground(Color.GRAY);
        gbc.gridy = 1;
        add(sub, gbc);


        JSeparator sep = new JSeparator();
        gbc.gridy = 2; gbc.insets = new Insets(4, 8, 16, 8);
        add(sep, gbc);
        gbc.insets = new Insets(8, 8, 8, 8);


        gbc.gridwidth = 1;
        gbc.gridy = 3; gbc.gridx = 0; gbc.weightx = 0;
        add(makeLabel("Username *"), gbc);
        txtUsername = makeField("Enter your username");
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtUsername, gbc);


        gbc.gridy = 4; gbc.gridx = 0; gbc.weightx = 0;
        add(makeLabel("School *"), gbc);
        txtSchool = makeField("Enter your school name");
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtSchool, gbc);


        gbc.gridy = 5; gbc.gridx = 0; gbc.weightx = 0;
        add(makeLabel("Session Name *"), gbc);
        txtSession = makeField("Enter session name ");
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtSession, gbc);


        gbc.gridy = 6; gbc.gridx = 0; gbc.weighty = 1;
        add(new JPanel() {{ setOpaque(false); }}, gbc);
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setPreferredSize(new java.awt.Dimension(130, 30));
        return lbl;
    }

    private JTextField makeField(String placeholder) {
        JTextField tf = new JTextField(22);
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setToolTipText(placeholder);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }


    public boolean validateFields() {
        if (txtUsername.getText().trim().isEmpty()) {
            showWarning("Please enter your username to continue.");
            txtUsername.requestFocus();
            return false;
        }
        if (txtSchool.getText().trim().isEmpty()) {
            showWarning("Please enter your school name to continue.");
            txtSchool.requestFocus();
            return false;
        }
        if (txtSession.getText().trim().isEmpty()) {
            showWarning("Please enter a session name to continue.");
            txtSession.requestFocus();
            return false;
        }

        state.username    = txtUsername.getText().trim();
        state.school      = txtSchool.getText().trim();
        state.sessionName = txtSession.getText().trim();
        return true;
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }
}