package com.lf.zk.thread;

public class WaitThread implements Runnable{
    Object lock;

    public WaitThread(Object lock){
        this.lock = lock;
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        synchronized (lock){
            System.out.println(threadName + "开始进入同步代码块区域");
            System.out.println(Thread.currentThread().getState());
            try {
                lock.wait();
                System.out.println(Thread.currentThread().getState());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(threadName + "准备离开同步代码块区域");
        }
    }
}