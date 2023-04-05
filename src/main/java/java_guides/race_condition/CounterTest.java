package java_guides.race_condition;

class CounterTest {
    public static void main(String[] args) {
        CountNow countNow = new CountNow();
        Thread t1 = new Thread(countNow);
        Thread t2 = new Thread(countNow);
        t1.start();
        t2.start();
    }

}

class CountNow implements Runnable {
    Counter counter = new Counter();

    @Override
    public void run() {
        String  name = Thread.currentThread().getName();
        System.out.println(name+ " started !");
        for (int i = 0; i < 25; i++) {
            counter.increment();
            System.out.println(name + " : " + counter.getCount());
        }
        System.out.println(" \n"+ name + " : " + counter.getCount());
    }
}

class Counter {
    private int count;

    public void increment() {
        int temp = count;
        temp = temp + 1;
        count = temp;
    }

    public int getCount() {
        return count;
    }
}

