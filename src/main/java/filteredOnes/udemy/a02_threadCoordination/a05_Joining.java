package filteredOnes.udemy.a02_threadCoordination;/*
 * MIT License
 *
 * Copyright (c) 2019 Michael Pogrebinsky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Joining Threads
 * https://www.udemy.com/java-multithreading-concurrency-performance-optimization
 */
public class a05_Joining {
    // we want to calculate the factorial of the given numbers
    public static void main(String[] args) throws InterruptedException {
        // first try with small numbers like:
//        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);
        // to see the effects of Daemon thread and join with time-out, use this:
        List<Long> inputNumbers = Arrays.asList(100000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true);   // if we want to stop the application after main thread finishes its tasks.
            thread.start();
        }

        for (Thread thread : threads) {
//            thread.join();          // if we want to wait for all the results
            thread.join(2000);    // if we want to ignore any result which takes more than 2 seconds and get the ready results
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }

        /*
        take away:
        - don't rely on the order of execution
        - always use thread coordination
        - design the code for worst case scenario, threads may take unreasonably long time depending on input : use join(..) with a time
        - stop the thread (using daemon) if it's not done in time
         */
    }

    public static class FactorialThread extends Thread {
        private final long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
