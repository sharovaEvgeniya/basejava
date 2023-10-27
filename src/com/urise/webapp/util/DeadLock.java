package com.urise.webapp.util;

public class DeadLock {
    public static final Object lockFirst = new Object();
    public static final Object lockSecond = new Object();

    public static void main(String[] args) {
        ThreadFirst threadFirst = new ThreadFirst();
        ThreadSecond threadSecond = new ThreadSecond();
        threadFirst.start();
        threadSecond.start();
    }

    private static class ThreadFirst extends Thread {
        @Override
        public void run() {
            synchronized (lockFirst) {
                System.out.println("Поток 1: Удерживает блокировку 1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                System.out.println("Поток 2: Ожидает блокировку 2");
                synchronized (lockSecond) {
                    System.out.println("Поток 1: Удерживает блокировку 1 и 2");
                }
            }
        }
    }
    private static class ThreadSecond extends Thread {
        @Override
        public void run() {
            synchronized (lockSecond) {
                System.out.println("Поток 2: Удерживает блокировку 1");
                try{
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
                System.out.println("Поток 2: Ожидает блокровку 2");
                synchronized (lockFirst) {
                    System.out.println("Поток 2: Удерживает блокировку 1 и 2");
                }
            }
        }
    }
}

