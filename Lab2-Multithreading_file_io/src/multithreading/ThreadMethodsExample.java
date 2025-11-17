package multithreading;

//Multithreading using thread methods

class Worker extends Thread {
 public void run() {
     try {
         for (int i = 1; i <= 3; i++) {
             System.out.println(getName() + " - Count: " + i);
             sleep(500); // pause for 0.5 seconds
         }
     } catch (InterruptedException e) {
         System.out.println("Thread interrupted");
     }
 }
}

public class ThreadMethodsExample {
 public static void main(String[] args) throws Exception {
     Worker t1 = new Worker();
     Worker t2 = new Worker();

     t1.setName("Worker-1");
     t2.setName("Worker-2");

     t1.setPriority(Thread.MAX_PRIORITY);
     t2.setPriority(Thread.MIN_PRIORITY);

     t1.start();
     t2.start();

     t1.join();   // wait for t1 to finish
     t2.join();   // wait for t2 to finish

     System.out.println("Both threads completed.");
 }
}
