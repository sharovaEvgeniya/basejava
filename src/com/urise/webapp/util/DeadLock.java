package com.urise.webapp.util;

public class DeadLock {
    public static final Object lockFirst = new Object();
    public static final Object lockSecond = new Object();

    public static void main(String[] args) {
        new Thread(() -> deadLockRun(lockFirst, lockSecond)).start();
        new Thread(() -> deadLockRun(lockSecond, lockFirst)).start();
    }

    private static void deadLockRun(Object first, Object second) {
        synchronized (first) {
            System.out.println("Поток " + Thread.currentThread().getName() + " удерживает блокировку 1");
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " ожидает блокировку 2");
            synchronized (second) {
                System.out.println("Поток " + Thread.currentThread().getName() + " удерживает блокировку 1 и 2");
            }
        }
    }
}

