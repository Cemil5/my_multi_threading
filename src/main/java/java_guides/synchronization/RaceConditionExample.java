package java_guides.synchronization;

public class RaceConditionExample {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                count++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                count++;
            }
        });

        t1.start();
        t2.start();
        Thread.sleep(0, 1 );
        System.out.println("Count1: " + count);
        t1.join();
        t2.join();

        System.out.println("Count2: " + count);
    }
}

