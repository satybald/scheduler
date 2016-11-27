package com.satybald;


import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Task<T> implements Comparable<Task<?>>, Callable {

    private final Integer id;
    private final LocalDateTime occuredAt;
    private final Callable<T> callable;


    public Task(LocalDateTime occuredAt, Callable<T> callable, Integer id) {
        this.occuredAt = occuredAt;
        this.callable = callable;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getOccuredAt() {
        return occuredAt;
    }

    public T getCallableResult() {
        return this.call();
    }

    @Override
    public T call() {
        try {
            return callable.call();
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public int compareTo(Task<?> o1) {
        int cmpDate = this.getOccuredAt().compareTo(o1.getOccuredAt());
        return cmpDate != 0 ? cmpDate : Integer.compare(this.getId(), o1.getId());
    }
}
