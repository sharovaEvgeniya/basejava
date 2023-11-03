package com.urise.webapp;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static int counter;
    private static final int THREADS_NUMBER = 10000;
    private static final Lock lock = new ReentrantLock();
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                        + ", " + Thread.currentThread().getState());
            }
        }).start();

        new Thread(() -> System.out.println(Thread.currentThread().getName()
                + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return counter;
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(atomicInteger.get());

        new MainConcurrency().inc();
    }

    private void inc() {
      atomicInteger.incrementAndGet();
    }
}
