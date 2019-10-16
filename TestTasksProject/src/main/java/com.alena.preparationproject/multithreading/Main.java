package com.alena.preparationproject.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        taskWithDigestFromLuxoft();
        taskWithAtomicInteger();

    }

    private static void taskWithDigestFromLuxoft() throws InterruptedException {
        int threadNum = 10;
        List<String> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            values.add(String.valueOf(i));
        }
        runThreads(new DigestV1(), threadNum, values.stream());
        runThreads(new DigestV2(), threadNum, values.stream());
    }

    private static void taskWithAtomicInteger() throws InterruptedException {
        int threadNum = 10;
        List<Integer> values = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            values.add(i);
        }
        runThreads(IncrementFactory.getIncrementIntegerClass(), threadNum, values.stream());
        runThreads(IncrementFactory.getSynchronizedIncrementClass(), threadNum, values.stream());
        runThreads(IncrementFactory.getIncrementVolatileClass(), threadNum, values.stream());
        runThreads(IncrementFactory.getIncrementAtomicClass(), threadNum, values.stream());
    }

    private static <T> void runThreads(ThreadFunction<T> function, int threadsNum, Stream<T> stream) throws InterruptedException {
        //ExecutorService нужен для выполнения некоторой задачи и имеет возможности по управлению ходом выполнения
        //Executors - фабрика по созданию ExecutorService
        ExecutorService service = Executors.newFixedThreadPool(threadsNum);

        stream.forEach(element -> service.submit(() -> function.doTask(element)));

        service.shutdown();

        while (!service.isTerminated()) {
            Thread.sleep(1);
        }

        System.out.println(function.getResult());
    }
}
