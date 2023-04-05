package filteredOnes;

class ThreadMethods implements Runnable {
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

class ClassA extends Thread {
    public void run() {
        System.out.println("Start Thread A ....");
        for (int i = 1; i <= 5; i++) {
            if (i == 1) {
                System.out.println("Thread A yielded");
                Thread.yield();
            }
            System.out.println("From Thread A: i = " + i);
        }
        System.out.println("... Exit Thread A");
    }
}

class ClassB extends Thread {
    public void run() {
        System.out.println("Start Thread B ....");
        for (int j = 1; j <= 5; j++) {
            System.out.println("From Thread B: j = " + j);
            if (j == 2) stop();
        }
        System.out.println("... Exit Thread B");
    }
}

class ClassC extends Thread {
    public void run() {
        System.out.println("Start Thread C ....");
        for (int k = 1; k <= 5; k++) {
            System.out.println("From Thread B: j = " + k);
            if (k == 3) {
                try {
                    sleep(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("... Exit Thread C");
    }
}

class ThreadControl {
    public static void main(String[] args) {
        ClassA t1 = new ClassA();
        ClassB t2 = new ClassB();
        ClassC t3 = new ClassC();
        t1.start();
        t2.start();
        t3.start();
        System.out.println("... End of executuion ");
    }

}

class MainTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " started.");
        System.out.println("before my custom thread starts Thread.activeCount() = " + Thread.activeCount());
        ThreadMethods myRunnable = new ThreadMethods();
        Thread myThread = new Thread(myRunnable);
        myThread.setPriority(8);

        Thread myThread2 = new Thread(myRunnable);
        System.out.println("myThread.getState() = " + myThread.getState());

        myThread.setPriority(Thread.MIN_PRIORITY);
        myThread.start();
        System.out.println("myThread.getState() = " + myThread.getState());

        System.out.println("before my custom thread starts Thread.activeCount() = " + Thread.activeCount());
        System.out.println("myThread.getState() = " + myThread.getState());
//        myThread.start();
        System.out.println("myThread2 = " + myThread2);
        System.out.println("myThread.getState() = " + myThread.getState());
        System.out.println("Thread.activeCount() = " + Thread.activeCount());
    }
}




