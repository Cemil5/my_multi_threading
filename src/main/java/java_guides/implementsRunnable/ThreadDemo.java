package java_guides.implementsRunnable;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new JavaThread(), "Java Thread").start();
        new Thread(new CThread(), "Python Thread").start();
        new Thread(new PythonThread(), "C Thread").start();
    }
}
