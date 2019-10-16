package com.alena.preparationproject.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

interface IncrementClass extends ThreadFunction<Integer> {
    int incrementAndGet();
    int get();
}
