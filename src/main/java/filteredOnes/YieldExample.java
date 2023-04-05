package filteredOnes;

public class YieldExample implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " executing...");
            Thread.yield(); // suggest to the scheduler to give other threads a chance to run
        }
    }

    public static void main(String[] args) {
        YieldExample yieldExample = new YieldExample();
        Thread t1 = new Thread(yieldExample, "Thread 1");
        Thread t2 = new Thread(yieldExample, "Thread 2");
        t1.start();
        t2.start();
    }
}




