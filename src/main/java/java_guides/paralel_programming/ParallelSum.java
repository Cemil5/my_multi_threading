package java_guides.paralel_programming;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelSum {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            numbers.add(i);
        }

        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("numThr: " + numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<SumTask> tasks = new ArrayList<>();

        int chunkSize = numbers.size() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? numbers.size() : (i + 1) * chunkSize;
            List<Integer> subList = numbers.subList(start, end);
            SumTask task = new SumTask(subList);
            tasks.add(task);
        }

//        executor.invokeAll(tasks);    // chatGPT'den aldım çalışmadı.
        tasks.forEach(
                SumTask::run
        );
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        int sum = 0;
        for (SumTask task : tasks) {
            sum += task.getResult();
        }

        System.out.println("Sum = " + sum);
    }
}

class SumTask implements Runnable{
    private List<Integer> numbers;
    private int result;

    public SumTask(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        result = sum;
    }

}

