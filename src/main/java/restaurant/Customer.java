package restaurant;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
public class Customer extends Thread {
    private int customerId;
    private List<Table> tables;
    private List<Door> doors;
    private Cashier cashier;
    private Semaphore semaphore;
    private boolean served;
    // Initialize the customer
    public Customer(int customerId, List tables, List doors, Cashier cashier) {
        this.customerId = customerId;
        this.tables = tables;
        this.doors = doors;
        this.cashier = cashier;
        served = false;
// This will make customer wait to be served
        semaphore = new Semaphore(0);
    }
    // Return a string representation
    @Override
    public String toString() {
        return "Customer " + customerId;
    }
    // Check if customer has been served
    public boolean isServed() {
        return served;
    }
    // Mark customer as served
    public void setServed(boolean served) {
        this.served = served;
    }
    // Signal the customer to stop waiting
    public void signal() {
        semaphore.release();
    }
    // Start customer, choose the best table, wait to get served
// and then pay afterwards
    @Override
    public void run() {
        try {
            Random random = new Random();
// Customer enters one of the doors
            Door chosenDoor = doors.get(random.nextInt(doors.size()));
            chosenDoor.enter(this);
// Look for the best table
            Table chosenTable = tables.get(random.nextInt(tables.size()));
            System.out.println(this + " initially chooses " + chosenTable);
            if (chosenTable.getQueueSize() > 7) {
// If line is long, look for another table that is shorter
                for (Table table : tables) {
                    if (table != chosenTable && table.getQueueSize() < chosenTable.getQueueSize()) {
                        chosenTable = table;
                    }
                }
                System.out.println(this + " chooses " + chosenTable + " as backup because initial table has long queue");
            }
// Go to the chosen table, and wait to be served
            chosenTable.addCustomer(this);
            semaphore.acquire();
// Once food arrives starts eating
            System.out.println(this + " is now eating");
            Thread.sleep(random.nextInt(1000) + 200);
            System.out.println(this + " is finished eating");
// Done eating, then leave the table
            chosenTable.removeSeatedCustomer(this);
// Pay for the bill
            cashier.acceptPayment(this);
// Customer leaves the restaurant
            System.out.println(this + " leaves restaurant");
            Main.signalCustomerExit();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
