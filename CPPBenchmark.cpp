#include <iostream>
#include <chrono>
#include <thread>
#include <fstream>
#include <vector>
#include <Windows.h>
#include <math.h>

#define dummyOperand 10000;

//thread dummy operation
auto dummyOP = []() {
    rand();
};

auto memoryAllocationBM(int arrayLen) {
    auto startT = std::chrono::steady_clock::now();
    int* arr = new int[arrayLen];   // allocate an array of given length
    auto endT = std::chrono::steady_clock::now();
    std::chrono::duration<double> duration = endT - startT;
    delete[] arr;
    return duration * pow(10, 9);
}

auto memoryAccessBM(int arrayLen) {
    int* arr = new int[arrayLen];
    auto startT = std::chrono::steady_clock::now();
    for (int i = 0; i < arrayLen; i++) {
        arr[i] = i;
    }
    auto endT = std::chrono::steady_clock::now();
    delete[] arr;
    std::chrono::duration<double> duration = endT - startT;
    return duration * pow(10, 9);
}

auto threadCreationBM(int nrThreads) {
    std::vector<std::thread> threads;
    auto startT = std::chrono::steady_clock::now();
    for (int i = 0; i < nrThreads; i++) {
        threads.emplace_back(dummyOP);
    }
    for (auto& thread : threads) {
        thread.join();
    }
    auto endT = std::chrono::steady_clock::now();
    std::chrono::duration<double> duration = endT - startT;
    return duration * pow(10, 9);
}


// The following 2 functions are W.I.P

auto threadContextBM(int nrThreads) {
    std::vector<std::thread> threads;
    for (int i = 0; i < nrThreads; i++) {
        threads.emplace_back(dummyOP);
    }
    auto startT = std::chrono::steady_clock::now();
    for (auto& thread : threads) {
        thread.join();
    }
    auto endT = std::chrono::steady_clock::now();
    std::chrono::duration<double> duration = endT - startT;
    return duration * pow(10, 9);
}

auto threadMigrationBM(int nrThreads) {
    std::vector<std::thread> threads;
    auto startT = std::chrono::steady_clock::now();
    for (int i = 0; i < nrThreads; i++) {
        threads.emplace_back([]() {
            SetThreadAffinityMask(GetCurrentThread(), 1); // Set thread to run on core 0
            dummyOP();
            });
    }
    for (auto& thread : threads) {
        thread.join();
    }
    auto endT = std::chrono::steady_clock::now();
    std::chrono::duration<double> duration = endT - startT;
    return duration * pow(10,9);
}


int main(int argc, char* argv[])
{
   if (argc != 2) {
        std::cerr << "Invalid call"<<"\n";
        return 1;
   }
    
    std::string testcase = argv[1];
    int arrayLen, nrThreads,iterations;
    std::ofstream output("CppBM.txt");

    if (testcase == "testcase1") {
        arrayLen = 100000;
        nrThreads = 100;
        iterations = 100;
        output << "Memory allocation" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = memoryAllocationBM(arrayLen).count();
            output << result << std::endl;
        }

        output << "Memory access" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = memoryAccessBM(arrayLen).count();
            output << result << std::endl;
        }

        output << "Thread creation" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadCreationBM(nrThreads).count();
            output << result/nrThreads << std::endl;
        }

        output << "Thread context switch" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadContextBM(nrThreads).count();
            output << result/nrThreads << std::endl;
        }

        output << "Thread migration" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadMigrationBM(nrThreads).count();
            output << result/nrThreads << std::endl;
        }
    }
    if (testcase == "testcase2") {
        arrayLen = 1000000;
        nrThreads = 150;
        iterations = 250;
        output << "Memory allocation" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = memoryAllocationBM(arrayLen).count();
            output << result << std::endl;
        }

        output << "Memory access" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = memoryAccessBM(arrayLen).count();
            output << result << std::endl;
        }

        output << "Thread creation" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadCreationBM(nrThreads).count();
            output << resul/nrThreads << std::endl;
        }

        output << "Thread context switch" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadContextBM(nrThreads).count();
            output << result/nrThreads << std::endl;
        }

        output << "Thread migration" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadMigrationBM(nrThreads).count();
            output << result/nrThreads << std::endl;
        }
    }
    if (testcase == "testcase3") {
        arrayLen = 2000000;
        nrThreads = 200;
        iterations = 400;
        output << "Memory allocation" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = memoryAllocationBM(arrayLen).count();
            output << result << std::endl;
        }

        output << "Memory access" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = memoryAccessBM(arrayLen).count();
            output << result << std::endl;
        }

        output << "Thread creation" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadCreationBM(nrThreads).count();
            output << result/nrThreads << std::endl;
        }

        output << "Thread context switch" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadContextBM(nrThreads).count();
            output << result / nrThreads << std::endl;
        }

        output << "Thread migration" << std::endl;
        for (int i = 0; i < iterations; i++) {
            double result = threadMigrationBM(nrThreads).count();
            output << result / nrThreads << std::endl;
        }
    }
}
