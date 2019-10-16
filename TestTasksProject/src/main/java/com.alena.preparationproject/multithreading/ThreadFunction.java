package com.alena.preparationproject.multithreading;

public interface ThreadFunction<T> {
    T doTask(T value);
    T getResult();
}
