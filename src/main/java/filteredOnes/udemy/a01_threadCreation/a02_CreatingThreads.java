package filteredOnes.udemy.a01_threadCreation;

class a02_CreatingThreads {

    public static void main(String[] args) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //Code that will run in  a new thread
//                System.out.println("we are now in thread: " + Thread.currentThread().getName()
//                        + "\t || thread priority is: " + Thread.currentThread().getPriority());
//            }
//        });
        Thread thread = new NewThread();
        thread.start();

    }

    private static class NewThread extends Thread{

        @Override
        public void run(){
            //Code that will run in  a new thread
            System.out.println("Hello from: " + this.getName());
        }
    }


}
