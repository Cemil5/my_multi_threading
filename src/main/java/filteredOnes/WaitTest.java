package filteredOnes;

import java.util.Optional;

class WaitTest {
    private boolean ready = false;
    private int data;

    public synchronized void setData(int data) {
        while (ready) {
            try {
                wait(); // wait until data is consumed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.data = data;
        ready = true;
        notifyAll(); // notify waiting threads that data is available
    }

    public synchronized int getData() {
        while (!ready) {
            try {
                wait(); // wait until data is available
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ready = false;
        notifyAll(); // notify waiting threads that data has been consumed
        return data;
    }
}

class Producer implements Runnable {
    private final WaitTest test;

    public Producer(WaitTest test) {
        this.test = test;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            test.setData(i);
            System.out.println("Produced: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private final WaitTest test;

    public Consumer(WaitTest test) {
        this.test = test;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            int data = test.getData();
            System.out.println("Consumed: " + data);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class WaitRunner {
    public static void main(String[] args) {
        WaitTest test = new WaitTest();
        Thread producerThread = new Thread(new Producer(test));
        Thread consumerThread = new Thread(new Consumer(test));
        producerThread.start();
        consumerThread.start();
        Thread.yield();
    }
}

