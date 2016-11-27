package com.satybald;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public interface Scheduler<T> {
    public Task<T> schedule(LocalDateTime occurAt, Callable<T> job);
    public void shutdown();
}
