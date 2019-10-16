package com.alena.preparationproject.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

//пример порождающего шаблона проектирования - Простая фабрика
public class IncrementFactory {

    public static ThreadFunction<Integer> getIncrementIntegerClass() {
        return new ThreadFunction<Integer>() {
            private int i;
            @Override
            public Integer doTask(Integer value) {
                return i++;
            }
            @Override
            public Integer getResult() {
                return i;
            }

        };
    }

    public static ThreadFunction<Integer> getSynchronizedIncrementClass() {
        return new ThreadFunction<Integer>() {
            private int i;
            @Override
            public synchronized Integer doTask(Integer value) {
                return i++;
            }
            @Override
            public Integer getResult() {
                return i;
            }

        };
    }

    public static ThreadFunction<Integer> getIncrementVolatileClass() {
        return new ThreadFunction<Integer>() {
            private volatile int i;
            @Override
            public Integer doTask(Integer value) {
                return i++;
            }
            @Override
            public Integer getResult() {
                return i;
            }
        };
    }

    public static ThreadFunction<Integer> getIncrementAtomicClass() {
        return new ThreadFunction<Integer>() {
            private AtomicInteger i = new AtomicInteger(0);
            @Override
            public Integer doTask(Integer value) {
                return i.incrementAndGet();
            }
            @Override
            public Integer getResult() {
                return i.get();
            }
        };
    }
}
