package com.lf.zk.thread;

public class ThreadStatusTest {

    private static Object object = new Object();

    public static void main(String[] args) throws Exception {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 1000; i++) {
                        System.out.print("");
                    }
                    Thread.sleep(500);
                    synchronized (object) {
                        object.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (object) {
                        Thread.sleep(1000);
                    }
                    Thread.sleep(1000);
                    synchronized (object) {
                        object.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        System.out.println("1 " + thread.getState());
        thread.start();
        thread1.start();
        System.out.println("2 " + thread.getState());
        while (thread.isAlive()) {
            System.out.println("---" + thread.getState());
            Thread.sleep(100);
        }
        System.out.println("3 " + thread.getState());

    }

}
