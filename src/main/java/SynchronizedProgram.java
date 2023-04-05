public class SynchronizedProgram {

    public static void main(String[] args) {
        final Object lock = new Object();
        ThreadA a = new ThreadA(lock);
        ThreadB b = new ThreadB(lock);
        a.start();
        b.start();
    }
}

class ThreadA extends Thread {
    private final Object lock;
    public ThreadA(Object lock) {
        this.lock = lock;
    }

    public void run() {
        synchronized (lock) {
            System.out.println("ThreadA acquired the lock");
            try {
                lock.wait(); // ThreadA is waiting for the lock to be released
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadA got the notification and resumed its execution");
        }
    }
}

class ThreadB extends Thread {
    private final Object lock;
    public ThreadB(Object lock) {
        this.lock = lock;
    }

    public void run() {
        synchronized (lock) {
            System.out.println("ThreadB acquired the lock");
            lock.notify(); // ThreadB releases the lock and notifies ThreadA to resume execution
            System.out.println("ThreadB released the lock");
        }
    }
}

