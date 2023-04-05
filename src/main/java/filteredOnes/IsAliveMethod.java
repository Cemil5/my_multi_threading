package filteredOnes;

public class IsAliveMethod {
}

class IsAlive extends Thread {
    public static int amount = 0;

    public static void main(String[] args) throws InterruptedException {
        IsAlive thread = new IsAlive();
        thread.start();
        // Wait for the thread to finish
        while (thread.isAlive()) {
            System.out.println("Waiting...");
        }
        // Update amount and print its value
        thread.join();
        System.out.println("After custom thread termination: " + amount);
        amount++;
        System.out.println("Main: " + amount);
    }

    public void run() {
        ++amount;
        System.out.println("while running : " + ++amount);
    }
}
