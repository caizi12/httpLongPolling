package com.lf.zk;

import com.alibaba.fastjson.JSON;

public class ThreadTest extends Thread{
    public static void main(String[] args) throws InterruptedException {

        ThreadTest threadTest = new ThreadTest();

        threadTest.start();
        threadTest.join(10000);
        System.out.println(threadTest.getState());

        threadTest.notifyAll();
        System.out.println(threadTest.getState());
        //ThreadTest threadTest2 = new ThreadTest();
        //threadTest2.start();
    }

    public void run() {
        Thread thread = Thread.currentThread();
        thread.setName("test-thread");
        System.out.println(Thread.currentThread().getState());
        System.out.println(JSON.toJSONString(thread));
        int  i =1;
        while (i++<10){
            String[] a = new String[]{"123","1232"};
        }
        System.out.println(thread.getState());
    }
}
