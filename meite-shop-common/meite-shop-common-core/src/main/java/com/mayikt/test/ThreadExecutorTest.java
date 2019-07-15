package com.mayikt.test;

import java.util.concurrent.*;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/7/15 0015 上午 9:57
 * @version: V1.0
 */
public class ThreadExecutorTest {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.execute(() -> {
            System.out.println(">>>executor...");
        });


        Future submit = pool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(">>>submit...Runnable");
        });
        try {
            // 等任务执行完毕会打印null
            System.out.println("result:" + submit.get());
            if (submit.get() == null) {
                System.out.println(">>>任务完成...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        // submit方便Exception处理
        Future<Integer> submit1 = pool.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println(">>>submit...Callable");
                int i = 1 / 0;
                return 1;
            }
        });
        try {
            // 拿到返回结果
            System.out.println("result1:" + submit1.get());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }

        pool.shutdown();
        while (true) {
//            System.out.println("waitting threadPool stop...");
            if (pool.isTerminated()) {
                Long end = System.currentTimeMillis();
                System.out.println("全部线程执行结束，耗时：" + (end - start));
                break;
            }
        }

    }

}
