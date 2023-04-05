package filteredOnes;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started.");
        // Define the behavior of the thread
        // This method will be called when the thread starts
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " started.");
        MyThread myThread = new MyThread();
        myThread.start();

        myThread.interrupt();
        Thread.yield();
    }
}




