package restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class Main {
    private static final int NUM_CUSTOMERS = 40;
    // Semaphore to protect the number of customers who exited the restaurant
    private static Semaphore customerExitSemaphore = new Semaphore(1);
    private static int numCustomerExits = 0;
    // Semaphore that forced this main class to wait until last customer exits restaurant
    private static Semaphore lastCustomerExitSemaphore = new Semaphore(0);

    // Add +1 to exit
    public static void signalCustomerExit() {
        try {
            customerExitSemaphore.acquire();
            numCustomerExits++;
            if (numCustomerExits >= NUM_CUSTOMERS) {
// Signal that the last customer has exited
                lastCustomerExitSemaphore.release();
            }
            customerExitSemaphore.release();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    // Initialize the threads and start them
    public static void main(String[] args) throws Exception {
// There will be 3 tables
        List<Table> tables = new ArrayList<>();
        tables.add(new Table("A"));
        tables.add(new Table("B"));
        tables.add(new Table("C"));
// There will be 1 kitchen shared by all waiters
        Kitchen kitchen = new Kitchen();
// There will be 3 waiters
        List<Waiter> waiters = new ArrayList<>();
        waiters.add(new Waiter(1, kitchen));
        waiters.add(new Waiter(2, kitchen));
        waiters.add(new Waiter(3, kitchen));
// There will be 1 waiter assigned for each table
        tables.get(0).setWaiter(waiters.get(0));
        tables.get(1).setWaiter(waiters.get(1));
        tables.get(2).setWaiter(waiters.get(2));
        waiters.get(0).setTable(tables.get(0));
        waiters.get(1).setTable(tables.get(1));
        waiters.get(2).setTable(tables.get(2));
// There will be 2 doors only in the restaurant for entry of customers
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(1));
        doors.add(new Door(2));
// There will be 1 cashier for payment of customers
        Cashier cashier = new Cashier();
// There will be 40 customers
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= NUM_CUSTOMERS; i++) {
            customers.add(new Customer(i, tables, doors, cashier));
        }
// Start all waiter and customer threads
        for (Waiter waiter : waiters) {
            waiter.start();
        }
        for (Customer customer : customers) {
            customer.start();
        }
// Wait for the last customer
        lastCustomerExitSemaphore.acquire();
// Signal all waiters to stop waiting
        for (Waiter waiter : waiters) {
            waiter.signalOrder();
        }
    }
}
