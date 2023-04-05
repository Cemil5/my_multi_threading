package java_guides.implementsRunnable;

public class RunnableWithLambdaDemo {
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            System.out.println("Starting thread: " + Thread.currentThread().getName());
            // 2000 milliseconds = 2 seconds
            for (int i = 0; i < 10; i++) {
                System.out.println(" Java programming .. " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Completed thread: " + Thread.currentThread().getName());
        }, "Java thread").start();

        new Thread(() -> {
            System.out.println("Starting thread: " + Thread.currentThread().getName());
            // 2000 milliseconds = 2 seconds
            for (int i = 0; i < 10; i++) {
                System.out.println(" Python programming .. " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Completed thread: " + Thread.currentThread().getName());
        }, "Python thread").start();

        new Thread(() -> {
            System.out.println("Starting thread: " + Thread.currentThread().getName());
            // 2000 milliseconds = 2 seconds
            for (int i = 0; i < 10; i++) {
                System.out.println(" C programming .. " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Completed thread: " + Thread.currentThread().getName());
        }, "C thread").start();
    }
}
