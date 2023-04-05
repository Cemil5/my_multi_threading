package restaurant;

import java.util.Random;
import java.util.concurrent.Semaphore;
// Place where waiters will prepare food. Only one waiter at a time
// can use this kitchen
public class Kitchen {
    private Semaphore semaphore;
    private Random random;
    // Initialize the kitchen
    public Kitchen() {
        semaphore = new Semaphore(1);
        random = new Random();
    }
    // Waiter will use this kitchen, if it is being used by another
// waiter then it will wait.
    public void use(Waiter waiter) {
        try {
            semaphore.acquire();
            System.out.println(waiter + " is now using the kitchen");
            Thread.sleep(random.nextInt(500) + 100);
            System.out.println(waiter + " is finished using the kitchen");
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
