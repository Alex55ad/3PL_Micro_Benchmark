package org.example.Model;
import java.io.*;
import java.util.ArrayList;

/**
 * The BenchmarkData class processes benchmark data from a file and provides access to different benchmark metrics
 * for a programming language.
 */
public class BenchmarkData {
    private ArrayList<Double> memoryAllocation;
    private ArrayList<Double> memoryAccess;
    private ArrayList<Double> threadCreation;
    private ArrayList<Double> threadContextSwitch;
    private ArrayList<Double> threadMigration;
    private String fileName;

    /**
     * Constructs a GraphLogic object with the specified file name and initializes
     * ArrayLists to store the split sections of numerical values.
     *
     * @param fileName The name of the file to be processed.
     */
    public BenchmarkData(String fileName) {
        this.fileName = fileName;
        memoryAllocation = new ArrayList<>();
        memoryAccess = new ArrayList<>();
        threadCreation = new ArrayList<>();
        threadContextSwitch = new ArrayList<>();
        threadMigration = new ArrayList<>();
        splitFile();

    }

    /**
     * Splits the content of the file into different ArrayLists based on identified sections.
     * Each section contains numerical values and is stored in a respective ArrayList.
     */
    private void splitFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentSection = "";

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                if (line.matches("[A-Za-z\\s]+")) {
                    currentSection = line.trim().toLowerCase();
                    continue; // Skip lines containing non-numerical characters
                }

                double value = Double.parseDouble(line.trim());
                switch (currentSection) {
                    case "memory allocation" -> memoryAllocation.add(value);
                    case "memory access" -> memoryAccess.add(value);
                    case "thread creation" -> threadCreation.add(value);
                    case "thread context switch" -> threadContextSwitch.add(value);
                    case "thread migration" -> threadMigration.add(value);
                    default -> {
                    }
                    // If the section is not recognized, ignore the value
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the specifications of the user's computer, including the operating system (OS),
     * CPU model, and the quantity of RAM memory.
     *
     * @return A formatted string containing the computer specifications:
     *         "OS: [OS Name] | CPU: [CPU Model] | RAM: [Memory Amount in GB]"
     */
    public static String getComputerSpecs() {
        String  ram = getRAMInfo();
        String cpu = getCPUName();
        String os = System.getProperty("os.name");
        return String.format("OS: %s | CPU: %s | RAM: %s", os, cpu, ram);
    }

    /**
     * Retrieves the total RAM memory available on the system.
     *
     * @return A string representation of the total RAM in GB.
     */
    private static String getRAMInfo() {
        long ramSize = 0;

        try {
            com.sun.management.OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
            ramSize = osBean.getTotalMemorySize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double ramGB = ramSize / (1024.0 * 1024 * 1024); // Convert bytes to GB
        return String.format("%.2f GB", ramGB);
    }

    /**
     * Retrieves the CPU name by executing the 'wmic cpu get name' command in the command prompt.
     * Parses the output to extract the CPU name from the command output.
     *
     * @return A String representing the CPU name.
     *         If CPU information is unavailable or an error occurs during retrieval, returns "CPU information unavailable".
     */
    private static String getCPUName() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "wmic cpu get name");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder cpuName = new StringBuilder();
            boolean cpuNameFound = false;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && cpuNameFound) {
                    cpuName.append(line.trim());
                    break;
                }
                if (!cpuNameFound && line.toLowerCase().contains("name")) {
                    cpuNameFound = true;
                }
            }

            process.waitFor(); // Wait for the process to complete
            process.destroy(); // Release resources

            return cpuName.toString().trim();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "CPU information unavailable";
    }

    public ArrayList<Double> getMemoryAllocation() {
        return memoryAllocation;
    }

    public ArrayList<Double> getMemoryAccess() {
        return memoryAccess;
    }

    public ArrayList<Double> getThreadCreation() {
        return threadCreation;
    }

    public ArrayList<Double> getThreadContextSwitch() {
        return threadContextSwitch;
    }

    public ArrayList<Double> getThreadMigration() {
        return threadMigration;
    }

    public String getFileName() {
        return fileName;
    }
}
