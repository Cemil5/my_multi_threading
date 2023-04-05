package java_guides.extendsToThread;

public class JavaExtendingToThread extends Thread {

    @Override
    public void run() {

        System.out.println("Starting thread: " + Thread.currentThread().getName());
        // 2000 milliseconds = 2 seconds
        for (int i = 0; i < 10; i++) {
            System.out.println(" Java programming ..");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Completed thread: " + Thread.currentThread().getName());
    }

}
