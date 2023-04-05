package filteredOnes;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockExample implements Runnable {

    private final Lock lock = new ReentrantLock();

    public void run() {
        lock.lock();
        try {
            // critical section of code
        } finally {
            lock.unlock();
        }
    }
}

