package org.example.Model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * The Graph class generates comparison graphs based on the BenchmarkData of three different programming languages.
 * It uses JFreeChart library to create line charts for various benchmark metrics.
 */
public class Graph {
    private BenchmarkData cppData;
    private BenchmarkData javaData;
    private BenchmarkData pythonData;

    /**
     * Initializes Graph with BenchmarkData for C++, Java, and Python languages.
     */
    public Graph() {
        cppData = new BenchmarkData("CppBM.txt");
        javaData = new BenchmarkData("JavaBM.txt");
        pythonData = new BenchmarkData("PythonBM.txt");
    }

    /**
     * Generates comparison graphs for various benchmark metrics (Memory Allocation, Memory Access, Thread Creation,
     * Thread Context Switch, Thread Migration) based on data from three different programming languages.
     */
    public void generateGraphs() {
        JFrame frame = new JFrame("Benchmark Comparison Graphs");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 5));

        frame.add(new ChartPanel(createChart("Memory Allocation", "Memory Allocation Comparison", "Iterations", cppData.getMemoryAllocation(), javaData.getMemoryAllocation(), pythonData.getMemoryAllocation())));
        frame.add(new ChartPanel(createChart("Memory Access", "Memory Access Comparison", "Iterations", cppData.getMemoryAccess(), javaData.getMemoryAccess(), pythonData.getMemoryAccess())));
        frame.add(new ChartPanel(createChart("Thread Creation", "Thread Creation Comparison", "Iterations", cppData.getThreadCreation(), javaData.getThreadCreation(), pythonData.getThreadCreation())));
        frame.add(new ChartPanel(createChart("Thread Context Switch", "Thread Context Switch Comparison", "Iterations", cppData.getThreadContextSwitch(), javaData.getThreadContextSwitch(), pythonData.getThreadContextSwitch())));
        frame.add(new ChartPanel(createChart("Thread Migration", "Thread Migration Comparison", "Iterations", cppData.getThreadMigration(), javaData.getThreadMigration(), pythonData.getThreadMigration())));

        frame.pack();
        frame.setVisible(true);
    }
    /**
     * Creates a JFreeChart line chart for the specified benchmark metric.
     *
     * @param category    The category of the benchmark metric (e.g., Memory Allocation, Memory Access, etc.).
     * @param title       The title of the chart.
     * @param xAxisLabel  The label for the x-axis of the chart.
     * @param cppData     ArrayList containing data for C++ benchmark.
     * @param javaData    ArrayList containing data for Java benchmark.
     * @param pythonData  ArrayList containing data for Python benchmark.
     * @return A JFreeChart line chart representing the comparison of the benchmark metric for the three languages.
     */
    private JFreeChart createChart(String category, String title, String xAxisLabel, ArrayList<Double> cppData, ArrayList<Double> javaData, ArrayList<Double> pythonData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < cppData.size(); i++) {
            dataset.addValue(cppData.get(i), "Cpp", String.valueOf(i));
        }

        for (int i = 0; i < javaData.size(); i++) {
            dataset.addValue(javaData.get(i), "Java", String.valueOf(i));
        }

        for (int i = 0; i < pythonData.size(); i++) {
            dataset.addValue(pythonData.get(i), "Python", String.valueOf(i));
        }

        return ChartFactory.createLineChart(title, xAxisLabel, category, dataset);
    }

}
