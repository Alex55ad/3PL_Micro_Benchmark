package org.example.View;

import org.example.Control.Controller;
import org.example.Model.BenchmarkData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Gui class represents the graphical user interface for the Micro-Benchmarking App.
 * It provides buttons to trigger benchmark tests and methods to create visual tables for collected data.
 */
public class Gui {
    private static JLabel info,info2,computerSpecs;
    /**
     * The Test case 1.
     */
    public JButton testCase1, /**
     * The Test case 2.
     */
    testCase2, /**
     * The Test case 3.
     */
    testCase3;
    private static JPanel mainPanel;
    private static JFrame mainFrame;

    /**
     * Constructor to initialize the GUI components and set up the main frame.
     */
    public Gui(){
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        mainFrame.setSize(875, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainPanel.setLayout(null);
        mainFrame.setTitle("Micro-Benchmarking App");
        ImageIcon bg = new ImageIcon(".\\background.png");
        Image img = bg.getImage();
        Image temp = img.getScaledInstance(875, 500, Image.SCALE_SMOOTH);
        bg = new ImageIcon(temp);
        JLabel back = new JLabel(bg);
        back.setLayout(null);
        back.setBounds(0, 0, 875, 500);
        Controller controller = new Controller(this);

        info = new JLabel("Available benchmarks:");
        info.setFont(new Font("Dialog", Font.BOLD, 20));
        info.setBounds(10, 20, 350, 25);
        mainPanel.add(info);

        info2 = new JLabel("<html>Benchmark info:<br>Quick benchmark: Quickest benchmark, uses less iterations and smaller data set <br>Medium benchmark: A more precise benchmark run on a bigger data set <br>Slow Benchmark: Most precise of the benchmarks but slow</html>");
        info2.setFont(new Font("Dialog", Font.BOLD, 16));
        info2.setBounds(10, 200, 850, 250);
        mainPanel.add(info2);

        computerSpecs = new JLabel(BenchmarkData.getComputerSpecs());
        computerSpecs.setFont(new Font("Dialog", Font.BOLD, 16));
        computerSpecs.setBounds(10, 50, 850, 50); // Adjust the bounds as needed
        mainPanel.add(computerSpecs);

        testCase1 = new JButton("Quick Benchmark");
        testCase1.setBackground(Color.LIGHT_GRAY);
        testCase1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        testCase1.setFont(new Font("Dialog", Font.BOLD, 20));
        testCase1.setBounds(25, 100, 250, 80);
        testCase1.addActionListener(controller);
        mainPanel.add(testCase1);

        testCase2 = new JButton("Medium Benchmark");
        testCase2.setBackground(Color.LIGHT_GRAY);
        testCase2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        testCase2.setFont(new Font("Dialog", Font.BOLD, 20));
        testCase2.setBounds(300, 100, 250, 80);
        testCase2.addActionListener(controller);
        mainPanel.add(testCase2);

        testCase3 = new JButton("Slow Benchmark");
        testCase3.setBackground(Color.LIGHT_GRAY);
        testCase3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        testCase3.setFont(new Font("Dialog", Font.BOLD, 20));
        testCase3.setBounds(575, 100, 250, 80);
        testCase3.addActionListener(controller);
        mainPanel.add(testCase3);

        mainPanel.add(back);
        mainFrame.setVisible(true);
    }


    /**
     * Creates a JTable populated with benchmark data from the provided BenchmarkData object.
     *
     * @param benchMarkData The BenchmarkData object containing benchmark information.
     * @return A JTable populated with benchmark data.
     */
    public JTable createTable(BenchmarkData benchMarkData) {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.getTableHeader().setName(benchMarkData.getFileName());

        // Add columns to the table based on data in the BenchMarkData object
        model.addColumn("Memory Allocation");
        model.addColumn("Memory Access");
        model.addColumn("Thread Creation");
        model.addColumn("Thread Context Switch");
        model.addColumn("Thread Migration");

        // Get the data from BenchMarkData object's ArrayLists
        ArrayList<Double> memoryAllocation = benchMarkData.getMemoryAllocation();
        ArrayList<Double> memoryAccess = benchMarkData.getMemoryAccess();
        ArrayList<Double> threadCreation = benchMarkData.getThreadCreation();
        ArrayList<Double> threadContextSwitch = benchMarkData.getThreadContextSwitch();
        ArrayList<Double> threadMigration = benchMarkData.getThreadMigration();

        int rowCount = memoryAllocation.size();

        // Populate the table with data from ArrayLists
        for (int i = 0; i < rowCount; i++) {
            Object[] rowData = {
                    (i < memoryAllocation.size()) ? memoryAllocation.get(i) : "",
                    (i < memoryAccess.size()) ? memoryAccess.get(i) : "",
                    (i < threadCreation.size()) ? threadCreation.get(i) : "",
                    (i < threadContextSwitch.size()) ? threadContextSwitch.get(i) : "",
                    (i < threadMigration.size()) ? threadMigration.get(i) : ""
            };
            model.addRow(rowData);
        }
        return table;
    }

    /**
     * Creates BenchmarkData objects for different benchmark files and generates tables for each file in the GUI.
     * Uses BenchmarkData objects to populate tables for PythonBM.txt, JavaBM.txt, and CppBM.txt files in the GUI.
     */
    public void tableWrapper() {
        BenchmarkData cpp = new BenchmarkData("CppBM.txt");
        BenchmarkData java = new BenchmarkData("JavaBM.txt");
        BenchmarkData python = new BenchmarkData("PythonBM.txt");

        JFrame tableFrame = new JFrame("Benchmark Tables");
        tableFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(new JLabel("Cpp Benchmark table"));
        topPanel.add(new JLabel("Java Benchmark table"));
        topPanel.add(new JLabel("Python Benchmark table"));

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.add(new JScrollPane(createTable(cpp)));
        bottomPanel.add(new JScrollPane(createTable(java)));
        bottomPanel.add(new JScrollPane(createTable(python)));

        tableFrame.add(topPanel, BorderLayout.NORTH);
        tableFrame.add(bottomPanel, BorderLayout.CENTER);

        tableFrame.pack();
        tableFrame.setVisible(true);
    }

}
