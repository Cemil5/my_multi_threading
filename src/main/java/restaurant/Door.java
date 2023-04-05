package restaurant;

import java.util.concurrent.Semaphore;
public class Door {
    private int doorId;
    private Semaphore semaphore;
    // Initialize the door
    public Door(int doorId) {
        this.doorId = doorId;
        semaphore = new Semaphore(1);
    }
    // 1 customer at a time to enter a door
    public void enter(Customer customer) {
        try {
            semaphore.acquire();
            System.out.println(customer + " entered using " + this);
            semaphore.release();
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }
    // Return a string representation of a door
    @Override
    public String toString() {
        return "Door " + doorId;
    }
}