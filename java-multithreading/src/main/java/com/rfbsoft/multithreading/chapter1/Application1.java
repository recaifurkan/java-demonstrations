package com.rfbsoft.multithreading.chapter1;

public class Application1 {

    public static void main(String[] args) {
        Runner runner1 = new Runner();
        Runner runner2 = new Runner();

        runner1.start();
        runner2.start();
        System.out.println("çıktı");
    }

    static class Runner extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println("Merhaba " + i + " - " + this.getName());
            }
        }

    }

}
