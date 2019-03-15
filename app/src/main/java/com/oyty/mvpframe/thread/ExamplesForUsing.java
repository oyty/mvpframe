package com.oyty.mvpframe.thread;

import java.util.concurrent.Future;

/**
 * Created by oyty on 2019/3/15.
 */
public class ExamplesForUsing {


    /**
     * Using it for background tasks
     */
    public void doSomeBackgroundWork() {
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here
                    }
                });
    }

    /**
     * Using it for Light-Weight background tasks
     */
    public void doSomeLoghtWeightBackgroundWork() {
        DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some light-weight background work here
                    }
                });
    }

    /**
     * Using it for main thread works
     */
    public void doSomeMainThreadWork() {
        DefaultExecutorSupplier.getInstance().forMainThreadTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some main thread work here
                    }
                });
    }

    /**
     * For cancelling the task
     */
    public void cancelableBackgroundTasks() {
        Future future = DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .submit(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                    }
                });

        // cancel the task
        future.cancel(true);
    }

    /**
     * Do some tasks at high priority
     */
    public void doSomeTaskAtHighPriority() {
        PriorityExecutorSupplier.getInstance().forBackgroundTasks()
                .submit(new PriorityRunnable(Priority.HIGH) {
                    @Override
                    public void run() {
                        // do some background work here at high priority
                    }
                });
    }
}
