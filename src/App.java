import javax.swing.*;
import javax.swing.border.LineBorder;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.*;

import java.security.*;
import java.sql.*;
import java.io.*;

class Auth {
    String jdbcUrl = "jdbc:mysql://localhost:3306/mydiary";
    String username = "root";
    String password = "20_MySQL_$=0";
    Connection connection;
    Statement stmt;
    PreparedStatement prepStmt;
    ResultSet result;

    Auth() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method to close the connection when it's no longer needed
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public int isThereUsers() {
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("select emailID from users where status=1");

            int rowCount = 0;
            while (result.next()) {
                rowCount++;
            }

            if (rowCount == 0) {
                // It means there are no users with status = 1
                JOptionPane.showMessageDialog(null, "Please Register yourself first!", "No Account Found",
                        JOptionPane.INFORMATION_MESSAGE);
                return 0;

            } else {
                // It means there is user, with status=1
                return 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int existingUser(String email) {
        try {
            prepStmt = connection
                    .prepareStatement("SELECT EXISTS (SELECT 1 FROM users WHERE emailID = ?) as existUser");
            prepStmt.setString(1, email);
            result = prepStmt.executeQuery();

            result.next();
            if (result.getInt("existUser") == 0) {
                // It menas user not exist
                return 0;
            } else {
                // It means user exist
                return 1;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return 1;

    }

    public int addUser(String email, String password) {

        // String passString = hashPasswordSHA1(new String(password.getPassword()));
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("Update users SET status=0");
            prepStmt = connection.prepareStatement("Insert Into users VALUES (?,?,1)");
            prepStmt.setString(1, email);
            prepStmt.setString(2, password);
            prepStmt.executeUpdate();
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public String getUser() {
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("select emailID from users where status=1");
            result.next();
            return result.getString("emailID");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "";
    }

    static String hashPasswordSHA1(String password) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] hash = sha1.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

class ThePanel {

    ThePanel() {

    }
}

public class App {
    String currentID;
    JFrame mainFrame = new JFrame("My Diary");
    // Working with JPanels
    JPanel panel;
    JPanel diaryEntries, diaryPad;
    GridBagConstraints gbc = new GridBagConstraints();

    Auth authenticated = new Auth();

    public App() throws Exception {
        SwingUtilities.invokeLater(() -> {

            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(600, 500);

            // Working with Menu Bar
            // Creating menu bars
            JMenuBar menuBar = new JMenuBar();
            // Adding menus
            JMenu fileMenu = new JMenu("File");
            JMenu accountsMenu = new JMenu("Accounts");
            JMenu helpMenu = new JMenu("Help");
            JMenu exportMenu = new JMenu("Export");

            // File sub menus
            JMenuItem newItem = new JMenuItem("New");
            JMenuItem deleteItem = new JMenuItem("Delete");
            // JMenuItem updateItem = new JMenuItem("New");

            // Adding file sub menus to file menu
            fileMenu.add(newItem);
            fileMenu.add(deleteItem);

            // Accounts sub menu
            JMenuItem switchAccountItem = new JMenuItem("Switch Account");
            JMenuItem addAccountItem = new JMenuItem("Add Account");
            JMenuItem removeAccountItem = new JMenuItem("Remove Account");

            // Adding Accounts sub menu to Accounts menu
            accountsMenu.add(switchAccountItem);
            accountsMenu.add(addAccountItem);
            accountsMenu.add(removeAccountItem);

            // Help Sub menu
            JMenuItem docsItem = new JMenuItem("Documentation");

            // Adding Help sub menu to Help menu
            helpMenu.add(docsItem);

            // Export sub menu
            JMenuItem exportTxtItem = new JMenuItem("as .txt");
            JMenuItem exportDocsItem = new JMenuItem("as .docs");
            JMenuItem exportRtfItem = new JMenuItem("as .rtf");

            // Adding Export sub menu to Export menu
            exportMenu.add(exportTxtItem);
            exportMenu.add(exportDocsItem);
            exportMenu.add(exportRtfItem);

            panel = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            panel.setLayout(layout);

            // Adding to menu bar
            menuBar.add(fileMenu);
            menuBar.add(accountsMenu);
            menuBar.add(helpMenu);
            menuBar.add(exportMenu);

            mainFrame.add(panel);
            mainFrame.setJMenuBar(menuBar);
            // mainFrame.pack();
            mainFrame.setVisible(true);
        });

    }

    private void checkAuth() {
        // Auth authenticated = new Auth();
        // Checking if there any users in table or not, if yes, checking status.
        if (authenticated.isThereUsers() == 0) {

            // Now, we have to redirect user to Register/login page.
            panel.removeAll();

            JLabel title = new JLabel("MyDiary Registration Page.");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(title, gbc);

            JLabel emailID = new JLabel("Email ID: ");
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(emailID, gbc);

            JTextField emailIDField = new JTextField(10);
            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(emailIDField, gbc);

            JLabel password = new JLabel("Password: ");
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(password, gbc);

            JPasswordField passwordField = new JPasswordField(10);
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(passwordField, gbc);

            JButton submitButton = new JButton("Submit");
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(submitButton, gbc);

            // Add an ActionListener to the JButton
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (validateForm(emailIDField, passwordField) == 1) {
                        int isExistingUser = authenticated.existingUser(emailIDField.getText());
                        if (isExistingUser == 1) {
                            JOptionPane.showMessageDialog(mainFrame, "Given User already exists.");
                        } else {
                            String passString = Auth.hashPasswordSHA1(new String(passwordField.getPassword()));
                            int statusAddUser = authenticated.addUser(emailIDField.getText(), passString);
                            if (statusAddUser == 1) {
                                String greetMessage = String.format("Welcome %s to My Diary App.",
                                        emailIDField.getText());
                                JOptionPane.showMessageDialog(mainFrame, greetMessage);
                            } else {

                                JOptionPane.showMessageDialog(mainFrame,
                                        "Something went wrong! Please try again, if problem persists, contact developer Abhishek Roka on GitHub.");
                            }
                        }

                    }

                }
            });

        } else {
            currentID = authenticated.getUser();

            panel.removeAll();
            makeApp();

        }
        // Repaint the panel to show the updated contents
        panel.revalidate();
        panel.repaint();
    }

    

    public void makeApp() {
        JScrollPane scrollPane;
        // Currently working here
        GridBagConstraints gbc = new GridBagConstraints();        

        String[] dates = {"hello","bye"};
        JComboBox<String> list = new JComboBox<>(dates);
        
        panel.add(list);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JTextArea textArea = new JTextArea(24,50);
        scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, gbc);
        
        
    }

    private int validateForm(JTextField email, JPasswordField password) {
        // checking whether email is empty or not
        // email = email.toLowerCase();
        if (email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter your EMAIL ID.");
            return 0;
        } else if (!email.getText().toLowerCase().endsWith("@gmail.com")) {
            JOptionPane.showMessageDialog(mainFrame, "Only Gmail Accounts are accepted.");
            return 0;
        } else if (password.getPassword().length == 0) {
            JOptionPane.showMessageDialog(mainFrame, "Please enter your EMAIL ID.");
            return 0;
        } else {

        }
        return 1;
    }

    public void printThings() {
        System.out.println("Hello, World!");
    }

    public static void main(String[] args) throws Exception {
        App myDiary = new App();
        myDiary.checkAuth();

        // System.out.println("Hello, World!");
    }
}
