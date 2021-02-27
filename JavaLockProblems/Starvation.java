
// Java program to illustrate Starvation concept 
class Starvation extends Thread {
  static int threadcount = 1;

  static synchronized void process() {
    System.out.println(threadcount + "st Child" + " Thread execution starts" + Thread.currentThread().getName());
    System.out.println("Child thread execution completes" + Thread.currentThread().getName());
    threadcount++;

  }

  public void run() {
    process();

  }

  public static void main(String[] args) throws InterruptedException {
    System.out.println("Main thread execution starts");

    // Thread priorities are set in a way that thread5
    // gets least priority.
    Starvation thread1 = new Starvation();
    thread1.setPriority(4);
    Starvation thread2 = new Starvation();
    thread2.setPriority(9);
    Starvation thread3 = new Starvation();
    thread3.setPriority(8);
    Starvation thread4 = new Starvation();
    thread4.setPriority(7);
    Starvation thread5 = new Starvation();
    thread5.setPriority(6);

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();

    // Here thread5 have to wait beacause of the
    // other thread. But after waiting for some
    // interval, thread5 will get the chance of
    // execution. It is known as Starvation
    thread5.start();

    System.out.println("Main thread execution completes");
  }
}
