package filteredOnes;

import java.util.concurrent.*;

public class MyCallable implements Callable<Integer> {
    public Integer call() throws Exception {
        // code to be executed by the thread
        return 42;
    }
}

class MyApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable callable = new MyCallable();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(callable);
        Integer result = future.get();

        System.out.println("result = " + result);
    }

}

