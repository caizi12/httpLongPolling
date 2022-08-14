package com.lf.zk.thread;

public class TestThread {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread waitThread = new Thread(new WaitThread(lock), "waitThread");
        Thread notifyThread = new Thread(new NotifyThread(lock), "notifyThread");
        waitThread.start();
        Thread.sleep(1000);
        notifyThread.start();
    }
}
