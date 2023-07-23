import javax.swing.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;


public class App {
    public App() throws Exception{
        JFrame mainFrame = new JFrame("My Diary");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 500);
        

        // Creating menu bars
        JMenuBar menuBar = new JMenuBar();
        //Adding menus
        JMenu fileMenu = new JMenu("File");
        JMenu accountsMenu = new JMenu("Accounts");
        JMenu helpMenu = new JMenu("Help");
        JMenu exportMenu = new JMenu("Export");

        // File sub menus
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem deleteItem = new JMenuItem("Delete");
        // JMenuItem updateItem = new JMenuItem("New");

        //Adding file sub menus to file menu
        fileMenu.add(newItem);
        fileMenu.add(deleteItem);

        //Accounts sub menu
        JMenuItem switchAccountItem = new JMenuItem("Switch Account");
        JMenuItem addAccountItem = new JMenuItem("Add Account");
        JMenuItem removeAccountItem = new JMenuItem("Remove Account");

        //Adding Accounts sub menu to Accounts menu
        accountsMenu.add(switchAccountItem);
        accountsMenu.add(addAccountItem);
        accountsMenu.add(removeAccountItem);

        // Help Sub menu
        JMenuItem docsItem = new JMenuItem("Documentation");

        //Adding Help sub menu to Help menu
        helpMenu.add(docsItem);

        //Export sub menu
        JMenuItem exportTxtItem = new JMenuItem("as .txt");
        JMenuItem exportDocsItem = new JMenuItem("as .docs");
        JMenuItem exportRtfItem = new JMenuItem("as .rtf");

        //Adding Export sub menu to Export menu
        exportMenu.add(exportTxtItem);
        exportMenu.add(exportDocsItem);
        exportMenu.add(exportRtfItem);

        
        

        



        menuBar.add(fileMenu);
        menuBar.add(accountsMenu);
        menuBar.add(helpMenu);
        menuBar.add(exportMenu);
        mainFrame.setJMenuBar(menuBar);

        mainFrame.setVisible(true);

    }
    
    public static void main(String[] args) throws Exception {
        App myDiary = new App();
        
        System.out.println("Hello, World!");
    }
}

