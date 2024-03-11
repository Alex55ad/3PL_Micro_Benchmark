import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JavaBenchmark {
    static Runnable dummyOp = () -> {
        new Random().nextInt();
    };

    static long memoryAllocationBM(int arrayLen) {
        long startT = System.nanoTime();
        int[] arr = new int[arrayLen];
        long endT = System.nanoTime();
        arr = null;
        return endT - startT;
    }

    static long memoryAccessBM(int arrayLen) {
        int[] arr = new int[arrayLen];
        long startT = System.nanoTime();
        for(int i = 0; i < arrayLen; i++)
            arr[i] = i;
        long endT = System.nanoTime();
        return endT - startT;
    }

    static long threadCreationBM(int nrThreads) {
        List<Thread> threads = new ArrayList<>();
        long starT = System.nanoTime();
        for (int i = 0; i < nrThreads; i++) {
            Thread newThread = new Thread(dummyOp);
            threads.add(newThread);
            newThread.start();
        }
        long endT = System.nanoTime();
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return (endT - starT);
    }

    static long threadContextBM(int nrThreads) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < nrThreads; i++) {
            Thread newThread = new Thread(dummyOp);
            threads.add(newThread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        long starT = System.nanoTime();
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endT = System.nanoTime();
        return (endT - starT);
    }

    static long threadMigrationBM(int nrThreads) {
        List<Thread> threads = new ArrayList<>();
        long starT = System.nanoTime();
        for (int i = 0; i < nrThreads; i++) {
            Thread newThread = new Thread(dummyOp);
            threads.add(newThread);
            // threads.add(new Thread(() -> {
            // ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            // bean.setThreadAffinityMask(Thread.currentThread().getId(), 1L); // Set thread to run on core 0
            //   dummyOp();
            //}));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endT = System.nanoTime();
        return (endT - starT);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid program call");
           return;
        }
        String testcase = args[0];
        int arrayLen, nrThreads, iterations;
        try {
            FileWriter output = new FileWriter("JavaBM.txt");

            if (testcase.equals("testcase1")) {
                arrayLen = 1000000;
                nrThreads = 100;
                iterations = 100;

                output.write("Memory allocation\n");
                for (int i = 0; i < iterations; i++) {
                    double result = memoryAllocationBM(arrayLen);
                    output.write(result + "\n");
                }

                output.write("Memory access\n");
                for (int i = 0; i < iterations; i++) {
                    double result = memoryAccessBM(arrayLen);
                    output.write(result + "\n");
                }

                output.write("Thread creation\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadCreationBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }

                output.write("Thread context switch\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadContextBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }

                output.write("Thread migration\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadMigrationBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }
            }
            if (testcase.equals("testcase2")) {
                arrayLen = 2000000;
                nrThreads = 150;
                iterations = 250;

                output.write("Memory allocation\n");
                for (int i = 0; i < iterations; i++) {
                    double result = memoryAllocationBM(arrayLen);
                    output.write(result + "\n");
                }

                output.write("Memory access\n");
                for (int i = 0; i < iterations; i++) {
                    double result = memoryAccessBM(arrayLen);
                    output.write(result + "\n");
                }

                output.write("Thread creation\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadCreationBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }

                output.write("Thread context switch\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadContextBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }

                output.write("Thread migration\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadMigrationBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }
            }
            if (testcase.equals("testcase3")) {
                arrayLen = 5000000;
                nrThreads = 200;
                iterations = 400;

                output.write("Memory allocation\n");
                for (int i = 0; i < iterations; i++) {
                    double result = memoryAllocationBM(arrayLen);
                    output.write(result + "\n");
                }

                output.write("Memory access\n");
                for (int i = 0; i < iterations; i++) {
                    double result = memoryAccessBM(arrayLen);
                    output.write(result + "\n");
                }

                output.write("Thread creation\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadCreationBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }

                output.write("Thread context switch\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadContextBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }

                output.write("Thread migration\n");
                for (int i = 0; i < iterations; i++) {
                    double result = threadMigrationBM(nrThreads);
                    output.write(result/nrThreads + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}