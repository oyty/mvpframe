package com.oyty.mvpframe.thread;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by oyty on 2019/3/15.
 */
public class DefaultExecutorSupplier extends BaseExecutorSupplier {

    /**
     * thread pool executor for background tasks
     */
    private final ThreadPoolExecutor forBackgroundTasks;

    /**
     * thread pool executor for light weight background tasks
     */
    private final ThreadPoolExecutor forLightWeightBackgroundTasks;

    /**
     * executor for main thread tasks
     */
    private final Executor mainThreadExecutor;

    /**
     * an instance of DefaultExecutorSupplier
     */
    private static DefaultExecutorSupplier instance;

    public static DefaultExecutorSupplier getInstance() {
        if(instance == null) {
            synchronized (DefaultExecutorSupplier.class) {
                instance = new DefaultExecutorSupplier();
            }
        }
        return instance;
    }

    /**
     * corePoolSize: The minimum number of threads to keep in the pool. Initially there are
     * zero threads in the pool. But as tasks are added to the queue, new threads are created.
     * If there are idle threads--but the thread count is lower than the codePoolSize--then
     * new threads will keep on being created.
     */
    private DefaultExecutorSupplier() {
        ThreadFactory backgroundPriorityThreadFactory =
                new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        // setting the thread pool executor for forBackgroundTasks
        forBackgroundTasks = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                backgroundPriorityThreadFactory
            );

        forLightWeightBackgroundTasks = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                backgroundPriorityThreadFactory
        );

        mainThreadExecutor = new MainThreadExecutor();
    }

    /**
     * returns the thread pool executor for background tasks
     * @return ThreadPoolExecutor
     */
    public ThreadPoolExecutor forBackgroundTasks() {
        return forBackgroundTasks;
    }

    /**
     * returns the thread pool executor for light weight background tasks
     * @return ThreadPoolExecutor
     */
    public ThreadPoolExecutor forLightWeightBackgroundTasks() {
        return forLightWeightBackgroundTasks;
    }

    /**
     * returns the thread pool executor for main thread task
     * @return Executor
     */
    public Executor forMainThreadTasks() {
        return mainThreadExecutor;
    }
}
