import sys
import time
import random
import threading
import multiprocessing


def dummyOP(): 
    random.randint(0,100)

def memoryAllocationBM(array_len):
    start_time = time.time_ns()
    arr = [0] * array_len # create a list of given length
    end_time = time.time_ns()
    del arr[::]
    return (end_time - start_time)


def memoryAccessBM(array_len):
    arr = [0] * array_len
    start_time = time.time_ns()
    arr = list(range(array_len))
    end_time = time.time_ns()
    del arr[::]
    return (end_time - start_time)

def threadCreationBM(nr_threads):
    threads = []
    start_time = time.time_ns()
    for i in range(nr_threads):
        thread = threading.Thread(target=dummyOP)
        thread.start()
        threads.append(thread)
        end_time = time.time_ns()
    for thread in threads:
        thread.join()
    
    return (end_time - start_time)

def threadContextSwitchBM(nr_threads):
    threads = []
    for i in range(nr_threads):
        thread = threading.Thread(target=dummyOP)
        thread.start()
        threads.append(thread)
    start_time = time.time_ns()
    for thread in threads:
        thread.join()
    end_time = time.time_ns()
    return (end_time - start_time)

def threadMigrationBM(nr_threads):
    processes = []
    start_time = time.time()
    process = multiprocessing.Process(target=dummyOP)
    process.start()
    processes.append(process)

    for process in processes:
        process.join()

    end_time = time.time()
    duration = end_time - start_time
    return duration


def main(args):
    if len(args) != 2:
        print("Invalid call")
        return
    testcase = args[1]

    if testcase == "testcase1":
        array_len = 1000000
        nr_threads = 100
        iterations = 100
        with open("PythonBM.txt", "w") as output:
            output.write("Memory allocation\n")
            for i in range(iterations):
                result = memoryAllocationBM(array_len)
                output.write(str(result) + "\n")

            output.write("Memory access\n")
            for i in range(iterations):
                result = memoryAccessBM(array_len)
                output.write(str(result) + "\n")

            output.write("Thread creation\n")
            for i in range(iterations):
                result = threadCreationBM(nr_threads)
                output.write(str(result/nr_threads) + "\n")

            output.write("Thread context switch\n")
            for i in range(iterations):
                result = threadContextSwitchBM(nr_threads)
                output.write(str(result/nr_threads) + "\n")

            output.write("Thread migration\n")
            for i in range(iterations):
                result = threadMigrationBM(nr_threads)
                output.write(str(result) + "\n")

    if testcase == "testcase2":
        array_len = 2000000
        nr_threads = 150
        iterations = 250
        with open("PythonBM.txt", "w") as output:
            output.write("Memory allocation\n")
            for i in range(iterations):
                result = memoryAllocationBM(array_len)
                output.write(str(result) + "\n")

            output.write("Memory access\n")
            for i in range(iterations):
                result = memoryAccessBM(array_len)
                output.write(str(result) + "\n")

            output.write("Thread creation\n")
            for i in range(iterations):
                result = threadCreationBM(nr_threads)
                output.write(str(result/nr_threads) + "\n")

            output.write("Thread context switch\n")
            for i in range(iterations):
                result = threadContextSwitchBM(nr_threads)
                output.write(str(result/nr_threads) + "\n")

            output.write("Thread migration\n")
            for i in range(iterations):
                result = threadMigrationBM(nr_threads)
                output.write(str(result) + "\n")

    if testcase == "testcase3":
        array_len = 5000000
        nr_threads = 200
        iterations = 400
        with open("PythonBM.txt", "w") as output:
            output.write("Memory allocation\n")
            for i in range(iterations):
                result = memoryAllocationBM(array_len)
                output.write(str(result) + "\n")

            output.write("Memory access\n")
            for i in range(iterations):
                result = memoryAccessBM(array_len)
                output.write(str(result) + "\n")

            output.write("Thread creation\n")
            for i in range(iterations):
                result = threadCreationBM(nr_threads)
                output.write(str(result/nr_threads) + "\n")

            output.write("Thread context switch\n")
            for i in range(iterations):
                result = threadContextSwitchBM(nr_threads)
                output.write(str(result/nr_threads) + "\n")

            output.write("Thread migration\n")
            for i in range(iterations):
                result = threadMigrationBM(nr_threads)
                output.write(str(result) + "\n")
    return
        
if __name__ == "__main__":
    main(sys.argv)