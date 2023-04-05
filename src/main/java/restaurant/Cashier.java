package restaurant;

import java.util.concurrent.Semaphore;
public class Cashier {
    private Semaphore semaphore;
    // Initialize the cashier
    public Cashier() {
        semaphore = new Semaphore(1);
    }
    // Accept customer's payment
    public void acceptPayment(Customer customer) {
        try {
            semaphore.acquire();
            System.out.println(customer + " paid the bill");
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
