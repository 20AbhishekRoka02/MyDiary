import javax.swing.*;

// import sun.security.mscapi.CKeyStore.MY;

import java.awt.*;

/**
 * MyFrame
 */
public class MyFrame {

    MyFrame() {
        JFrame frame = new JFrame("GridBagLayout Positioning Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        JPanel subPanel1 = new JPanel();
        subPanel1.setBackground(Color.RED);
        JPanel subPanel2 = new JPanel();
        subPanel2.setBackground(Color.BLUE);

        GridBagConstraints constraints = new GridBagConstraints();

        // Component: Subpanel 1
        constraints.gridx = 0; // Column 0
        constraints.gridy = 0; // Row 0
        constraints.fill = GridBagConstraints.BOTH; // Fill the cell horizontally and vertically
        constraints.weightx = 1.0; // Expand horizontally
        constraints.weighty = 1.0; // Expand vertically
        constraints.insets = new Insets(5, 5, 5, 5); // Padding
        frame.add(subPanel1, constraints);

        // Component: Subpanel 2
        constraints.gridx = 1; // Column 1
        constraints.gridy = 0; // Row 0
        constraints.fill = GridBagConstraints.BOTH; // Fill the cell horizontally and vertically
        constraints.weightx = 1.0; // Expand horizontally
        constraints.weighty = 1.0; // Expand vertically
        constraints.insets = new Insets(5, 5, 5, 5); // Padding
        frame.add(subPanel2, constraints);

        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        
    }
}