package filteredOnes;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started.");
        // Define the behavior of the thread
        // This method will be called when the thread starts
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class MyRunnable2 implements Runnable {
    public void run() {
        // code to be executed by the thread
    }
}



class MainRunnable {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " started.");
        System.out.println("before my custom thread starts Thread.activeCount() = " + Thread.activeCount());
        ThreadMethods myRunnable = new ThreadMethods();
        Thread myThread = new Thread(myRunnable);
        myThread.setPriority(8);
        Thread myThread2 = new Thread(myRunnable);
        myThread.setPriority(Thread.MIN_PRIORITY);
        System.out.println("before my custom thread starts Thread.activeCount() = " + Thread.activeCount());
        System.out.println("myThread.getState() = " + myThread.getState());
        myThread.start();
        System.out.println("myThread2 = " + myThread2);
        System.out.println("myThread.getState() = " + myThread.getState());
        System.out.println("Thread.activeCount() = " + Thread.activeCount());

//        ---------

        MyRunnable2 runnable = new MyRunnable2();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}




