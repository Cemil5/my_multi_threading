package filteredOnes;

public class MyClass {
    private static int counter = 0;

    public synchronized static void incrementCounter() {
        counter++;
    }
}

