package com.rfbsoft.prodcons;

import java.util.ArrayList;
import java.util.List;

public class ProdConController {


    public static class SignalObject {
        private boolean isDataAvailable = false;

        public synchronized boolean isDataAvailable() {
            return isDataAvailable;
        }

        public synchronized void setDataAvailable(boolean dataAvailable) {
            isDataAvailable = dataAvailable;
        }
    }

    public static class ProdWorker implements Runnable {
        private List stringList;
        private SignalObject signalObject;

        public ProdWorker(List stringList, SignalObject signalObject) {
            this.stringList = stringList;
            this.signalObject = signalObject;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Adding " + i + " to queue");
                stringList.add(String.valueOf(i));
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (signalObject) {
                signalObject.setDataAvailable(true);
                signalObject.notifyAll();
            }
        }
    }



    public static class ConWorker implements Runnable {
        private final List stringList;
        private String name;
        private SignalObject signalObject;

        public ConWorker(List stringList, SignalObject signalObject, String name) {
            this.stringList = stringList;
            this.name = name;
            this.signalObject = signalObject;
        }

        @Override
        public void run() {
            while (!signalObject.isDataAvailable()) {
                try {
                    synchronized (signalObject) {
                        signalObject.wait();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Received interrupt");
                    ex.printStackTrace();
                }
            }

            synchronized (stringList) {
                for (int i = 0; i < stringList.size(); i++) {
                    System.out.println("Received:" + stringList.get(i) + " by worker:" + name);
                    stringList.remove(i);
//                     if (i % 2 == 0 && this.name.equals("worker1")) {
//                     System.out.println("Received:" + stringList.get(i) + " by worker:" + name);
//                     }
//                     else {
//                     System.out.println("Received:" + stringList.get(i) + " by worker:" + name);
//                     }
                }
            }

            System.out.println("Finished consuming all data");
        }
    }
    public static void main(String[] args) throws Exception {
        List stringList = new ArrayList();
        SignalObject signalObject = new SignalObject();
        ProdWorker prodWorker = new ProdWorker(stringList, signalObject);
        ConWorker conWorker1 = new ConWorker(stringList, signalObject, "worker1");
        ConWorker conWorker2 = new ConWorker(stringList, signalObject, "worker2");
        Thread prodThrd = new Thread(prodWorker);
        Thread conThrd1 = new Thread(conWorker1);
        Thread conThrd2 = new Thread(conWorker2);
        prodThrd.start();
        conThrd1.start();
        conThrd2.start();

        prodThrd.join();
        conThrd1.join();
        conThrd2.join();
    }
}
