package java_guides.synchronization;

// This program is not synchronized.
class CallMe {
    void  call(final String msg) {
        System.out.print("[" + msg);
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("]");
    }
}

class Caller implements Runnable {
    protected String msg;
    protected CallMe target;
    protected Thread t;

    public Caller(final CallMe target, final String s) {
        this.target = target;
        msg = s;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        target.call(msg);
    }
}

class SynchronizedMethodExample {

    public static void main(final String[] args) {
        final CallMe target = new CallMe();
        final Caller ob1 = new Caller(target, "Hello");
        final Caller ob2 = new Caller(target, "Synchronized");
        final Caller ob3 = new Caller(target, "World");
        // wait for threads to end
        try {
            ob1.t.join();
            ob2.t.join();
            ob3.t.join();
        } catch (final InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
    /*
     As you can see, by calling sleep( ), the call( ) method allows execution to switch to another thread. This results in the mixed-up output of the three message strings. In this program, nothing exists to stop all three threads from calling the same method, on the same object, at the same time. This is known as a race condition, because the three threads are racing each other to complete the method.
This example used sleep( ) to make the effects repeatable and obvious. In most situations, a race condition is more subtle and less predictable, because you can’t be sure when the context switch will occur. This can cause a program to run right one time and wrong the next.
To fix this problem in this program, we must serialize access to call( ). That is, we must restrict its access to only one thread at a time. To do this, you simply need to proceed call( )’s definition with the keyword synchronized.
     */
}
