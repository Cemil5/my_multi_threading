package java_guides.concurrent_programming;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentExample {

    public static void main(String[] args) {
        // Create an ExecutorService with a fixed pool of 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Submit 10 tasks to the executor
        for (int i = 0; i < 10; i++) {
            executor.submit(new Task(i));
        }

        // Shutdown the executor once all tasks are complete
        executor.shutdown();
    }

    static class Task implements Runnable {
        private int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        public void run() {
            System.out.println("Task " + taskId + " is running.");
        }
    }
}

