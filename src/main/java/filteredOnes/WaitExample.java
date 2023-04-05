package filteredOnes;

public class WaitExample {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(new Waiting(lock));
        Thread t2 = new Thread(new Waiting(lock));
        t1.start();
        t2.start();
        Thread t3 = new Thread( ()-> {} );
        t3.start();
        Thread.sleep(1000);
        if (t1.getState().equals(Thread.State.WAITING)) {
            lock.notifyAll();
            t3.notifyAll();
        }
        System.out.println("t2.getState() = " + t2.getState());
        t3.notifyAll();
    }
}

class Waiting implements Runnable {
    private Object lock;

    public Waiting(Object lock) {
        this.lock = lock;
    }

    public void run() {
        synchronized (lock) {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting");
                lock.wait();
                System.out.println(Thread.currentThread().getName() + " is notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

