package com.cyber.infrastructure.cache;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public enum AuthingCacheScheduler {

    INSTANCE;

    private AtomicInteger cacheTaskNumber = new AtomicInteger(1);
    private ScheduledExecutorService scheduler;

    AuthingCacheScheduler() {
        create();
    }

    private void create() {
        this.shutdown();
        this.scheduler = new ScheduledThreadPoolExecutor(10, r -> new Thread(r, String.format("Cyber-Authing-Task-%s", cacheTaskNumber.getAndIncrement())));
    }

    public void shutdown() {
        if (null != scheduler) {
            this.scheduler.shutdown();
        }
    }

    public void schedule(Runnable task, long delay) {
        this.scheduler.scheduleAtFixedRate(task, delay, delay, TimeUnit.MILLISECONDS);
    }
}
