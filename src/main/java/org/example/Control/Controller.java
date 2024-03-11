package org.example.Control;
import org.example.Model.Graph;
import org.example.View.Gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The Controller class manages the actions triggered by the GUI's buttons
 * and orchestrates the execution of benchmark tests.
 */
public class Controller implements ActionListener {
    private Gui gui;
    private Graph graph;

    /**
     * Constructor initializing the Controller with a reference to the GUI.
     *
     * @param gui The graphical user interface to be controlled.
     */
    public  Controller(Gui gui) {
        this.gui = gui;
        this.graph = new Graph();
    }
    /**
     * Performs actions based on the event triggered by GUI buttons.
     *
     * @param e The ActionEvent triggered by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String argument;

        if (e.getSource() == gui.testCase1) {
            argument = "testcase1";
            runBenchmark(argument);
            gui.tableWrapper();
            graph.generateGraphs();

        } else if (e.getSource() == gui.testCase2) {
            argument = "testcase2";
            runBenchmark(argument);
            gui.tableWrapper();
            graph.generateGraphs();

        } else if (e.getSource() == gui.testCase3) {
            argument = "testcase2";
            runBenchmark(argument);
            gui.tableWrapper();
            graph.generateGraphs();
        }
    }

    /**
     * Executes various benchmarks in different programming languages based on the argument provided.
     *
     * @param argument The argument specifying the type of benchmark to run.
     */
    private void runBenchmark(String argument){
        String fileName, command;
        fileName = "CPPBenchmark.exe";
        command = "start CPPBenchmark.exe " + argument;
        launchBenchmark(fileName,command);

        fileName = "JavaBenchmark.java";
        command = "java JavaBenchmark.java "+ argument;
        launchBenchmark(fileName,command);

        fileName = "PythonBenchmark.py";
        command = "python PythonBenchmark.py "+ argument;
        launchBenchmark(fileName,command);
    }
    /**
     * Launches a benchmark by executing specific commands based on the provided file name and command.
     *
     * @param fileName The name of the file for benchmarking.
     * @param command  The command to execute the benchmark.
     */
    private void launchBenchmark(String fileName, String command){
        File file = new File(fileName);
        if(file.exists()){
            try{ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
                processBuilder.directory(new File(System.getProperty("user.dir")));
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Process completed successfully.");
                } else {
                    System.out.println("Process " + command + " did not complete successfully. Exit code: " + exitCode);
                }
                System.out.println(process.getErrorStream());
            }catch(IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println("File not found: " + fileName);
        }
    }

}
