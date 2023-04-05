package java_guides.race_condition;

public class CounterDemo {

    public static void main(String[] args) throws InterruptedException {
        Count counter = new Count();

        Thread t1 = new Thread(() -> {
            System.out.println("t1 started");
            for (int i = 0; i < 25; i++) {
                System.out.print(" t1 : " + counter.increment());
            }
            System.out.println(" \nt1 stored : " + counter.value());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 started");
            for (int i = 0; i < 25; i++) {
                System.out.print(" t2 : " + counter.increment());
            }
            System.out.println(" \nt2 stored : " + counter.value());
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("counter = " + counter.value());
        /*
This program creates two threads (t1 and t2) that increment a shared variable count 10,000 times each.
However, because the increment operation is not atomic, there is a possibility for a race condition to occur,
where the two threads interfere with each other's operations and the final value of count is not what is expected.

In this case, the expected final value of count should be 20,000 (10,000 from each thread),
but because of the race condition, the actual value may be lower or higher than this.

To fix the race condition, we can use synchronization mechanisms like locks or atomic operations
to ensure that only one thread can access the shared variable at a time.
         */
    }
}

class Count {
    private int c = 0;

    public int increment() {
        return  ++c;
    }

    public int decrement() {
        return --c;
    }

    public int value() {
        return c;
    }
}