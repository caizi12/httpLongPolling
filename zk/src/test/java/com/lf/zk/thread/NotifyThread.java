package com.lf.zk.thread;

public class NotifyThread implements Runnable{

    Object lock;

    public NotifyThread(Object lock){
        this.lock = lock;
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(Thread.currentThread().getState());
        synchronized (lock){
            System.out.println(threadName + "开始进入同步代码块区域");
            System.out.println(Thread.currentThread().getState());
            lock.notify();
            try {
                System.out.println(Thread.currentThread().getState());
                System.out.println(threadName + "业务处理开始");
                // 暂停 2s 表示业务处理
                Thread.sleep(2000);
                System.out.println(threadName + "业务处理结束");
                System.out.println(Thread.currentThread().getState());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + "准备离开同步代码块区域");
            //lock.notify();放在这一行唤醒，效果一样
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + "退出同步代码块后续操作");
    }
}