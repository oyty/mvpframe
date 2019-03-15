package com.oyty.mvpframe.thread;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

/**
 * Created by oyty on 2019/3/15.
 */
public class PriorityThreadFactory implements ThreadFactory {

    private final int priority;

    public PriorityThreadFactory(int priority) {
        this.priority = priority;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(priority);
                } catch (Throwable t) {}
                runnable.run();
            }
        };
        return new Thread(wrapperRunnable);
    }
}
