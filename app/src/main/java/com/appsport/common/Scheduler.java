package com.appsport.common;

import android.os.Handler;

import com.appsport.task.CancelTimerTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author LEBOC Philippe on 04/10/2016.
 */
public final class Scheduler {

    private Handler handler = new Handler();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public ScheduledFuture scheduleAtFixedRateGraphic(final Runnable runnable, long initialDelay, long period) {
        return scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, initialDelay, period);
    }

    public ScheduledFuture scheduleAtFixedRateGraphic(final Runnable runnable, long initialDelay, long period, long abortDelay) {
        final ScheduledFuture task = scheduleAtFixedRateGraphic(runnable, initialDelay, period);
        schedule(new CancelTimerTask(task), abortDelay);
        return task;
    }

    public ScheduledFuture scheduleAtFixedRate(Runnable runnable, long initialDelay, long period) {
        return scheduler.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture scheduleGraphic(final Runnable runnable, long initialDelay) {
        return scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, initialDelay, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture schedule(Runnable runnable, long initialDelay) {
        return scheduler.schedule(runnable, initialDelay, TimeUnit.MILLISECONDS);
    }

    public static Scheduler getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder
    {
        protected static final Scheduler INSTANCE = new Scheduler();
    }
}