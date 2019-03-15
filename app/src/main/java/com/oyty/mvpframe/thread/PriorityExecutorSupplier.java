package com.oyty.mvpframe.thread;

import android.os.Process;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by oyty on 2019/3/15.
 */
public class PriorityExecutorSupplier extends BaseExecutorSupplier {

    /**
     * thread pool executor for priority background tasks
     */
    private final PriorityThreadPoolExecutor forPriorityBackgroundTasks;

    /**
     * an instance of PriorityExecutorSupplier
     */
    private static PriorityExecutorSupplier instance;

    public static PriorityExecutorSupplier getInstance() {
        if(instance == null) {
            synchronized (PriorityExecutorSupplier.class) {
                instance = new PriorityExecutorSupplier();
            }
        }
        return instance;
    }

    public PriorityExecutorSupplier() {
        ThreadFactory backgroundPriorityThreadFactory =
                new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        // setting the thread pool executor for forBackgroundTasks
        forPriorityBackgroundTasks = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );
    }

    /**
     * returns the thread pool executor for background tasks
     * @return forPriorityBackgroundTasks
     */
    public PriorityThreadPoolExecutor forBackgroundTasks() {
        return forPriorityBackgroundTasks;
    }
}
