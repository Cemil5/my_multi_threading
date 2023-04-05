package restaurant;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Waiter extends Thread {
    private int waiterId;
    private Semaphore semaphore;
    private Table table;
    private Kitchen kitchen;
    // Initialize the waiter
    public Waiter(int waiterId, Kitchen kitchen) {
        this.waiterId = waiterId;
        this.kitchen = kitchen;
        semaphore = new Semaphore(0);
    }
    // Initiaize the waiter's table
    public void setTable(Table table) {
        this.table = table;
    }
    // Signal is recieved from a customer that sat on
// the table it is servicing. This means waiter
// has to move and get an order
    public void signalOrder() {
        semaphore.release();
    }
    // Entry point of the waiter to execute independently
    @Override
    public void run() {
        try {
            while (true) {
// Wait for a customer from the table it is assigned
                semaphore.acquire();
// Service the next customer from the table
                Customer customer = table.serveNextCustomer();
// If there are no customer to serve next, then stop now
                if (customer == null) {
                    break;
                }
// Use the kitchen
                System.out.println(this + " is now serving " + customer);
                kitchen.use(this);
// Wait for the food outside the kitchen to get the order
                System.out.println(this + " is waiting outside kitchen for order to be ready...");
                Random random = new Random();
                Thread.sleep(random.nextInt(1000) + 300);
// Go back to the kitchen to grab food
                System.out.println(this + " will now go back to kitchen to get customer's order.");
                kitchen.use(this);
                customer.setServed(true);
                System.out.println(this + " has served " + customer);
                customer.signal();
// Process next customer...
            }
            System.out.println(this + " left the restaurant");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    // Return a string represenation of a waiter
    @Override
    public String toString() {
        return "Waiter " + waiterId;
    }
}