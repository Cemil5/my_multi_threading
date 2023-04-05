package filteredOnes.udemy.a01_threadCreation;/*
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

/**
 * Threads Creation - Part 2. Thread Inheritance
 * https://www.udemy.com/java-multithreading-concurrency-performance-optimization
 */
/*
Let's say I want to design a secure vault where I'm planning to store my money and I want to see how long it would take
the hackers to break into the vault. By guessing my code, we will have a few hacker threads trying to brute force my code concurrently.
In addition to that, we're going to have a police thread. That thread is going to come to our rescue by counting down 10 seconds.
And if the hackers haven't broken into the vault by then and ran away with the money, the policemen is going to arrest them
while counting down the police thread is going to show us the progress of its arrival.
 */

import java.util.List;
import java.util.Random;

class a04_CaseStudy_Vault {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = List.of(
                new AscendingHackerThread(vault),
                new DescendingHackerThread(vault),
                new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Vault {
        private final int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            return this.password == guess;
        }
    }


    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }


    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }


    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(i);
            }

            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
