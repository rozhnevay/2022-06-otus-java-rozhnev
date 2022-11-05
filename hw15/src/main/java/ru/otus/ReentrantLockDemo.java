package ru.otus;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReentrantLockDemo {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);
    private static final String thread1 = "t1";
    private static final String thread2 = "t2";

    private final Lock lock = new ReentrantLock();

    private Integer num = 0;
    private boolean thread2Printed = false;
    private boolean thread1Printed = false;
    private boolean increment = true;

    public static void main(String[] args) throws InterruptedException {
        new ReentrantLockDemo().go();
    }

    private void go() throws InterruptedException {
        var t1 = new Thread(this::thread1CriticalSection);
        t1.setName(thread1);
        t1.start();

        var t2 = new Thread(this::thread2CriticalSection);
        t2.setName(thread2);
        t2.start();
    }

    private void thread1CriticalSection() {
        while (num <= 10 && num >= 0) {
            if ((thread2Printed || num == 0) && !thread1Printed) {
                try {
                    lock.lock();
                    num = increment ? ++num : --num;
                    if (increment && num == 10 || !increment && num == 1) {
                        increment = !increment;
                    }
                    logger.info(num.toString());
                    thread2Printed = false;
                    thread1Printed = true;
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private void thread2CriticalSection() {
        while (num <= 10 && num > 0) {
            if (thread1Printed && !thread2Printed) {
                try {
                    lock.lock();
                    logger.info(num.toString());
                    thread2Printed = true;
                    thread1Printed = false;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
