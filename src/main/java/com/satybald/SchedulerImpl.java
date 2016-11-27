package com.satybald;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulerImpl<T> implements Scheduler<T> {
    private final PriorityBlockingQueue<Task<?>> taskQueue;
    private final AtomicInteger counter;
    private final ExecutorService poolTask;
    private final ScheduledExecutorService scheduler;

    public SchedulerImpl() {
        this.taskQueue = new PriorityBlockingQueue<Task<?>>();
        counter = new AtomicInteger();
        poolTask = Executors.newCachedThreadPool();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Worker(), 0, 1, TimeUnit.SECONDS);
    }

    public Task<T> schedule(LocalDateTime occurAt, Callable<T> job) {
        Task taskToExecute = new Task(occurAt, job, counter.incrementAndGet());
        taskQueue.add(taskToExecute);
        return taskToExecute;
    }

    @Override
    public void shutdown() {
        this.poolTask.shutdown();
        this.scheduler.shutdown();
    }


    private boolean isReadyForProcessing(Task minTask) {
        if(minTask == null)
            return false;
        else {
            LocalDateTime now = LocalDateTime.now();
            return now.isEqual(minTask.getOccuredAt()) || minTask.getOccuredAt().isBefore(now);
        }
    }
    class Worker implements Runnable{
        @Override
        public void run() {
            Task minTask = taskQueue.peek();
            if(isReadyForProcessing(minTask)) {
                poolTask.submit(taskQueue.poll());
            }
        }
    }
}


