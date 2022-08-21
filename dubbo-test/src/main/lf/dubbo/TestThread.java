package lf.dubbo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThread {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        CountDownLatch ca = new CountDownLatch(1);
        CountDownLatch cb = new CountDownLatch(1);
        CountDownLatch cc = new CountDownLatch(1);
        Thread tA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ca.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                cb.countDown();
            }
        });

        Thread tB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                cc.countDown();
            }
        });
        Thread tC = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cc.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
            }
        });

        tA.start();
        tB.start();
        tC.start();
        ca.countDown();

    }
}
