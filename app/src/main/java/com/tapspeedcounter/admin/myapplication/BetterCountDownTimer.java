package com.tapspeedcounter.admin.myapplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ADMIN on 3/12/2018.
 */

public abstract class BetterCountDownTimer extends Timer {
    private long totalTime, interval, delay;
    private TimerTask task;
    private long startTime = -1;
    private boolean restart = false, wasCancelled = false, wasStarted = false;

    public BetterCountDownTimer(long totalTime, long interval) {
        this(totalTime, interval, 0);
    }

    public BetterCountDownTimer(long totalTime, long interval, long delay) {
        super("BetterCountDownTimer", true);
        this.delay = delay;
        this.interval = interval;
        this.totalTime = totalTime;
        this.task = getTask(totalTime);
    }

    public void start() {
        wasStarted = true;
        this.scheduleAtFixedRate(task, delay, interval);
    }

    public void restart() {
        if(!wasStarted) {
            start();
        }
        else if(wasCancelled) {
            wasCancelled = false;
            this.task = getTask(totalTime);
            start();
        }
        else{
            this.restart = true;
        }
    }

    public void stop() {
        this.wasCancelled = true;
        this.task.cancel();
    }

    // Call this when there's no further use for this timer
    public void dispose(){
        cancel();
        purge();
    }

    private TimerTask getTask(final long totalTime) {
        return new TimerTask() {

            @Override
            public void run() {

                long timeLeft;
                if (startTime < 0 || restart) {
                    startTime = scheduledExecutionTime();
                    timeLeft = totalTime;
                    restart = false;
                } else {
                    timeLeft = totalTime - (scheduledExecutionTime() - startTime);

                    if (timeLeft <= 0) {
                        this.cancel();
                        startTime = -1;
                        onFinish();
                        return;
                    }
                }
                onTick(timeLeft);}
        };
    }

    public abstract void onTick(long timeLeft);
    public abstract void onFinish();
}
