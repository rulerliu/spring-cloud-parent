package com.mayikt.test;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch是一个异步辅助类，它能让一个和多个线程处于等待状态，直到其他线程完成了一些列操作。
 * <p>
 * 比如某个线程需要其他线程执行完毕才能执行其他的：
 */
public class CountDownLautchTest {

    /**
     * public void await() throws InterruptedException { };   //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
     * public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
     * public void countDown() { };  //将count值减1
     *
     * @param args
     */
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {

            System.out.println("子线程1执行开始");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程1执行结束");
            latch.countDown();

        }).start();


        new Thread(() -> {

            System.out.println("子线程2执行开始");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程2执行结束");
            latch.countDown();

        }).start();

        new Thread(() -> {
            try {
                System.out.println("准备结束了。。。");
                latch.await();
                System.out.println("所有子线程执行完毕了。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
